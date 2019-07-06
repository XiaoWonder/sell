package com.imooc.convertor;

import com.imooc.dto.OrderDTO;
import com.imooc.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 潇
 * @create 2019-06-26 19:27
 */
public class OrderMaster2OrderDTOConvertor {
    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> list){
        List<OrderDTO> orderDTOList=new ArrayList<>();
            for(OrderMaster orderMaster:list){
                OrderDTO orderDTO=new OrderDTO();
                BeanUtils.copyProperties(orderMaster, orderDTO);
                orderDTOList.add(orderDTO);
            }
        return orderDTOList;
    }
}
