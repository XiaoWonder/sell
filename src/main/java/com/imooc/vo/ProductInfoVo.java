package com.imooc.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 潇
 * @create 2019-06-24 20:38
 */
@Data
public class ProductInfoVo {
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
