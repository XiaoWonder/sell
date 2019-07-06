package com.imooc.controller;

import com.imooc.entity.ProductCategory;
import com.imooc.entity.ProductInfo;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductInfoService;
import com.imooc.utils.ResultVoUtil;
import com.imooc.vo.ProductInfoVo;
import com.imooc.vo.ProductVo;
import com.imooc.vo.ResultVo;
import com.sun.org.apache.xerces.internal.xs.LSInputList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 买家商品
 * @author 潇
 * @create 2019-06-24 20:19
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductInfoService infoService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVo list(){
        //查询所有上架的商品
        List<ProductInfo> upAll = infoService.findUpAll();

        //查询类目(一次性查询)
        List<Integer> categroyList=new ArrayList<>();
        for(ProductInfo info:upAll){
            categroyList.add(info.getCategoryType());
        }
        List<ProductCategory> categoryType= categoryService.findByCategoryTypeIn(categroyList);
        //数据拼装
        List<ProductVo> productVoList=new ArrayList<>();
        for(ProductCategory category:categoryType){
            ProductVo productVo=new ProductVo();
            productVo.setCategroyType(category.getCategoryType());
            productVo.setCategroyName(category.getCategoryName());

            List<ProductInfoVo> infoVoList=new ArrayList<>();
            for(ProductInfo productInfo:upAll){
                if(productInfo.getCategoryType()==category.getCategoryType()){
                    ProductInfoVo infoVo=new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, infoVo);
                    infoVoList.add(infoVo);
                }
            }
            productVo.setProductInfoVoList(infoVoList);
            productVoList.add(productVo);
        }
        ResultVo vo = ResultVoUtil.success(productVoList);
        return vo;
    }


}
