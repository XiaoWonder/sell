package com.imooc.service.impl;

import com.imooc.dao.ProductInfoDao;
import com.imooc.dto.CartDTO;
import com.imooc.entity.ProductInfo;
import com.imooc.enums.ProductInfoStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 潇
 * @create 2019-06-24 19:46
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoDao infoDao;
    @Override
    public ProductInfo findOne(String id) {
        return infoDao.findOne(id);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return infoDao.findByProductStatus(ProductInfoStatusEnum.up.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return infoDao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return infoDao.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> list) {
      for(CartDTO cartDTO:list){
          ProductInfo productInfo=infoDao.findOne(cartDTO.getProductId());
          if(productInfo==null){
              throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
          }
          Integer result=productInfo.getProductStock()+cartDTO.getProductQuantity();
          productInfo.setProductStock(result);
          infoDao.save(productInfo);
      }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> list) {
        for(CartDTO cartDTO:list){
            ProductInfo info = infoDao.findOne(cartDTO.getProductId());
            if(info==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=info.getProductStock()-cartDTO.getProductQuantity();
            if(result<0){
                //库存不足
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            info.setProductStock(result);
            infoDao.save(info);
        }
    }
}
