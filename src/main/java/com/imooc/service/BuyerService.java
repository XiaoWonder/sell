package com.imooc.service;

import com.imooc.dto.OrderDTO;

/**
 * @author 潇
 * @create 2019-06-27 20:25
 */
public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderId);
    //取消订单
    OrderDTO cancelOrder(String openid,String orderId);
}
