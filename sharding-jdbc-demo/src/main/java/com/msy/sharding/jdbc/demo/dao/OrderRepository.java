package com.msy.sharding.jdbc.demo.dao;

import com.msy.sharding.jdbc.demo.entity.Order;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:49
 * @desc
 */
public interface OrderRepository extends CommonRepository<Order> {
    Order selectOne(Long orderId);
}
