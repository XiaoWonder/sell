package com.imooc.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品 包含类目
 * @author 潇
 * @create 2019-06-24 20:34
 */
@Data
public class ProductVo {
    @JsonProperty("name")
    private String categroyName;
    @JsonProperty("type")
    private Integer categroyType;
    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVoList;
}
