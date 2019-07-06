package com.imooc.dao;

import com.imooc.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 潇
 * @create 2019-06-24 19:33
 */
public interface ProductInfoDao extends JpaRepository<ProductInfo,String> {
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
