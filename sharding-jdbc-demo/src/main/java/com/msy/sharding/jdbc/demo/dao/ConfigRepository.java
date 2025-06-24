package com.msy.sharding.jdbc.demo.dao;

import com.msy.sharding.jdbc.demo.entity.ConfigBean;
import com.msy.sharding.jdbc.demo.entity.Page;

import java.util.List;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:50
 * @desc
 */
public interface ConfigRepository {

    Long insert(ConfigBean user);

    void delete(Long userId);

    @SuppressWarnings("unchecked")
    List<ConfigBean> selectAll();

    List<ConfigBean> selectOnePage(Page page);

}
