package com.huawei.subwaycharge;

import com.huawei.subwaycharge.CardTypeEnum;
import com.huawei.subwaycharge.ChargeCmdInfo;
import com.huawei.subwaycharge.ChargeImpl;
import com.huawei.subwaycharge.DistanceInfo;
import com.huawei.subwaycharge.OpResult;
import com.huawei.exam.Command;
//import com.huawei.exam.ReturnCodeEnum;
import com.huawei.exam.ExamServer;

public class ExamCommand
  implements Command
{
  private static final String CMD_RESET = "r";
  private static final String CMD_CHARGE = "c";
  private static final String CMD_QUERY = "q";
  private static final String CMD_h = "h";
  private static final String CMD_d = "d";
  private ChargeImpl impl;

  public ExamCommand()
  {
    this.impl = new ChargeImpl();
  }

  public byte[] command(byte[] byteArray) {
    OpResult rst = null;

    if (byteArray == null) {
      rst = OpResult.createOpResult(ReturnCodeEnum.E01);
      return rst.toString().getBytes();
    }

    String cmds = new String(byteArray);
    String[] paras = cmds.split(" ");

    String cmd = paras[0].trim();

    if ("r".equalsIgnoreCase(cmd))
      rst = cmdReset(paras);
    else if ("c".equalsIgnoreCase(cmd))
      rst = cmdCharge(paras);
    else if("h".equalsIgnoreCase(cmd))
      rst = cmdChargeBack(paras);
    else if("d".equalsIgnoreCase(cmd))
        rst = cmdDelete(paras);
    else if ("q".equalsIgnoreCase(cmd))
    {
      rst = cmdQuery(paras);
    }
    else
    {
      rst = OpResult.createOpResult(ReturnCodeEnum.E01);
    }

    return rst.toString().getBytes();
  }

  
  //RÃüÁî
  private OpResult cmdReset(String[] paras)
  {
    if ((paras == null) || (1 != paras.length))
    {
      return OpResult.createOpResult(ReturnCodeEnum.E01);
    }
    return this.impl.opReset();
  }
  
  
//HÃüÁî
  private OpResult cmdChargeBack(String[] paras)
  {
    if ((paras == null) || (2 != paras.length))
    {
      return OpResult.createOpResult(ReturnCodeEnum.E01);
    }
    String cardNo = paras[1].trim();
    int cardNum = Integer.parseInt(cardNo);
    if ( (cardNum > 9) || (cardNum < 0) )
    	return OpResult.createOpResult(ReturnCodeEnum.E01);
    
    return this.impl.opChargeBack(cardNum);
  }
  
//dÃüÁî
  private OpResult cmdDelete(String[] paras)
  {
    if ((paras == null) || (2 != paras.length))
    {
      return OpResult.createOpResult(ReturnCodeEnum.E01);
    }
    String cardNo = paras[1].trim();
    int cardNum = Integer.parseInt(cardNo);
    if ( (cardNum > 9) || (cardNum < 0) )
    	return OpResult.createOpResult(ReturnCodeEnum.E01);
    
    return this.impl.opDelete(cardNum);
  }
  
  
// qÃüÁî
  private OpResult cmdQuery(String[] paras)
  {
    if ((paras == null) || (paras.length != 4))
    {
      return OpResult.createOpResult(ReturnCodeEnum.E01);
    }

    String cardNo = paras[1].trim();
    String startTime = paras[2].trim();
    String endTime = paras[3].trim();

    if ((startTime.length() != 5) || (endTime.length() != 5))
    {
      return OpResult.createOpResult(ReturnCodeEnum.E01);
    }

    String[] startTimes = startTime.split(":");
    String[] endTimes = endTime.split(":");

    if ((2 != startTimes.length) || (2 != endTimes.length))
    {
      return OpResult.createOpResult(ReturnCodeEnum.E01);
    }

    try
    {
      int cardNum = Integer.parseInt(cardNo);
      int startHour = Integer.parseInt(startTimes[0]);
      int startMin = Integer.parseInt(startTimes[1]);
      int endHour = Integer.parseInt(endTimes[0]);
      int endMin = Integer.parseInt(endTimes[1]);

      if ((cardNum > 9) || (cardNum < 0) || 
        (startHour > 23) || (startHour < 0) || 
        (endHour > 23) || (endHour < 0) || 
        (startMin > 59) || (startMin < 0) || 
        (endMin > 59) || (endMin < 0))
      {
        return OpResult.createOpResult(ReturnCodeEnum.E01);
      }

      return this.impl.opQuery(cardNum, startHour, startMin, endHour, endMin);
    }
    catch (java.lang.Exception cardNum)
    {
    }
    return OpResult.createOpResult(ReturnCodeEnum.E01);
  }

  private OpResult cmdCharge(String[] paras)
  {
    if ((paras == null) || (paras.length != 8))
    {
      return OpResult.createOpResult(ReturnCodeEnum.E01);
    }
    CardTypeEnum cardTypeEn = null;

    String cardNo = paras[1].trim();
    String cardType = paras[2].trim();
    String cardLeftMoney = paras[3].trim();
    String startTime = paras[4].trim();
    String startStation = paras[5].trim().toUpperCase();
    String endTime = paras[6].trim();
    String endStation = paras[7].trim().toUpperCase();

    if ((startTime.length() != 5) || (endTime.length() != 5))
    {
      return OpResult.createOpResult(ReturnCodeEnum.E01);
    }

    String[] startTimes = startTime.split(":");
    String[] endTimes = endTime.split(":");

    if ((2 != startTimes.length) || (2 != endTimes.length))
    {
      return OpResult.createOpResult(ReturnCodeEnum.E01);
    }

    if ((startStation.length() > 2) || (startStation.length() < 1) || 
      (endStation.length() > 2) || (endStation.length() < 1))
    {
      return OpResult.createOpResult(ReturnCodeEnum.E01);
    }

    if ((cardType.startsWith("A")) && (cardType.length() < 2))
    {
      cardTypeEn = CardTypeEnum.A;
    }
    else if ((cardType.startsWith("B")) && (cardType.length() < 2))
    {
      cardTypeEn = CardTypeEnum.B;
    }
    else if ((cardType.startsWith("C")) && (cardType.length() < 2))
    {
      cardTypeEn = CardTypeEnum.C;
    }
    else
    {
      return OpResult.createOpResult(ReturnCodeEnum.E01);
    }

    try
    {
      int cardNum = Integer.parseInt(cardNo);
      int startHour = Integer.parseInt(startTimes[0]);
      int startMin = Integer.parseInt(startTimes[1]);
      int endHour = Integer.parseInt(endTimes[0]);
      int endMin = Integer.parseInt(endTimes[1]);
      int cardMoney = Integer.parseInt(cardLeftMoney);

      if ((cardNum > 9) || (cardNum < 1) || 
        (cardMoney > 999) || (cardMoney < 0))
      {
        return OpResult.createOpResult(ReturnCodeEnum.E01);
      }
      if ((startHour > 23) || (startHour < 0) || 
        (endHour > 23) || (endHour < 0) || 
        (startMin > 59) || (startMin < 0) || 
        (endMin > 59) || (endMin < 0))
      {
        return OpResult.createOpResult(ReturnCodeEnum.E01);
      }

      ChargeCmdInfo ci = new ChargeCmdInfo();
      ci.setCardNo(cardNum);
      ci.setCardMoney(cardMoney);
      ci.setInHour(startHour);
      ci.setInMinute(startMin);
      ci.setCardType(cardTypeEn);
      ci.setInStation(startStation.toUpperCase());
      ci.setOutHour(endHour);
      ci.setOutMinute(endMin);
      ci.setOutStation(endStation.toUpperCase());

      DistanceInfo[] distances = { 
        new DistanceInfo("S3", "S4", 3), 
        new DistanceInfo("S6", "S5", 6), 
        new DistanceInfo("S1", "S2", 4), 
        new DistanceInfo("S4", "S5", 2), 
        new DistanceInfo("S3", "S2", 1) };

      return this.impl.opCharge(ci, distances);
    }
    catch (java.lang.Exception cardNum)
    {
    }
    return OpResult.createOpResult(ReturnCodeEnum.E01);
  }
}