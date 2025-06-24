package com.msy.sharding.jdbc.demo.service;

import com.msy.sharding.jdbc.demo.entity.ConfigBean;
import com.msy.sharding.jdbc.demo.entity.Order;
import com.msy.sharding.jdbc.demo.entity.Page;
import com.msy.sharding.jdbc.demo.entity.User;

import java.util.List;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:54
 * @desc
 */
public interface JpaEntityService {
    void initEnvironment();

    void cleanEnvironment();

    void processSuccess();

    void processFailure();

    //增加订单
    void addOrder(Order dto);

    //查询全部订单
    List<Order> selectAllOrder();

    List<Order> selectAOrderByUserId(long userId);
    List<Order> selectOrderOfUser();
    List<Order> selectOrderByUserIdInRange(long low, long high);

    List<Order> selectOrderByUserIdInList(List<Long> ids);

    //增加用户
    void addUser(User dto);

    //查询全部用户
    List<User> selectAllUser();

    //查询分页用户
    List<User> selectUser(Page page);


    List<User> selectBigThan(final Long userId);

    void addConfigBean(ConfigBean dto);

    //查询全部用户
    List<ConfigBean> selectAllConfigBean();

    List<ConfigBean> selectConfigBean(Page page);


    Order selectOneOrder(Long orderId);

    void localTransactionDemo();
}
