package com.xy.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xy.sell.dto.OrderDTO;
import com.xy.sell.entity.OrderDetail;
import com.xy.sell.enums.ResultEnum;
import com.xy.sell.exception.SellException;
import com.xy.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){

        Gson gson=new Gson();


        OrderDTO orderDTO=new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList=new ArrayList<>();

        try{
            orderDetailList=gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());

        }catch (Exception e){
            log.error("【对象转换】错误，json={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }




}
