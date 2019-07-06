package com.imooc.utils;

import java.util.Random;

/**
 * @author 潇
 * @create 2019-06-26 13:28
 */
public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式:时间+随机数
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random=new Random();
        Integer number=random.nextInt(900000)+100000;
        return String.valueOf(number)+System.currentTimeMillis();
    }
}
