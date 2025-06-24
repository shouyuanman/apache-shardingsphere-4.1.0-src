package com.msy.sharding.jdbc.demo.core;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:43
 * @desc
 */
public final class RangeOrderShardingAlgorithm implements RangeShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(final Collection<String> availableTargetNames, final RangeShardingValue<Integer> shardingValue) {
        Collection<String> result = new HashSet<>(2);
        for (int i = shardingValue.getValueRange().lowerEndpoint(); i <= shardingValue.getValueRange().upperEndpoint(); i++) {

            for (String each : availableTargetNames) {
                System.out.println("shardingValue = " + shardingValue.getValueRange() + " target = " + each + "  shardingValue.getValue() % 2) = " + i % 2);
                if (each.endsWith(String.valueOf(i % 2))) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}
