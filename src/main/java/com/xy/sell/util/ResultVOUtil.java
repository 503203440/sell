package com.xy.sell.util;

import com.xy.sell.VO.ResultVO;
import com.xy.sell.enums.ResultVOCodeEnum;

/**
 * 封装返回值数据信息
 */
public class ResultVOUtil {

    //查询成功并设置数据集
    public static ResultVO success(Object object){
        ResultVO resultVO=new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg("success");
        resultVO.setCode(ResultVOCodeEnum.SUCCESS.getCode());
        return resultVO;

    }

    //执行成功，不返回数据集
    public static ResultVO success(){
        return success(null);
    }

    //失败
    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }


}
