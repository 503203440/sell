package com.xy.sell.enums;

import lombok.Getter;

/**
 * 返回信息枚举类
 */
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_UNDERSTOCK(20, "商品库存不足"),
    ORDER_NOT_EXIST(30, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(40, "订单详情不存在"),
    ORDER_STUTAS_ERROR(50, "订单状态不正确"),
    ORDER_UPDATE_FAIL(60, "订单更新失败"),
    ORDER_DETAIL_EMPTY(70, "订单详情为空"),
    ORDER_PAY_STATUS_ERROR(80, "订单支付状态不正确"),
    PARAM_ERROR(90, "参数不正确"),
    CART_EMPTY(100,"购物车不能为空"),
    ORDER_OWNER_ERROR(110,"该订单不属于当前用户"),
    ;


    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
