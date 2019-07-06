package com.imooc.service;

import com.imooc.dto.CartDTO;
import com.imooc.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 潇
 * @create 2019-06-24 19:42
 */
public interface ProductInfoService {
    ProductInfo findOne(String id);
    List<ProductInfo> findUpAll();
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo);
    void increaseStock(List<CartDTO> list);
    void decreaseStock(List<CartDTO> list);
}
