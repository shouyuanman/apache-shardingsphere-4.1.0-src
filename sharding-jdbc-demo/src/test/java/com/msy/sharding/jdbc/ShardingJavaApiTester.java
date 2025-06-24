package com.msy.sharding.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 16:08
 * @desc
 */
public class ShardingJavaApiTester {
    protected static final String USER_LOGIC_TB = "t_user";
    DataSource dataSource;

    @Before
    public void runBeforeTestMethod() {
        System.out.println("@Before - runBeforeTestMethod");
    }

    // 通过Druid构建数据源.
    // driver : 数据库驱动
    // url: 数据库地址
    // username/pwd : 账号和密码

    public DataSource buildDruidDataSource(String url, String username, String password) {
        DruidDataSource druidDataSource = new DruidDataSource();

//        druidDataSource.setDriverClassName(Driver.class.getName());
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);

        return druidDataSource;
    }

    /**
     * 通过ShardingDataSourceFactory 构建分片数据源
     *
     * @return
     * @throws SQLException
     */
    @Before

    public void buildShardingDataSource() throws SQLException {
        /*
         * 1. 数据源集合：dataSourceMap
         * 2. 分片规则：shardingRuleConfig
         * 3. 属性：properties
         *
         */

        DataSource druidDs1 = buildDruidDataSource(
                "jdbc:mysql://localhost:3306/sharding_db1?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=true&serverTimezone=UTC",
                "root", "123456");

        DataSource druidDs2 = buildDruidDataSource(
                "jdbc:mysql://localhost:3306/sharding_db2?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=true&serverTimezone=UTC",
                "root", "123456");
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
        // 添加数据源.
        // 两个数据源ds_0和ds_1
        dataSourceMap.put("ds0", druidDs1);
        dataSourceMap.put("ds1", druidDs2);

        /**
         * 需要构建表规则
         * 1. 指定逻辑表.
         * 2. 配置实际节点》
         * 3. 指定主键字段.
         * 4. 分库和分表的规则》
         *
         */
        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();


        //step2：分片规则
        TableRuleConfiguration userShardingRuleConfig = userShardingRuleConfig();
        shardingRuleConfig.getTableRuleConfigs().add(userShardingRuleConfig);


        // 多数据源一定要指定默认数据源
        // 只有一个数据源就不需要
        shardingRuleConfig.setDefaultDataSourceName("ds0");

        Properties properties = new Properties();
        //打印sql语句，生产环境关闭
        properties.setProperty("sql.show", Boolean.TRUE.toString());

        dataSource = ShardingDataSourceFactory.createDataSource(
                dataSourceMap, shardingRuleConfig, properties);

    }


    /**
     * 表的分片规则
     */
    protected TableRuleConfiguration userShardingRuleConfig() {
        String logicTable = USER_LOGIC_TB;

        //获取实际的 ActualDataNodes
        String actualDataNodes = "ds$->{0..1}.t_user_$->{0..1}";

        // 两个表达式的 笛卡尔积
//ds0.t_user_0
//ds1.t_user_0
//ds0.t_user_1
//ds1.t_user_1

        TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration(logicTable, actualDataNodes);

        //设置分表策略
        // inline 模式
        ShardingStrategyConfiguration tableShardingStrategy =
                new InlineShardingStrategyConfiguration("user_id", "t_user_$->{user_id % 2}");
        //自定义模式
//        TableShardingAlgorithm tableShardingAlgorithm = new TableShardingAlgorithm();
//        ShardingStrategyConfiguration tableShardingStrategy = new StandardShardingStrategyConfiguration("user_id", tableShardingAlgorithm);

        tableRuleConfig.setTableShardingStrategyConfig(tableShardingStrategy);

        // 配置分库策略（Groovy表达式配置db规则）
        // inline 模式
        ShardingStrategyConfiguration dsShardingStrategy = new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}");
        //自定义模式
//        DsShardingAlgorithm dsShardingAlgorithm = new DsShardingAlgorithm();
//        ShardingStrategyConfiguration dsShardingStrategy = new StandardShardingStrategyConfiguration("user_id", dsShardingAlgorithm);
        tableRuleConfig.setDatabaseShardingStrategyConfig(dsShardingStrategy);

//        SnowflakeShardingKeyGenerator keyGenerator = new SnowflakeShardingKeyGenerator();
        Properties properties = new Properties();
        properties.setProperty("max.vibration.offset", String.valueOf(4096));
//        keyGenerator.setProperties(properties);


        tableRuleConfig.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "user_id"/**, properties*/));
        return tableRuleConfig;
    }

    /**
     * 新增测试.
     */
    @Test
    public void testInsertUser() throws SQLException {

        /*
         * 1. 需要到DataSource
         * 2. 通过DataSource获取Connection
         * 3. 定义一条SQL语句.
         * 4. 通过Connection获取到PreparedStament.
         *  5. 执行SQL语句.
         *  6. 关闭连接.
         */


        // * 2. 通过DataSource获取Connection
        Connection connection = dataSource.getConnection();
        // * 3. 定义一条SQL语句.
        // 注意：******* sql语句中 使用的表是 上面代码中定义的逻辑表 *******
        String sql = "insert into t_user(name) values('name-0001')";

        // * 4. 通过Connection获取到PreparedStament.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // * 5. 执行SQL语句.
        preparedStatement.execute();

        sql = "insert into t_user(name) values('name-0002')";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();

        // * 6. 关闭连接.
        preparedStatement.close();
        connection.close();
    }

    /**
     * 新增测试.
     */
    @Test
    public void testSelectUser() throws SQLException {

        /*
         * 1. 需要到DataSource
         * 2. 通过DataSource获取Connection
         * 3. 定义一条SQL语句.
         * 4. 通过Connection获取到PreparedStament.
         *  5. 执行SQL语句.
         *  6. 关闭连接.
         */


        // * 2. 通过DataSource获取Connection
        Connection connection = dataSource.getConnection();
        // * 3. 定义一条SQL语句.
        // 注意：******* sql语句中 使用的表是 上面代码中定义的逻辑表 *******
        String sql = "select * from  t_user where user_id=10000";

        // * 4. 通过Connection获取到PreparedStament.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // * 5. 执行SQL语句.
        ResultSet resultSet = preparedStatement.executeQuery();


        // * 6. 关闭连接.
        preparedStatement.close();
        connection.close();
    }


}
