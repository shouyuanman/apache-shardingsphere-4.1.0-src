package com.msy.sharding.jdbc.demo.entity;

import java.io.Serializable;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:51
 * @desc
 */
public class User implements Serializable {


    private long userId;

    private String name;


    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(" user_id: %s, name: %s", userId, name);
    }
}
