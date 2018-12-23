package com.xy.sell.service.impl;

import com.xy.sell.dto.OrderDTO;
import com.xy.sell.enums.ResultEnum;
import com.xy.sell.exception.SellException;
import com.xy.sell.service.BuyerService;
import com.xy.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    /**
     * 查询一个订单
     *
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    /**
     * 取消一个订单
     *
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO==null){
            log.error("【取消订单】查询不到该订单，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }


    private OrderDTO checkOrderOwner(String openid, String orderId){

        OrderDTO orderDTO=orderService.findOne(orderId);

        if(orderDTO==null){
            return null;
        }
        //判断是否是自己的订单
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】订单openid不一致，openid={}，orderDTO={}",openid,orderDTO.toString());
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        return orderDTO;

    }



}
