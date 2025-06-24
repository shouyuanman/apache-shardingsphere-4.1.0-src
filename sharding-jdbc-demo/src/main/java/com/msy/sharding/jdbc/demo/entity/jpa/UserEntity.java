package com.msy.sharding.jdbc.demo.entity.jpa;

import com.msy.sharding.jdbc.demo.entity.User;

import javax.persistence.*;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:54
 * @desc
 */
@Entity
@Table(name = "t_user")
public final class UserEntity extends User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public long getUserId() {
        return super.getUserId();
    }

    @Column(name = "name")
    public String getName() {
        return super.getName();
    }
}
