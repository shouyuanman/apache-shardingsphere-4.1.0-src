package com.msy.sharding.jdbc.demo.core;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:43
 * @desc
 */
public final class PreciseOrderShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(final Collection<String> availableTargetNames,
                             final PreciseShardingValue<Long> shardingValue) {
        for (String each : availableTargetNames) {
            System.out.println("shardingValue = " + shardingValue.getValue()+ " target = " + each + "  shardingValue.getValue() % 2) = " + shardingValue.getValue() % 2L);
            if (each.endsWith(String.valueOf(shardingValue.getValue() % 2L))) {
                return each;
            }
        }
        return null;
    }
}
