package com.huawei.loan;

/**
 * <p>
 * Title: 考生可以根据自己的需求在本类中增加变量和函数
 * </p>
 * <p>
 * Description: 常量定义
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unknown
 * @version 1.0
 */
public class Constants 
{
    // 贷款编号的边界值
    public final static int LOANID_MIN = 1;
    public final static int LOANID_MAX = 10;

    // 月收入的边界值
    // 低收入的最小值
    public final static int INCOME_LOW_MIN = 1000;
    // 低等收入的最大值
    public final static int INCOME_LOW_MAX = 5000;
    // 高等收入的最小值
    public final static int INCOME_HIGH_MIN = 5001;
    // 高等收入的最大值
    public final static int INCOME_HIGH_MAX = 10000;

    // 贷款年限的边界值
    // 最小值
    public final static int INSTALLMENTS_MIN = 12;
    // 低收入人群年限的最大值
    public final static int INSTALLMENTS_LOW_MAX = 120;
    // 低收入人群年限的最大值
    public final static int INSTALLMENTS_HIGH_MAX = 240;
    // 年限的利率折扣拐点
    public final static int INSTALLMENTS_RATE_DISCOUNT = 120;

    // 贷款金额的边界值
    // 最小值
    public final static int AMOUNT_MIN = 100000;
    // 低收入人群金额的最大值
    public final static int AMOUNT_LOW_MAX = 500000;
    // 低收入人群金额的最大值
    public final static int AMOUNT_HIGH_MAX = 1000000;
    // 贷款折扣的拐点
    public final static int AMOUNT_RATE_DISCOUNT = 400000;

    // 申请时间的边界值
    public final static int APPLY_TIME_MIN = 1;
    public final static int APPLY_TIME_MAX = 10;

    // 查询时间的边界值
    public final static int QUERY_TIME_MIN = 1;
    public final static int QUERY_TIME_MAX = 24;

    // 年利率的种类
    public final static double LOAN_RATE_TYPE_0_24 = 0.024;
    public final static double LOAN_RATE_TYPE_0_48 = 0.048;

    // 利率的折扣类型
    public final static double LOAD_RATE_DISCOUNT_TYPE_0_5 = 0.5;

    // 银行的贷款限额
    public static int BANK_LOAN_AMOUNT_LIMIT = 3000000;
    // 银行能够发生贷款的份数限制
    public static int BANK_LOAN_COUNT_LIMIT = 5;
}
