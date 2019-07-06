package com.imooc.service.impl;

import com.imooc.dao.ProductCategoryDao;
import com.imooc.entity.ProductCategory;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 潇
 * @create 2019-06-24 19:07
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryDao categoryDao;
    @Override
    public ProductCategory findOne(Integer id) {
        return categoryDao.findOne(id);
    }

    @Override
    public List<ProductCategory> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> list) {
        return categoryDao.findByCategoryTypeIn(list);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return categoryDao.save(productCategory);
    }
}
