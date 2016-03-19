package com.huawei.subwaycharge;

import com.huawei.exam.ReturnCodeEnum;
import com.huawei.exam.ReturnCodeRst;

/**
 * <p>Title: 本类供考生调用，不允许更改</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class OpResult {

    /**
     * 创建结果对象，给客户端返回简单结果信息
     *
     *
     * @param errCode ReturnCodeEnum：返回码枚举
     *
     * @return OpResult：返回结果对象
     */
    public static OpResult createOpResult(ReturnCodeEnum errCode) {
        return new ReturnCodeRst(errCode);
    }

    /**
     * 创建结果对象，给客户端返回简单结果信息，返回请求扣费命令处理结果时调用
     *
     *
     * @param errCode ReturnCodeEnum：返回码枚举
     * @param cardNo int：卡号
     * @param moneyLeft int：余额
     *
     * @return OpResult：返回结果对象
     */
    public static OpResult createOpResult(ReturnCodeEnum returnCode, int cardNo, int moneyLeft) {
        return new ReturnCodeRst(returnCode, cardNo, moneyLeft);
    }

    /**
     * 创建结果对象，给客户端返回简单结果信息，查询扣费日志命令调用
     *
     *
     * @param errCode ReturnCodeEnum：返回码枚举
     * @param logs ChargeLogInfo[]：扣费日志信息
     *
     * @return OpResult：返回结果对象
     */
    public static OpResult createOpResult(ReturnCodeEnum returnCode, ChargeLogInfo[] logs) {
        return new ReturnCodeRst(returnCode, logs);
    }

    public abstract String toString();
}
