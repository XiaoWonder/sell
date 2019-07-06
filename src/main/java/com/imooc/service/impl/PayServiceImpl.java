package com.imooc.service.impl;

import com.imooc.config.WeChatPayConfig;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.OrderService;
import com.imooc.service.PayService;
import com.imooc.utils.JsonUtil;
import com.imooc.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 潇
 * @create 2019-07-01 10:54
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {
    @Autowired
    BestPayServiceImpl bestPayService;
    @Autowired
    OrderService orderService;
    private static final String ORDER_NAME="微信点餐订单";

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest=new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("微信支付:payRequest:{}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("微信支付:payResponse:{}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //验证签名
        //支付状态
        //支付金额
        //支付人(下单人==支付人)
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("微信支付,异步通知:{}", JsonUtil.toJson(payResponse));
        //修改订单的支付状态
        String orderId = payResponse.getOrderId();
        OrderDTO one = orderService.findOne(orderId);
        //判断订单是否存在
        if(one==null){
            log.error("微信支付回调,订单不存在,orderId={}", payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致
        if(!MathUtil.equals(one.getOrderAmount().doubleValue(), payResponse.getOrderAmount())){
            log.error("微信支付回调,订单金额不一致,orderId={},微信通知金额:{},系统金额:{}", payResponse.getOrderId(),payResponse.getOrderAmount(),one.getOrderAmount());
            throw new  SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        orderService.paid(one);
        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     */
    @Override
    public RefundResponse reFund(OrderDTO orderDTO) {
        RefundRequest refundRequest=new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("微信退款 request:{}", JsonUtil.toJson(refundRequest));
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("微信退款 response:{}", JsonUtil.toJson(refundResponse));
        return refundResponse;
    }
}
