package com.imooc.dao;

import com.imooc.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 潇
 * @create 2019-06-24 18:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao categoryDao;

    @Test
    public void findOne(){
        ProductCategory productCategory = categoryDao.findOne(1);
        System.out.println(productCategory);
    }

    @Test
    public void findList(){
        List<Integer> list=new ArrayList<>();
        list.add(3);
        list.add(2);
        List<ProductCategory> byCategoryTypeIn = categoryDao.findByCategoryTypeIn(list);
        System.out.println(byCategoryTypeIn.size());
        Assert.assertNotEquals(0,1 );


    }
}