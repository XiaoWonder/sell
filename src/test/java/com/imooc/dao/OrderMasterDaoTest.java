package com.imooc.dao;

import com.imooc.entity.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 潇
 * @create 2019-06-26 12:32
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMasterDaoTest {

    @Autowired
    OrderMasterDao orderMasterDao;
    @Test
    public void findByPage(){
        PageRequest pageRequest=new PageRequest(0, 3);
        Page<OrderMaster> byBuyerOpenid = orderMasterDao.findByBuyerOpenid("1127", pageRequest);
        System.out.println(byBuyerOpenid.getContent().size());
    }
}