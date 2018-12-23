package com.xy.sell.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xy.sell.entity.OrderDetail;
import com.xy.sell.enums.OrderStatusEnum;
import com.xy.sell.enums.PayStatusEnum;
import com.xy.sell.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    //订单id
    private String orderId;
    //买家名字
    private String buyerName;
    //买家电话
    private String buyerPhone;
    //买家地址
    private String buyerAddress;
    //买家微信openid
    private String buyerOpenid;
    //订单总金额
    private BigDecimal orderAmount;
    //订单状态（默认0，新下单）
    private Integer orderStatus;
    //支付状态（默认0，等待支付）
    private Integer payStatus;
    //创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    //更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> OrderDetailList;

}
