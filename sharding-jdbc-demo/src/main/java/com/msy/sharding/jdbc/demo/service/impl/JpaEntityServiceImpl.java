package com.msy.sharding.jdbc.demo.service.impl;

import com.msy.sharding.jdbc.demo.dao.ConfigRepository;
import com.msy.sharding.jdbc.demo.dao.OrderRepository;
import com.msy.sharding.jdbc.demo.dao.SearchOrderRepository;
import com.msy.sharding.jdbc.demo.dao.UserRepository;
import com.msy.sharding.jdbc.demo.entity.ConfigBean;
import com.msy.sharding.jdbc.demo.entity.Order;
import com.msy.sharding.jdbc.demo.entity.Page;
import com.msy.sharding.jdbc.demo.entity.User;
import com.msy.sharding.jdbc.demo.entity.jpa.ConfigEntity;
import com.msy.sharding.jdbc.demo.entity.jpa.OrderEntity;
import com.msy.sharding.jdbc.demo.entity.jpa.UserEntity;
import com.msy.sharding.jdbc.demo.service.JpaEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:55
 * @desc
 */
@Service
@Transactional
public class JpaEntityServiceImpl implements JpaEntityService {
    @Resource
    private OrderRepository orderRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private SearchOrderRepository searchOrderRepository;
    @Resource
    private ConfigRepository configRepository;

    @Override
    public void initEnvironment() {
        orderRepository.createTableIfNotExists();
        orderRepository.truncateTable();
    }

    @Override
    public void cleanEnvironment() {
        orderRepository.dropTable();
    }

    @Transactional
    @Override
    public void addOrder(Order order) {
        Order orderEntity = new OrderEntity();
        orderEntity.setUserId(order.getUserId());
        orderEntity.setStatus("NotPayed");
        orderRepository.insert(orderEntity);
    }

    @Override
    public Order selectOneOrder(Long orderId) {
        return orderRepository.selectOne(orderId);
    }

    @Override
    public List<Order> selectAOrderByUserId(long userId) {

        List<Order> r = new ArrayList<>();

        List<OrderEntity> temp = searchOrderRepository.selectOrderOfUserId(userId);

        r.addAll(temp);
        return r;
    }

    @Override
    public List<Order> selectOrderOfUser() {
        List<Order> r = new ArrayList<>();

        List<OrderEntity> temp = searchOrderRepository.selectOrderOfUser();

        r.addAll(temp);
        return r;
    }

    @Override
    public List<Order> selectOrderByUserIdInRange(long low, long high) {

        List<Order> r = new ArrayList<>();

        List<OrderEntity> temp = searchOrderRepository.selectOrderByUserIdInRange(low, high);

        r.addAll(temp);
        return r;
    }

    @Override
    public List<Order> selectOrderByUserIdInList(List<Long> ids) {

        List<Order> r = new ArrayList<>();

        List<OrderEntity> temp = searchOrderRepository.selectOrderByUserIdInList(ids);

        r.addAll(temp);
        return r;
    }

    //增加用户
    public void addUser(User dto) {
        User userEntity = new UserEntity();
        userEntity.setUserId(dto.getUserId());
        userEntity.setName(dto.getName());
        Long userId = userRepository.insert(userEntity);
        dto.setUserId(userId);
    }

    //查询全部用户
    public List<User> selectAllUser() {
        return userRepository.selectAll();
    }

    //查询分页用户
    public List<User> selectUser(Page page) {

        return userRepository.selectOnePage(page);
    }

    public List<User> selectBigThan(final Long userId) {

        return userRepository.selectBigThan(userId);
    }

    @Override
    public List<ConfigBean> selectAllConfigBean() {
        return configRepository.selectAll();

    }

    @Override
    public List<ConfigBean> selectConfigBean(Page page) {
        return configRepository.selectOnePage(page);
    }

    @Override
    public void addConfigBean(ConfigBean dto) {
        ConfigEntity entity = new ConfigEntity();
        entity.setId(dto.getId());
        entity.setStatus(dto.getStatus());
        configRepository.insert(entity);

    }

    @Transactional
    @Override
    public void processSuccess() {
        System.out.println("-------------- Process Success Begin ---------------");
        List<Long> orderIds = insertData();
        selectAllOrder();
        deleteData(orderIds);
        selectAllOrder();
        System.out.println("-------------- Process Success Finish --------------");
    }

    @Transactional
    @Override
    public void processFailure() {
        System.out.println("-------------- Process Failure Begin ---------------");
        insertData();
        System.out.println("-------------- Process Failure Finish --------------");
        throw new RuntimeException("Exception occur for transaction test.");
    }

    private List<Long> insertData() {
        System.out.println("---------------------------- Insert Data ----------------------------");
        List<Long> result = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            Order order = new OrderEntity();
            order.setUserId(i);
            order.setStatus("INSERT_TEST");
            orderRepository.insert(order);
            result.add(order.getOrderId());
        }
        return result;
    }

    @Transactional
    private void deleteData(final List<Long> orderIds) {
        System.out.println("---------------------------- Delete Data ----------------------------");
        for (Long each : orderIds) {
            orderRepository.delete(each);
        }
    }

    @Override
    public List<Order> selectAllOrder() {

        return orderRepository.selectAll();
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @Override
    public void localTransactionDemo() {
        for (int i = 0; i < 2; i++) {

            User user = new User();

            user.setName("user_" + i);
            //增加用户
            addUser(user);

            Order dto = new Order();

            dto.setUserId(user.getUserId());

            //增加用户
            addOrder(dto);

        }
        int j=1/0;

    }
}
