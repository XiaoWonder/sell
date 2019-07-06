package com.imooc.service;

import com.imooc.entity.ProductCategory;

import java.util.List;

/**
 * @author 潇
 * @create 2019-06-24 19:05
 */
public interface CategoryService {
   ProductCategory findOne(Integer id);
   List<ProductCategory> findAll();
    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);
    ProductCategory save(ProductCategory productCategory);

}
