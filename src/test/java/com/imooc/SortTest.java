package com.imooc;

/**
 * @author 潇
 * @create 2019-07-01 17:32
 */
public class SortTest {

    public static void main(String[] args) {
        Integer[] arr={1,2,3};
        Integer avg = avg(arr);
        System.out.println(avg);
    }

    public static Integer avg(Integer[] arr) {
        int max =0;
        int min =0;
        int count = 0;
        for(int i =0; i<arr.length;i++){
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
        return count/(arr.length-2);
    }
}
