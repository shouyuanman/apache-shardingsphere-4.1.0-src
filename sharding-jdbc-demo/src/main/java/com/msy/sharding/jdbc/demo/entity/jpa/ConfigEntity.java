package com.msy.sharding.jdbc.demo.entity.jpa;

import com.msy.sharding.jdbc.demo.entity.ConfigBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:53
 * @desc
 */
@Entity
@Table(name = "t_config")
public final class ConfigEntity extends ConfigBean {

    @Id
    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public long getId() {
        return super.getId();
    }

    @Column(name = "status")
    public String getStatus() {
        return super.getStatus();
    }
}
