package com.msy.sharding.jdbc.demo.core;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:42
 * @desc
 */
public class DsPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    private  String name="ds";
    private  int dsCounts=2;

    public DsPreciseShardingAlgorithm() {
    }

    public DsPreciseShardingAlgorithm(String name, int dsCounts) {
        this.name = name;
        this.dsCounts = dsCounts;
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {

        Long value = preciseShardingValue.getValue();
        long index = value % dsCounts;
        return this.name +index;
    }


}
