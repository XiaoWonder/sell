package com.imooc.dao;

import com.imooc.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 潇
 * @create 2019-06-25 15:39
 */
public interface OrderDetailDao extends JpaRepository<OrderDetail,String> {
    List<OrderDetail> findByOrderId(String orderid);
}
