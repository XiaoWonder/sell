package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 潇
 * @create 2019-06-27 20:27
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    OrderService orderService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO one = checkOrderOwner(openid, orderId);
        return one;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO one = checkOrderOwner(openid, orderId);
        if(one==null){
            log.error("取消订单。。。查不到该订单orderid={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(one);
    }


    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO one = orderService.findOne(orderId);
        if(one==null){
            return null;
        }
        //判断是否是自己的订单
        if(!one.getOrderId().equals(openid)){
            log.error("查询订单,订单的openid不一致:{},orderDTO={}", openid,one);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return one;
    }
}
