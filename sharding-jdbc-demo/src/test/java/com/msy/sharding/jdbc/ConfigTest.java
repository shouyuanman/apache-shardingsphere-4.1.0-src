package com.msy.sharding.jdbc;

import com.msy.sharding.jdbc.demo.ShardingJdbcDemoApplication;
import com.msy.sharding.jdbc.demo.entity.ConfigBean;
import com.msy.sharding.jdbc.demo.entity.Page;
import com.msy.sharding.jdbc.demo.service.JpaEntityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 16:12
 * @desc
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingJdbcDemoApplication.class})
// 指定启动类

public class ConfigTest {

    @Resource
    JpaEntityService entityService;


    @Test
    public void testAddOneConfigBean() {

        ConfigBean dto = new ConfigBean();

        dto.setId(3L);
        dto.setStatus("UN_KNOWN" );

        //增加 数据
        entityService.addConfigBean(dto);


    }


    @Test
    public void testSelectAllConfigBean() {
        List<ConfigBean> all = entityService.selectAllConfigBean();
        System.out.println(all);

    }


    @Test
    public void testAddSomeConfigBean() {

        for (int i = 0; i < 10; i++) {
            ConfigBean dto = new ConfigBean();

            dto.setStatus("UN_KNOWN" + i);

            //增加 数据
            entityService.addConfigBean(dto);
        }


    }


    @Test
    public void testSelectPage() {
        Page page = new Page();
        //第1页
        page.setPage(1);
        page.setRowsTotal(10);
        page.setRows(3);
        List<ConfigBean> list = entityService.selectConfigBean(page);
        System.out.println(list);


    }
    @Test
    public void testSelect2Page() {
        Page page = new Page();
        //第1页
        page.setPage(1);
        page.setRowsTotal(10);
        page.setRows(3);
        List<ConfigBean> list = entityService.selectConfigBean(page);
        System.out.println(list);

        //第2页

        page.setPage(2);
        page.setRowsTotal(10);
        page.setRows(3);
        list = entityService.selectConfigBean(page);
        System.out.println(list);

    }

}
