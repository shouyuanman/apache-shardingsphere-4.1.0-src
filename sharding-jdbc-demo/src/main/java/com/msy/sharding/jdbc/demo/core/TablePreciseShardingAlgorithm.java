package com.msy.sharding.jdbc.demo.core;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:46
 * @desc 定制分表策略
 */
public class TablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    private int tableCount = 2;
    private int dsCounts = 2;

    public TablePreciseShardingAlgorithm() {
    }

    public TablePreciseShardingAlgorithm(int tableCounts, int dsCounts) {
        this.tableCount = tableCounts;
        this.dsCounts = dsCounts;
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        StringBuilder sb = new StringBuilder();
        Long value = preciseShardingValue.getValue();
        String logicTableName = preciseShardingValue.getLogicTableName();
        long index = value / dsCounts % tableCount;
        sb.append(logicTableName).append(index);
        return sb.toString();
    }
}
