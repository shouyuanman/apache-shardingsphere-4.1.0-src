package com.msy.sharding.jdbc.demo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:22
 * @desc
 */

@EnableConfigurationProperties

@SpringBootApplication(scanBasePackages =
        {"com.msy.sharding.jdbc.demo",
        }, exclude = {
        DataSourceAutoConfiguration.class,
//        SecurityAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class})
@EnableScheduling
@EnableJpaRepositories(basePackages = {
        "com.msy.sharding.jdbc.demo.dao.impl",
        "com.msy.sharding.jdbc.demo.dao",
})
@EnableTransactionManagement(proxyTargetClass = true)

@EntityScan(basePackages = {
        "com.msy.sharding.jdbc.demo.entity.jpa",
})

public class ShardingJdbcDemoApplication {
    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext applicationContext = SpringApplication.run(ShardingJdbcDemoApplication.class, args);


            Environment env = applicationContext.getEnvironment();
            String port = env.getProperty("server.port");
            String path = env.getProperty("server.servlet.context-path");
            System.out.println("\n----------------------------------------------------------\n\t" +
                    "Application is running! Access URLs:\n\t" +
                    "swagger-ui: \thttp://localhost:" + port + path + "/swagger-ui.html\n\t" +
                    "----------------------------------------------------------");

        }catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
