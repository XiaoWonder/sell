package com.imooc.dao;

import com.imooc.entity.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 潇
 * @create 2019-06-24 19:37
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductInfoDaoTest {

    @Autowired
    private ProductInfoDao infoDao;
    @Test
    public void findByProductStatus() {
        List<ProductInfo> list = infoDao.findByProductStatus(0);
        System.out.println(list);
    }
}