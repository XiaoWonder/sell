package com.imooc.service;

import com.imooc.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * @author 潇
 * @create 2019-07-01 10:53
 */
public interface PayService {
    PayResponse create(OrderDTO orderDTO);
    PayResponse notify(String notifyData);
    RefundResponse reFund(OrderDTO orderDTO);
}
