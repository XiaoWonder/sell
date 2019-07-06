package com.imooc.service;

import com.imooc.dto.OrderDTO;
import com.imooc.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author 潇
 * @create 2019-06-26 12:40
 */
public interface OrderService {
    OrderDTO create(OrderDTO orderDTO);
    OrderDTO findOne(String orderId);
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);
    OrderDTO cancel(OrderDTO orderDTO);
    OrderDTO finish(OrderDTO orderDTO);
    OrderDTO paid(OrderDTO orderDTO);
}
