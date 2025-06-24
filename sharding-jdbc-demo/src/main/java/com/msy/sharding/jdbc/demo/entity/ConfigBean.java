package com.msy.sharding.jdbc.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:52
 * @desc
 */
@Data
public class ConfigBean implements Serializable {

    private long id;

    private String status;

}
