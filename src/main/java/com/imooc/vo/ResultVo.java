package com.imooc.vo;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @author 潇
 * @create 2019-06-24 20:24
 */
@Data
public class ResultVo<T> {

    private Integer code;
    private String msg;
    private T data;

}
