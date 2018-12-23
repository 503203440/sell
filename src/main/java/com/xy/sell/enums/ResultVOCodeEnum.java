package com.xy.sell.enums;

/**
 * 返回信息代码枚举类
 */
public enum ResultVOCodeEnum {
    SUCCESS(0, "成功"), ERROR(1, "失败"),
    ;

    private Integer code;
    private String msg;


    ResultVOCodeEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
