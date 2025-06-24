package com.msy.sharding.jdbc.demo.dao.impl;

import com.msy.sharding.jdbc.demo.dao.OrderRepository;
import com.msy.sharding.jdbc.demo.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:58
 * @desc
 */
@Repository
@Transactional
public class JpaOrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createTableIfNotExists() {
        throw new UnsupportedOperationException("createTableIfNotExists for JPA");
    }

    @Override
    public void truncateTable() {
        throw new UnsupportedOperationException("truncateTable for JPA");
    }

    @Override
    public void dropTable() {
        throw new UnsupportedOperationException("dropTable for JPA");
    }

    @Override
    public Long insert(final Order order) {
        entityManager.persist(order);
        return order.getOrderId();
    }

    @Override
    public void delete(final Long orderId) {
        Query query = entityManager.createQuery("DELETE FROM OrderEntity o WHERE o.orderId = ?1");
        query.setParameter(1, orderId);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> selectAll() {
        return (List<Order>) entityManager.createQuery("SELECT o FROM OrderEntity o").getResultList();
    }

    @Override
    public Order selectOne(final Long orderId) {
        return (Order) entityManager.createQuery("SELECT o FROM OrderEntity  o WHERE o.orderId = ?1")
                .setParameter(1, orderId)
                .getSingleResult();
    }
}
