package com.imooc.service.impl;

import com.imooc.convertor.OrderMaster2OrderDTOConvertor;
import com.imooc.dao.OrderDetailDao;
import com.imooc.dao.OrderMasterDao;
import com.imooc.dto.CartDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.entity.OrderDetail;
import com.imooc.entity.OrderMaster;
import com.imooc.entity.ProductInfo;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.OrderService;
import com.imooc.service.PayService;
import com.imooc.service.ProductInfoService;
import com.imooc.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.w3c.dom.ls.LSException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 潇
 * @create 2019-06-26 12:52
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    ProductInfoService infoService;
    @Autowired
    OrderDetailDao detailDao;
    @Autowired
    OrderMasterDao orderMasterDao;
    @Autowired
    PayService payService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        List<CartDTO> list = new ArrayList<>();
        //查询商品(数量,价格)
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            ProductInfo productInfo = infoService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            detailDao.save(orderDetail);
            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
            list.add(cartDTO);
        }
        //写入订单数据库(ordermaster,orderdetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);
        //扣库存
        infoService.decreaseStock(list);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster one = orderMasterDao.findOne(orderId);
        if (one == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> detailList = detailDao.findByOrderId(orderId);
        if (detailList.size() == 0) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(one, orderDTO);
        orderDTO.setOrderDetailList(detailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> page = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderMaster> content = page.getContent();
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConvertor.convert(content);
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, page.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单的状态
        if (orderDTO.getOrderStatus() != OrderStatusEnum.NEW.getCode()) {
            log.error("取消订单:订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORRDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("取消订单:跟新失败:{}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("取消订单 订单中无商品详情:{}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> list = new ArrayList<>();
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        for(OrderDetail detail:orderDetailList){
            CartDTO cartDTO=new CartDTO(detail.getProductId(), detail.getProductQuantity());
            list.add(cartDTO);
        }
        infoService.increaseStock(list);
        //如果已支付,需要退款
        if(orderDTO.getOrderStatus()==PayStatusEnum.SUCCESS.getCode()){
            payService.reFund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(orderDTO.getOrderStatus()!=OrderStatusEnum.NEW.getCode()){
            log.error("订单状态不正确 orderid={},orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORRDER_STATUS_ERROR);
        }
        //修改状态
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("完结订单:跟新失败:{}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(orderDTO.getOrderStatus()!=OrderStatusEnum.NEW.getCode()){
            log.error("订单状态不正确 orderid={},orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORRDER_STATUS_ERROR);
        }
        //判断支付状态
        if(orderDTO.getPayStatus()!=PayStatusEnum.WAIT.getCode()){
            log.error("订单支付状态不正确 orderid={},orderStatus={}", orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("订单支付完成:跟新失败:{}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
