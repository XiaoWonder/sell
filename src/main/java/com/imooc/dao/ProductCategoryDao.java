package com.imooc.dao;

import com.imooc.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 潇
 * @create 2019-06-24 17:58
 */
public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer>{

    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);

}
