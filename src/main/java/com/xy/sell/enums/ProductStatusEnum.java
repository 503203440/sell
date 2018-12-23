package com.xy.sell.enums;


import lombok.Getter;

/**
 * 商品状态
 */
@Getter
public enum ProductStatusEnum {

    下架(0,"下架"),
    在架(1,"在架"),
    ;

    private Integer code;
    private String message;

    //枚举构造
    ProductStatusEnum(Integer code,String message){
        this.code=code;

        this.message=message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
