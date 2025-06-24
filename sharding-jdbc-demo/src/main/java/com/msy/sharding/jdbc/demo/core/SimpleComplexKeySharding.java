package com.msy.sharding.jdbc.demo.core;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:44
 * @desc
 */
public class SimpleComplexKeySharding implements ComplexKeysShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         ComplexKeysShardingValue<Long> shardingValue) {
        Map<String, Collection<Long>> map = shardingValue.getColumnNameAndShardingValuesMap();
//            Map<String, Range<Long>> rangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();

        Collection<Long> userIds = map.get("user_id");
        Collection<Long> orderIds = map.get("order_id");

        List<String> result = new ArrayList<>();
        // user_id，order_id分片键进行分表
        for (Long userId : userIds) {
            for (Long orderId : orderIds) {

                Long complexKeySharding = userId + orderId;
                Long suffix = complexKeySharding % 2;


                for (String each : availableTargetNames) {
                    System.out.println("innerShardingValue = " + complexKeySharding + " target = " + each + " innerShardingValue % 2 = " + suffix);
                    if (each.endsWith(suffix + "")) {
                        result.add(each);
                    }
                }
            }
        }
        return result;
    }
}
