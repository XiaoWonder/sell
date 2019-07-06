package com.imooc.enums;

import lombok.Getter;

/**
 * 商品状态枚举
 * @author 潇
 * @create 2019-06-24 19:49
 */
@Getter
public enum ProductInfoStatusEnum {
    up(0,"在架"),
    down(1,"下架");
    private Integer code;
    private String message;
    ProductInfoStatusEnum(Integer code, String message){
        this.code=code;
        this.message=message;
    }
}
