package com.imooc.exception;

import com.imooc.enums.ResultEnum;

/**
 * @author 潇
 * @create 2019-06-26 13:05
 */
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }

    public SellException(int code,String message){
        super(message);
        this.code=code;
    }
}
