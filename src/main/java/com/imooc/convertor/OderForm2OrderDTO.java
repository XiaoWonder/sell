package com.imooc.convertor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.dto.OrderDTO;
import com.imooc.entity.OrderDetail;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * @author 潇
 * @create 2019-06-27 16:25
 */
@Slf4j
public class OderForm2OrderDTO {
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson=new Gson();

        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        try {
            List<OrderDetail> list=gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
            orderDTO.setOrderDetailList(list);
        }catch (Exception e){
            log.error("对象转换错误:{}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return orderDTO;
    }
}
