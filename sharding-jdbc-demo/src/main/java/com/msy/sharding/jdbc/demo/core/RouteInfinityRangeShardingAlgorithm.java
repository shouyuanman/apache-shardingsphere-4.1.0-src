package com.msy.sharding.jdbc.demo.core;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:44
 * @desc
 */
public final class RouteInfinityRangeShardingAlgorithm implements RangeShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(final Collection<String> availableTargetNames, final RangeShardingValue<Integer> shardingValue) {

        Collection<String> result = new HashSet<>();

        result.addAll(availableTargetNames);

        return result;
    }
}
