package com.imooc.dao;

import com.imooc.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 潇
 * @create 2019-06-25 15:34
 */
public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {
    Page<OrderMaster> findByBuyerOpenid(String openid, Pageable pageable);
}
