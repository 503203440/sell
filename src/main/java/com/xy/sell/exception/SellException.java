package com.xy.sell.exception;

import com.xy.sell.enums.ResultEnum;

/**
 * 异常类
 */
public class SellException extends RuntimeException {

    private Integer code;

    //构造方法
    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());

        this.code=resultEnum.getCode();
    }

    //构造方法（2）
    public SellException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

}
