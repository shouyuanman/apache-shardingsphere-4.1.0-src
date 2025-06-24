package com.msy.sharding.jdbc.demo.core;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.*;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:45
 * @desc
 */
public class SimpleHintShardingAlgorithmDemo implements HintShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         HintShardingValue<Integer> hintShardingValue) {


        Collection<Integer> values = hintShardingValue.getValues();



        Collection<String> result = new HashSet<>(2);


        for (String each : availableTargetNames) {

            for (int shardingValue : values) {


                System.out.println("hintShardingValue = " + shardingValue + " target = " + each + " hintShardingValue % 2 = " + shardingValue % 2);
                if (each.endsWith(String.valueOf(shardingValue % 2))) {
                    result.add(each);
                }

            }
        }
        return result;
    }


}