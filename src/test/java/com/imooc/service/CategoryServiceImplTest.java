package com.imooc.service;

import com.imooc.entity.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 潇
 * @create 2019-06-24 19:11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;
    @Test
    public void findOne(){
        ProductCategory one = categoryService.findOne(2);
        System.out.println(one);
    }
}