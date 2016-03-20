package com.huawei.subwaycharge;

/**
 * <p>Title: 考生可以根据自己的需求在本类中增加方法，但不能修改已有方法与变量</p>
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
public class ChargeLogInfo {

    /*查询扣费日志操作命令参数 结构*/
    //<卡号><进站时间HH:MM><进站站点名称><出站时间HH:MM><出站站点名称><实际扣款金额><扣费是否成功>
    private int cardNo; /* 卡号(范围: 0 ~ 9) */
    private int InHour; /* 进站时间:钟点(范围: 0 ~ 23) */
    private int InMinute; /* 进站时间:分钟(范围: 0 ~ 59) */
    private String InStation; /* 进站站点名称 */
    private int OutHour; /* 出站时间:钟点(范围: 0 ~ 23) */
    private int OutMinute; /* 出站时间:分钟(范围: 0 ~ 59) */
    private String OutStation; /* 出站站点名称 */
    private int ChargeCount; /* 扣款额 */
    private boolean isCharged; /* 是否成功 */

    public ChargeLogInfo(int cardNo, int inHour, int inMinute,
                         String inStation, int outHour, int outMinute, String outStation,
                         int chargeCount, boolean isCharged) {

        this.cardNo = cardNo;
        this.InHour = inHour;
        this.InMinute = inMinute;
        this.InStation = inStation;
        this.OutHour = outHour;
        this.OutMinute = outMinute;
        this.OutStation = outStation;
        this.ChargeCount = chargeCount;
        this.isCharged = isCharged;
    }
    
    public ChargeLogInfo(ChargeCmdInfo ci, int ChargeCount, boolean isCharged) {
    	this.cardNo = ci.getCardNo();
    	this.InHour = ci.getInHour();
    	this.InMinute = ci.getInMinute();
    	this.InStation = ci.getInStation();
    	this.OutHour = ci.getOutHour();
    	this.OutMinute = ci.getOutMinute();
    	this.OutStation = ci.getOutStation();
    	this.ChargeCount = ChargeCount;
    	this.isCharged = isCharged;
    }

    public int getCardNo() {
        return cardNo;
    }

    public void setCardNo(int cardNo) {
        this.cardNo = cardNo;
    }

    public int getInHour() {
        return InHour;
    }

    public void setInHour(int inHour) {
        InHour = inHour;
    }

    public int getInMinute() {
        return InMinute;
    }

    public void setInMinute(int inMinute) {
        InMinute = inMinute;
    }

    public String getInStation() {
        return InStation;
    }

    public void setInStation(String inStation) {
        InStation = inStation;
    }

    public int getOutHour() {
        return OutHour;
    }

    public void setOutHour(int outHour) {
        OutHour = outHour;
    }

    public int getOutMinute() {
        return OutMinute;
    }

    public void setOutMinute(int outMinute) {
        OutMinute = outMinute;
    }

    public String getOutStation() {
        return OutStation;
    }

    public void setOutStation(String outStation) {
        OutStation = outStation;
    }

    public int getChargeCount() {
        return ChargeCount;
    }

    public void setChargeCount(int chargeCount) {
        ChargeCount = chargeCount;
    }

    public boolean isCharged() {
        return isCharged;
    }

    public void setCharged(boolean isCharged) {
        this.isCharged = isCharged;
    }
}
