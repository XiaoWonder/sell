package com.imooc;

import java.util.Arrays;

/**
 * @author 潇
 * @create 2019-07-01 19:27
 */
public class SimpleSort {
    public static void main(String[] args) {
        double[]    a={5.2,6.7,5.8,6.9};//定义数组（评分）
        Arrays.sort(a);//对数组（评分）进行排序
        a=Arrays.copyOfRange(a, 1, a.length-1);//去掉最高分最低分
        double  sum = 0;//定义总分变量
        //循环累加评分
        for(int i=0;i<a.length;i++){
            System.out.println(a[i]);//打印去掉最高分最低分后的新数
            sum+=a[i];
        }
        System.out.println("平均分为"+sum/a.length);//打印平均分
    }
}
