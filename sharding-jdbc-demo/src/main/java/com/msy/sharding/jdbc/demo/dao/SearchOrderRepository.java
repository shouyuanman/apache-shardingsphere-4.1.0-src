package com.msy.sharding.jdbc.demo.dao;

import com.msy.sharding.jdbc.demo.entity.jpa.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author shouyuanman@lexin.com
 * @date 2025/6/24 15:49
 * @desc
 */
@Repository
public interface SearchOrderRepository extends JpaRepository<OrderEntity, Long>, JpaSpecificationExecutor {
    /**
     * 根据用户查询 order
     *
     * @return
     */
    @Query(nativeQuery = true,
            value = "SELECT a.* FROM `t_order` a left join `t_user` b on a.user_id=b.user_id  where  a.user_id=?1")
    List<OrderEntity> selectOrderOfUserId(long userId);
    /**
     * 根据用户查询 order
     *
     * @return
     */
    @Query(nativeQuery = true,
            value = "SELECT a.* FROM `t_order` a left join `t_user` b on a.user_id=b.user_id ")
    List<OrderEntity> selectOrderOfUser();

    /**
     * 根据用户 删除 order
     *
     * @return
     */

    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(nativeQuery = true,
            value = "delete from t_order where  `timestamp` <= curdate() - interval 3 month")
    void deleteLogOfThreeMonth();


    @Query(nativeQuery = true,
            value = "select * from t_order" +
                    "where user_id  BETWEEN ?1 and ?2")
    List<OrderEntity> selectOrderByUserIdInRange(long low, long high);

    @Query(nativeQuery = true,
            value = "select * from t_order" +
                    "where user_id  in  (:ids)")
    List<OrderEntity> selectOrderByUserIdInList(@Param("ids") List<Long> ids);

}
