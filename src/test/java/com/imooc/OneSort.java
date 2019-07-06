package com.imooc;

/**
 * @author 潇
 * @create 2019-07-01 19:55
 */
public class OneSort {

    public static void main(String[] args) {
        Integer[] arr={1,6,4,2};
        double avg = avg(arr);
        System.out.println(avg);
    }
    public static Double avg(Integer[] arr) {
        int max = arr[0]>arr[1]?arr[0]:arr[1];
        int min = arr[0]>arr[1]?arr[1]:arr[0];
        int count = 0;
        for(int i =2; i<arr.length;i++){
            if(arr[i]>max){
                count += max;
                max = arr[i];
                continue;
            }
            if(arr[i]<min){
                count += min;
                min = arr[i];
                continue;
            }
            count += arr[i];
        }
        return count/(Double.valueOf(arr.length-2));
    }
}


