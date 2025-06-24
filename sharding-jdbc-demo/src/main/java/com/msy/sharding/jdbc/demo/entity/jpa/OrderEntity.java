package com.msy.sharding.jdbc.demo.entity.jpa;

import com.msy.sharding.jdbc.demo.entity.Order;

import javax.persistence.*;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:53
 * @desc
 */
@Entity
@Table(name = "t_order")
public final class OrderEntity extends Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public long getOrderId() {
        return super.getOrderId();
    }

    @Column(name = "user_id")
    @Override
    public long getUserId() {
        return super.getUserId();
    }

    @Column(name = "status")
    public String getStatus() {
        return super.getStatus();
    }
}
