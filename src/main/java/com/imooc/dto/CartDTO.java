package com.imooc.dto;

import lombok.Data;

/**
 * @author 潇
 * @create 2019-06-26 15:05
 */
@Data
public class CartDTO {
    private String productId;
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
