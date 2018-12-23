package com.xy.sell.entity;

import com.xy.sell.enums.OrderStatusEnum;
import com.xy.sell.enums.PayStatusEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 */
@Entity
@Data
public class OrderMaster {

    //订单id
    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    //支付状态（默认0，等待支付）
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;


}
