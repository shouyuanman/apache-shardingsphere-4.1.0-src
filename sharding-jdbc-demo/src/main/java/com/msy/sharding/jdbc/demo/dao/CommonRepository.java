package com.msy.sharding.jdbc.demo.dao;

import java.util.List;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:50
 * @desc
 */
public interface CommonRepository<T> {

    void createTableIfNotExists();

    void dropTable();

    void truncateTable();

    Long insert(T entity);

    void delete(Long id);

    List<T> selectAll();
}
