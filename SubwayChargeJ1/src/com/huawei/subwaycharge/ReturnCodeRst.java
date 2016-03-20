package com.huawei.subwaycharge;

import com.huawei.subwaycharge.ChargeLogInfo;
import com.huawei.subwaycharge.OpResult;

import java.util.ArrayList;
import java.util.List;

public class ReturnCodeRst extends OpResult
{
  private ReturnCodeEnum errCode = null;
  private static final int MAX_LOG_NUM = 10;
  private int cardID = 0;
  private int moneyLeft = 0;
  private List<ChargeLogInfo> chargedList = new ArrayList();
  private CardTypeEnum cate;

  public ReturnCodeRst(ReturnCodeEnum errCode)
  {
    this.errCode = errCode;
  }

  public ReturnCodeRst(ReturnCodeEnum returnCode, int cardNo, int moneyLeft)
  {
    this.errCode = returnCode;
    this.cardID = cardNo;
    this.moneyLeft = moneyLeft;
  }

  public ReturnCodeRst(ReturnCodeEnum returnCode, ChargeLogInfo[] logs)
  {
    this.errCode = returnCode;
    this.chargedList.clear();

    for (int i = 0; (i < 10) && (i < logs.length); ++i)
    {
      this.chargedList.add(logs[i]);
    }
  }

  public ReturnCodeRst(ReturnCodeEnum returnCode, ChargeLogInfo[] logs,CardTypeEnum cate)
  {
    this.errCode = returnCode;
    this.chargedList.clear();
    this.cate=cate;

    for (int i = 0; (i < 10) && (i < logs.length); ++i)
    {
      this.chargedList.add(logs[i]);
    }
  }
  
  
  
  public String toString()
  {
    StringBuffer sb;
    if (this.errCode == null)
    {
      return "";
    }

    if (ReturnCodeEnum.I00 == this.errCode)
    {
      return "I00:欢迎使用地铁收费系统";
    }

    if (ReturnCodeEnum.E01 == this.errCode)
    {
      return "E01:非法命令";
    }
    if (ReturnCodeEnum.E02 == this.errCode)
    {
      return "E02:参数错误(时间关系错误)";
    }
    if (ReturnCodeEnum.E21 == this.errCode)
    {
      return "E21:查询失败(无相应记录)";
    }
    if (ReturnCodeEnum.E22 == this.errCode)
    {
      return "E22:操作失败，此票卡已注销";
    }
    if (ReturnCodeEnum.I10 == this.errCode)
    {
      sb = new StringBuffer();
      sb.append("I10:扣费失败(无效路线)\r\n");
      sb.append("<卡号=").append(this.cardID).append(">");
      sb.append("<余额=").append(this.moneyLeft).append(">");
      return sb.toString();
    }
    if (ReturnCodeEnum.I11 == this.errCode)
    {
      sb = new StringBuffer();
      sb.append("I11:扣费成功\r\n");
      sb.append("<卡号=").append(this.cardID).append(">");
      sb.append("<余额=").append(this.moneyLeft).append(">");
      return sb.toString();
    }
    if (ReturnCodeEnum.I12 == this.errCode)
    {
      sb = new StringBuffer();
      sb.append("I12:扣费成功(余额过低)\r\n");
      sb.append("<卡号=").append(this.cardID).append(">");
      sb.append("<余额=").append(this.moneyLeft).append(">");
      return sb.toString();
    }
    if (ReturnCodeEnum.I22 == this.errCode)
    {
      sb = new StringBuffer();
      sb.append("I22:票卡注销成功\r\n");
      sb.append("<卡号=").append(this.cardID).append(">");
      sb.append("<余额=").append(this.moneyLeft).append(">");
      return sb.toString();
    }
    if (ReturnCodeEnum.I13 == this.errCode)
    {
      sb = new StringBuffer();
      sb.append("I13:扣费失败(余额不足)\r\n");
      sb.append("<卡号=").append(this.cardID).append(">");
      sb.append("<余额=").append(this.moneyLeft).append(">");
      return sb.toString();
    }
    if (ReturnCodeEnum.I20 == this.errCode)
    {
      sb = new StringBuffer();
      String rstLogStr = "";
      sb.append("I20:查询成功\r\n");
      if (this.chargedList == null)
      {
        rstLogStr = "";
      }
      else
      {
        rstLogStr = getLogInfoStr();
      }
      sb.append(rstLogStr);
      return sb.toString();
    }
    
    if (ReturnCodeEnum.I30 == this.errCode)
    {
      sb = new StringBuffer();
      String rstLogStr = "";
      sb.append("I30:查询消费成功\r\n");
      if (this.chargedList == null)
      {
        rstLogStr = "";
      }
      else
      {
        rstLogStr = getLogInfoStr2();
      }
      sb.append(rstLogStr);
      return sb.toString();
    }
    return "";
  }

  private String getLogInfoStr()
  {
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < this.chargedList.size(); ++i)
    {
      ChargeLogInfo cl = (ChargeLogInfo)this.chargedList.get(i);
      sb.append("<").append(cl.getCardNo()).append(">");
      sb.append("<").append(String.format("%02d", new Object[] { Integer.valueOf(cl.getInHour()) })).append(":").append(String.format("%02d", new Object[] { Integer.valueOf(cl.getInMinute()) })).append(">");
      sb.append("<").append(cl.getInStation()).append(">");
      sb.append("<").append(String.format("%02d", new Object[] { Integer.valueOf(cl.getOutHour()) })).append(":").append(String.format("%02d", new Object[] { Integer.valueOf(cl.getOutMinute()) })).append(">");
      sb.append("<").append(cl.getOutStation()).append(">");
      sb.append("<").append(cl.getChargeCount()).append(">");
      if (cl.isCharged())
      {
        sb.append("<").append("成功").append(">");
      }
      else
      {
        sb.append("<").append("失败").append(">");
      }
      sb.append("\r\n");
    }
    return sb.toString();
  }
  
  
  
  
  private String getLogInfoStr2()
  {
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < this.chargedList.size(); ++i)
    {
      ChargeLogInfo cl = (ChargeLogInfo)this.chargedList.get(i);
      sb.append("<").append("历史消费记录").append("> ");
      sb.append("卡号：").append(cl.getCardNo()).append(" ");
      sb.append("卡类别：");
      if(cate==CardTypeEnum.A)
    	  sb.append("单程票 ");
      else if(cate==CardTypeEnum.B)
    	  sb.append("老年卡 ");
      else
    	  sb.append("普通卡 ");
      sb.append("进站时间：").append(String.format("%02d", new Object[] { Integer.valueOf(cl.getInHour()) })).append(":").append(String.format("%02d", new Object[] { Integer.valueOf(cl.getInMinute()) })).append(" ");
      sb.append("进站名称：").append(cl.getInStation()).append(" ");
      sb.append("出站时间：").append(String.format("%02d", new Object[] { Integer.valueOf(cl.getOutHour()) })).append(":").append(String.format("%02d", new Object[] { Integer.valueOf(cl.getOutMinute()) })).append(" ");
      sb.append("出站名称：").append(cl.getOutStation()).append(" ");
      sb.append("费用：").append(cl.getChargeCount());
      sb.append("\r\n");
    }
    return sb.toString();
  }
  
  
  
  
  
  
  
}