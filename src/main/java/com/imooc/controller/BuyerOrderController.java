package com.imooc.controller;

import com.imooc.convertor.OderForm2OrderDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import com.imooc.utils.ResultVoUtil;
import com.imooc.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 潇
 * @create 2019-06-27 13:29
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    BuyerService buyerService;

    //创建订单
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult result){
        Map<String, String> map = new HashMap<>();
            if (result.hasErrors()) {
                log.error("创建订单:参数不正确,orderForm={}", orderForm);
                throw new SellException(ResultEnum.PARAM_ERROR.getCode(), result.getFieldError().getDefaultMessage());
            }
            OrderDTO orderDTO = OderForm2OrderDTO.convert(orderForm);
            if (orderDTO.getOrderDetailList().size() == 0) {
                log.error("购物车不能为空");
                throw new SellException(ResultEnum.CART_EMPTY);
            }
            OrderDTO createResult = orderService.create(orderDTO);
            map.put("orderId", createResult.getOrderId());

        return ResultVoUtil.success(map);
    }

    //订单列表
    @GetMapping(value = "/list")
    public ResultVo<List<OrderDTO>> list(@RequestParam("openid")String openid,
                                         @RequestParam(value = "page",defaultValue = "0")Integer page,
                                         @RequestParam(value = "size",defaultValue = "5")Integer size){
         if(StringUtils.isEmpty(openid)){
           log.error("查询订单列表,openid为空");
           throw new SellException(ResultEnum.PARAM_ERROR);
         }
        PageRequest pageRequest=new PageRequest(page, size);
        Page<OrderDTO> dtoPage = orderService.findList(openid, pageRequest);
        List<OrderDTO> content = dtoPage.getContent();
        return ResultVoUtil.success(content);
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVo<OrderDTO> detail(@RequestParam("openid")String openid,
                                     @RequestParam("orderId")String orderId){

        OrderDTO one = buyerService.findOrderOne(openid, orderId);
        return ResultVoUtil.success(one);
    }

    //取消订单
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    public ResultVo cancel(@RequestParam("openid")String openid,
                           @RequestParam("orderId")String orderId){
        buyerService.cancelOrder(openid, orderId);
        return ResultVoUtil.success();
    }

    public void test(){
        System.out.println("潇");
    }

}
