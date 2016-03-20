package com.huawei.subwaycharge;

public enum ReturnCodeEnum
{
  I00, I10, I11, I12, I13, I20, I22 , I30, E01, E02, E21,E22;

  private static final String[] ERR_CODE_STR;

  static
  {
    ERR_CODE_STR = new String[] { 
      "I00:欢迎使用地铁收费系统", 
      "I10:扣费失败(无效路线)", 
      "I11:扣费成功", 
      "I12:扣费成功(余额过低)", 
      "I13:扣费失败(余额不足)", 
      "I20:查询成功", 
      "I22:票卡注销成功", 
      "I30:查询消费成功", 
      "E01:非法命令", 
      "E02:参数错误(时间关系错误)", 
      "E21:查询失败(无相应记录)",
      "E22:操作失败，此票卡已注销"};
  }

  public String toString()
  {
    return ERR_CODE_STR[ordinal()];
  }
}