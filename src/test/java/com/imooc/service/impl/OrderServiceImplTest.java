package com.imooc.service.impl;

import com.imooc.dto.CartDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.entity.OrderDetail;
import com.imooc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 潇
 * @create 2019-06-26 17:24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServiceImplTest {

    private final String OPENID="1127xiao";
    private final String ORDERID="8442211561544007585";
    @Autowired
    OrderService orderService;
    @Test
    public void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerAddress("慕课网");
        orderDTO.setBuyerName("潇哥");
        orderDTO.setBuyerOpenid(OPENID);
        orderDTO.setBuyerPhone("1582756");
        //购物车
        List<OrderDetail> list=new ArrayList<>();
        OrderDetail detail=new OrderDetail();
        detail.setProductId("1");
        detail.setProductQuantity(10);
        list.add(detail);
        orderDTO.setOrderDetailList(list);
        OrderDTO result = orderService.create(orderDTO);
        log.info("创建订单:result={}",result);
    }

    @Test
    public void findOne() {
        OrderDTO one = orderService.findOne(ORDERID);
        log.info("查看订单:info={}",one);
    }

    @Test
    public void findList() {
        PageRequest request=new PageRequest(0, 3);
        Page<OrderDTO> list = orderService.findList(OPENID,request );
        log.info("查看订单:info={}",list.getContent());
    }

    @Test
    public void cancel() {
        OrderDTO one = orderService.findOne(ORDERID);
        OrderDTO orderDTO = orderService.cancel(one);
        log.info("取消订单:info={}",orderDTO );
    }

    @Test
    public void finish() {
        OrderDTO one = orderService.findOne(ORDERID);
        OrderDTO orderDTO = orderService.finish(one);
        log.info("完结订单:info={}",orderDTO );
    }

    @Test
    public void paid() {
        OrderDTO one = orderService.findOne(ORDERID);
        OrderDTO orderDTO = orderService.paid(one);
        log.info("支付订单:info={}",orderDTO );
    }
}