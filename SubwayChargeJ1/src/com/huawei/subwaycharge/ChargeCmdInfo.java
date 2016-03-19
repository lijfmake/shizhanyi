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
public class ChargeCmdInfo {

    /* 请求扣费操作命令参数 结构 */
    private int cardNo; /* 卡号 */
    private CardTypeEnum cardType; /* 卡类型 */
    private int cardMoney; /* 卡内当前金额 */
    private int inHour; /* 进站时间:钟点(范围: 0 ~ 23) */
    private int inMinute; /* 进站时间:分钟(范围: 0 ~ 59) */
    private String inStation; /* 进站站点名称 */

    private int outHour; /* 出站时间:钟点(范围: 0 ~ 23) */
    private int outMinute; /* 出站时间:分钟(范围: 0 ~ 59) */
    private String outStation; /* 出站站点名称 */

    public ChargeCmdInfo() {

    }

    public ChargeCmdInfo(int cardNo, CardTypeEnum cardType, int cardMoney,
                         int inHour, int inMinute, String inStation, int outHour,
                         int outMinute, String outStation) {

        this.cardNo = cardNo;
        this.cardType = cardType;
        this.cardMoney = cardMoney;
        this.inHour = inHour;
        this.inMinute = inMinute;
        this.inStation = inStation;
        this.outHour = outHour;
        this.outMinute = outMinute;
        this.outStation = outStation;
    }

    public int getCardNo() {
        return cardNo;
    }

    public void setCardNo(int cardNo) {
        this.cardNo = cardNo;
    }

    public CardTypeEnum getCardType() {
        return cardType;
    }

    public void setCardType(CardTypeEnum cardType) {
        this.cardType = cardType;
    }

    public int getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(int cardMoney) {
        this.cardMoney = cardMoney;
    }

    public int getInHour() {
        return inHour;
    }

    public void setInHour(int inHour) {
        this.inHour = inHour;
    }

    public int getInMinute() {
        return inMinute;
    }

    public void setInMinute(int inMinute) {
        this.inMinute = inMinute;
    }

    public String getInStation() {
        return inStation;
    }

    public void setInStation(String inStation) {
        this.inStation = inStation;
    }

    public int getOutHour() {
        return outHour;
    }

    public void setOutHour(int outHour) {
        this.outHour = outHour;
    }

    public int getOutMinute() {
        return outMinute;
    }

    public void setOutMinute(int outMinute) {
        this.outMinute = outMinute;
    }

    public String getOutStation() {
        return outStation;
    }

    public void setOutStation(String outStation) {
        this.outStation = outStation;
    }
}
