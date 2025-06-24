package com.msy.sharding.jdbc;

import com.msy.sharding.jdbc.demo.ShardingJdbcDemoApplication;
import com.msy.sharding.jdbc.demo.entity.Order;
import com.msy.sharding.jdbc.demo.entity.Page;
import com.msy.sharding.jdbc.demo.entity.User;
import com.msy.sharding.jdbc.demo.service.JpaEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 16:09
 * @desc
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingJdbcDemoApplication.class})
// 指定启动类
public class ShardingSpringbootTest {

    @Resource
    JpaEntityService entityService;


    @Test
    public void testAddSomeUser() {

        for (int i = 0; i < 10; i++) {
            User dto = new User();

            dto.setName("user_" + i);

            //增加用户
            entityService.addUser(dto);
        }
    }

    @Test
    public void testSelectAllUser() {
        //增加用户
        List<User> all = entityService.selectAllUser();
        System.out.println(all);

    }


    @Test
    public void testSelectBigThan() {


        entityService.selectBigThan(1L);
    }


    @Test
    public void testSelectAllOrder() {

        entityService.selectAllOrder();

    }


    @Test
    public void testSelectOderById() {

        entityService.selectOneOrder(707925531249061888L);
//        entityService.selectOneOrder(1L);

    }

    @Test
    public void testSelectPage() {
        Page page = new Page();
        //第1页
        page.setPage(1);
        page.setRowsTotal(10);
        page.setRows(3);
        List<User> list = entityService.selectUser(page);
        System.out.println(list);

        //第2页

        page.setPage(2);
        page.setRowsTotal(10);
        page.setRows(3);
        list = entityService.selectUser(page);
        System.out.println(list);

    }


    @Test
    public void testAddSomeOrder() {

        for (int i = 0; i < 10; i++) {
            Order dto = new Order();
            dto.setUserId(704733680467685377L);

            //增加订单
            entityService.addOrder(dto);
        }


    }

    @Test
    public void testAddSomeOrderByMonth() throws InterruptedException {

        for (int month = 1; month <= 12; month++) {
            final int index = month;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("当前月份 = " + index);
                    HintManager hintManager = HintManager.getInstance();
                    hintManager.addTableShardingValue("t_order", index);
                    hintManager.addDatabaseShardingValue("t_order", index);

                    Order dto = new Order();
                    dto.setUserId(704733680467685377L);

                    //增加订单
                    entityService.addOrder(dto);

                    hintManager.close();
                }
            }).start();
        }

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testAddUserAndOrder() {


        for (int i = 0; i < 10; i++) {

            User user = new User();

            user.setName("user_" + i);
            //增加用户
            entityService.addUser(user);

            Order dto = new Order();

            dto.setUserId(user.getUserId());

            //增加用户
            entityService.addOrder(dto);
        }


    }


    @Test
    public void testLocalTransaction() {

        //增加用户
        entityService.localTransactionDemo();




    }

    @Test
    public void testSelectAOrderByUserId() {

        long userId = 704733680467685377L;
        List<Order> all = entityService.selectAOrderByUserId(userId);
        System.out.println(all);

    }

    @Test
    public void testSelectOrderOfUser() {

        List<Order> all = entityService.selectOrderOfUser();
        System.out.println(all);

    }

    @Test
    public void testOrderByUserIdInRange() {

        long userIdOfLow = 704733680467685377L;
        long userIdOfHigh = 704733680467685377L + 1000;
        List<Order> all = entityService.selectOrderByUserIdInRange(userIdOfLow, userIdOfHigh);
        System.out.println(all);

    }


    @Test
    public void testOrderByUserIdInList() {
        List<Long> ids = new ArrayList<>();
        long userIdOfLow = 704733680467685377L;
        ids.add(userIdOfLow);
        for (int i = 0; i < 10; i++) {
            ids.add(userIdOfLow + i);

        }
        System.out.println("ids = " + ids);
        List<Order> all = entityService.selectOrderByUserIdInList(ids);
        System.out.println(all);

    }

}
