package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.service.OrderService;
import com.imooc.service.PayService;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 潇
 * @create 2019-07-01 14:48
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PayServiceImplTest {

    @Autowired
    PayService payService;
    @Autowired
    OrderService orderService;
    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOne("3411711561626745850");
        payService.create(orderDTO);
    }

    @Test
    public void reFund() {
        OrderDTO orderDTO = orderService.findOne("9543301561626951856");
        payService.reFund(orderDTO);
    }
}