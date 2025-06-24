package com.msy.sharding.jdbc.demo.dao;

import com.msy.sharding.jdbc.demo.entity.Page;
import com.msy.sharding.jdbc.demo.entity.User;

import java.util.List;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:49
 * @desc
 */
public interface UserRepository {
    Long insert(User user);

    void delete(Long userId);

    @SuppressWarnings("unchecked")
    List<User> selectAll();

    List<User> selectOnePage(Page page);

    public List<User> selectBigThan(final Long userId);
}
