package com.imooc.service.impl;

import com.imooc.dao.ProductInfoDao;
import com.imooc.entity.ProductInfo;
import com.imooc.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 潇
 * @create 2019-06-24 19:56
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoService infoService;

    @Test
    public void findOne() {
        ProductInfo one = infoService.findOne("1");
        System.out.println(one);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = infoService.findUpAll();
        Assert.assertNotEquals(0, upAll.size());
    }

    @Test
    public void findAll() {
        Pageable pageable=new PageRequest(0, 5);
        Page<ProductInfo> page = infoService.findAll(pageable);
        long totalElements = page.getTotalElements();
        System.out.println(totalElements);
    }

    @Test
    public void save() {

    }
}