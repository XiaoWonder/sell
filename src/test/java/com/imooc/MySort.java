package com.imooc;

/**
 * @author 潇
 * @create 2019-07-01 18:39
 */
public class MySort {
    static int max;
    static int min;
    public static void main(String[] args) {
        Integer[] arr={1,2,2,4};
        Integer avg = avg(arr);
        System.out.println(avg);
    }

    public static Integer avg(Integer[] arr){
        Integer sum=0;
        for(int i=0;i<arr.length;i++){
           if(arr[i]>=arr[0]){
             max=arr[i];
           }
           if(arr[i]<=arr[0]){
               min=arr[i];
           }
        }
        for(int i=0;i<arr.length;i++){
            if(arr[i]>min&&arr[i]<max){
                sum+=arr[i];
            }
        }
        return sum/(arr.length-2);
    }

}
