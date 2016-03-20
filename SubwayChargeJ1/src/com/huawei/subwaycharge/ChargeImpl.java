package com.huawei.subwaycharge;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//import com.huawei.exam.ReturnCodeEnum;



/**
 * <p>Title: 待考生实现类</p>
 * 各方法按要求返回，程序库会组装报文输出
 * 系统提供了3个输出接口，在OpResult类中定义，请考生先阅读定义。
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
public class ChargeImpl {


    // 必须提供无参数构造函数，考生可在函数体中根据需要增加初始化代码
    // 程序库中会且只会生成一个ChargeImpl实例，并在整个进程生命周期中一直使用这个实例
	List<ChargeLogInfo> list ;
	int if_Delete[]=new int[10];

//		if_Delete[i]=0;
	//List<DistanceInfo> distanceInfoList ;
	
    public ChargeImpl() {
    	list = new ArrayList<ChargeLogInfo>();
    	
    }

    /**
     * 考生需要实现的接口
     * r命令接口，实现系统的初始化
     *
     * @return OpResult：处理结果
     */
    public OpResult opReset() {
    	list.clear();
    	System.out.println("list clear");
    	for(int i=0;i<10;i++){
    		if_Delete[i]=0;
    		
    	}
        return OpResult.createOpResult(ReturnCodeEnum.I00);
    }
    
    
    /**
     * 考生需要实现的接口
     * h命令接口，实现扣费历史记录查询功能，（只查询消费成功的记录）。
     *
     * @return OpResult：处理结果
     */
    public OpResult opChargeBack(int cardNo) { 
//    	System.out.println("h命令接口");
    	
    	
    	if(if_Delete[0]==1 || if_Delete[cardNo]==1)
    		return OpResult.createOpResult(ReturnCodeEnum.E22);
    	
		sortList(list);
		int number=0;
		for(ChargeLogInfo loginfo:list){
			if(loginfo.isCharged()==true){	//满足查询条件
				number++;
			}
		}
    	ChargeLogInfo[] logs = new  ChargeLogInfo[number];
		if(cardNo == 0){
			boolean flag = H_handleCardNoEquals0(list,logs); 	
			if(flag)
				return OpResult.createOpResult(ReturnCodeEnum.E21);
			return OpResult.createOpResult(ReturnCodeEnum.I30,logs);
		}else{
   			boolean flag = H_handleCardNoEqualsElse(cardNo,list,logs);
			if(flag)
				return OpResult.createOpResult(ReturnCodeEnum.E21);
			return OpResult.createOpResult(ReturnCodeEnum.I30,logs);
		}
    }
    
    
    
    
    
    /**
     * 考生需要实现的接口
     * d命令接口，实现注销功能
     *
     * @return OpResult：处理结果
     */
    public OpResult opDelete(int cardNo) { 
    	if_Delete[cardNo]=1;
		if(cardNo == 0){
			return OpResult.createOpResult(ReturnCodeEnum.I22,cardNo,0);
		}else{
			return OpResult.createOpResult(ReturnCodeEnum.I22,cardNo,0);
		}
    }


    /**
     * 考生需要实现的接口
     * q命令接口，实现扣费日志的查询功能
     *
     * @param cardNo int   ：卡号
     * @param startHour int：查询起始时间的小时部分
     * @param startMin int ：查询起始时间的分钟部分
     * @param endHour int  ：查询结束时间的小时部分
     * @param endMin int   ：查询结束时间的分钟部分
     *
     * @return OpResult：处理结果
     */
    public OpResult opQuery(int cardNo, int startHour, int startMin,
            int endHour, int endMin) {

    		//查询成功请考生调用OpResult类的接口OpResult createOpResult(ReturnCodeEnum returnCode, ChargeLogInfo[] logs);
    	if(timeAndCardNoJudge(cardNo,startHour,startMin,endHour,endMin)){
    			return OpResult.createOpResult(ReturnCodeEnum.E01);
    		}else if(timeJudge(startHour,startMin,endHour,endMin)){
    			return OpResult.createOpResult(ReturnCodeEnum.E02);
    		}else{
				/*
				* list进行排序，按照cardNo和endTime
				*/
				sortList(list);
				//ChargeLogInfo[] logs = new  ChargeLogInfo[list.size()];
					
				int newStartTime = normalizationTime(startHour,startMin);
				int newEndTime = normalizationTime(endHour,endMin);
				
				if(cardNo == 0){
				int i = 0;
				for(ChargeLogInfo loginfo:list){
					int loginfoOutTime = normalizationTime(loginfo.getOutHour(),loginfo.getOutMinute());
					if(loginfoOutTime>= newStartTime && loginfoOutTime<= newEndTime){	
						++i;
					}
				}		
				ChargeLogInfo[] logs = new ChargeLogInfo[i];
				
				boolean flag = handleCardNoEquals0(list,logs,newStartTime,newEndTime); 	
				if(flag)
					return OpResult.createOpResult(ReturnCodeEnum.E21);
				return OpResult.createOpResult(ReturnCodeEnum.I20,logs);
				}else{
				int i = 0;
				for(ChargeLogInfo loginfo: list){
					//loginfo 对象的出站时间
					int loginfoCardNo = loginfo.getCardNo();
					int loginfoOutTime = normalizationTime(loginfo.getOutHour(),loginfo.getOutMinute());
					if(loginfoCardNo == cardNo && loginfoOutTime>= newStartTime && loginfoOutTime<= newEndTime){	
						++i;
					}
				}
				ChargeLogInfo[] logs = new ChargeLogInfo[i];
					boolean flag = handleCardNoEqualsElse(cardNo,list,logs,newStartTime,newEndTime);
				if(flag)
					
					return OpResult.createOpResult(ReturnCodeEnum.E21);
				return OpResult.createOpResult(ReturnCodeEnum.I20,logs);
				}
			}
		}


    /**
     * 考生需要实现的接口
     * c命令接口，实现请求扣费的功能
     *
     * @param  chargeCmd ChargeCmdInfo:查询命令的消息体
     * @param  distances DistanceInfo []:相邻站点间里程表；
     *                                 (系统已内置，考生根据此数组信息，自行设计算法获取不同站之间的距离）
     *                                 相邻站点间里程表信息举例：
     *                                 ("S6", "S5", 6)
     *                                 ("S3", "S4", 3)
     *                                 ("S1", "S2", 4)
     *                                 ("S4", "S5", 3)
     *                                 ("S3", "S2", 1)
     *
     * @return OpResult：处理结果
     */
    public OpResult opCharge(ChargeCmdInfo ci, DistanceInfo[] distances) {

        //扣费成功,请考生调用OpResult类的接口OpResult    createOpResult(ReturnCodeEnum returnCode, int cardNo, int moneyLeft);
    	
    	if(if_Delete[0]==1 || if_Delete[ci.getCardNo()]==1)
    		return OpResult.createOpResult(ReturnCodeEnum.E22);
    	
    	//时间格式错误
    	if(ci.getInHour()<0||ci.getInHour()>23||ci.getOutHour()<0||ci.getOutHour()>23||
    		ci.getInMinute()<0||ci.getInMinute()>59||ci.getOutMinute()<0||ci.getOutMinute()>59)
    	{
			// TODO: handle exception
			this.getLogInfo(ci, 0, false);
			return OpResult.createOpResult(ReturnCodeEnum.E01);
		
    	}
    	
    	if (( ci.getInHour()>ci.getOutHour() ) || (ci.getInHour()==ci.getOutHour() && ci.getInMinute()>ci.getOutMinute())){
    		
    	//时间关系错误
    		this.getLogInfo(ci, 0, false);
    		return OpResult.createOpResult(ReturnCodeEnum.E02);
    		
    	}
    	
		String[] stationArray ={"S1","S2","S3","S4","S5","S6"};
    	byte bResult = 0;
		for (String temp : stationArray) {
			if (temp.equals(ci.getInStation()) || temp.equals(ci.getOutStation())) {
				bResult += 1;
				if(temp.equals(ci.getInStation()) && temp.equals(ci.getOutStation())){
					bResult += 1;
					break;
				}
			}
		}
		if(bResult!=2){ //站点异常
			this.getLogInfo(ci, 0, false);
			return OpResult.createOpResult(ReturnCodeEnum.I10,ci.getCardNo(),ci.getCardMoney());
		}
    	
		//计算两地之间的距离
		
		int dis[]={4,1,3,2,6},distance=0;
		int in1=Integer.parseInt(ci.getInStation().substring(1));
		int in2=Integer.parseInt(ci.getOutStation().substring(1));
		if(in1>in2){
			int int_temp=in1;
			in1=in2;
			in2=int_temp;
		}
		for(int i=in1-1;i<in2-1;i++){
			distance=distance+dis[i];
		}
    	
		
		//CardTypeEnum A=CardTypeEnum.A;
		//CardTypeEnum B=CardTypeEnum.B;
//		CardTypeEnum C=CardTypeEnum.C;
		
		//进出站为同一地点
		if(in1 == in2){
			return sameStation(ci);
			
		}
		//进出站为不同的地点
		else{
			return diffStation(ci, distance);
			
			
		}
    }

	public OpResult diffStation(ChargeCmdInfo ci, int distance) {
		//计算基本票价
		int base_price;
		if(distance<=3) base_price=2;
		else if(distance<=5) base_price=3;
		else if(distance<=10) base_price=4;
		else	base_price=5;
		
		if(ci.getCardType()==CardTypeEnum.A){ //5.不同地点，单程票，扣费成功/失败
			
			if(ci.getCardMoney()>=base_price)
			{
				this.getLogInfo(ci, ci.getCardMoney(), true);
				return OpResult.createOpResult(ReturnCodeEnum.I11,ci.getCardNo(),0);
			}else
			{
				this.getLogInfo(ci, 0, false);
				return OpResult.createOpResult(ReturnCodeEnum.I13,ci.getCardNo(),ci.getCardMoney());
			}
		}
		else{ 
			if((ci.getInHour()>=7 && ci.getInHour()<9) || (ci.getInHour()*60+ci.getInMinute()>=16*60+30 && ci.getInHour()*60+ci.getInMinute()<18*60+30)){
				//6.不同地点，非单程票，进站时间为[7:00,9:00）、[16:30,18:30)时，无任何优惠
				int remain=ci.getCardMoney()-base_price;
				return return_result(remain,base_price,ci);
			}
			else if((ci.getInHour()>=10 && ci.getInHour()<11) || (ci.getInHour()>=15 && ci.getInHour()<16)){
				//7.不同地点，非单程票，进站时间为[10:00,11:00）、[15:00,16:00）时，5折优惠
				int discount_price=(int) Math.floor(0.5*base_price);
				int remain=ci.getCardMoney()-discount_price;
				return return_result(remain,discount_price,ci);
				
			}
			else{
				if(ci.getCardType()==CardTypeEnum.B){
					//8.不同地点，老年卡，正常时间，9折优惠
					int discount_price=(int) Math.floor(0.9*base_price);
					int remain=ci.getCardMoney()-discount_price;
					return return_result(remain,discount_price,ci);
				}
				else{
					//9.不同地点，普通卡，正常时间，无优惠
					int remain=ci.getCardMoney()-base_price;
					return return_result(remain,base_price,ci);
				}
				
			}
			
		}
	}

	public OpResult sameStation(ChargeCmdInfo ci) {
		int time_interval=(ci.getOutHour()-ci.getInHour())*60+ci.getOutMinute()-ci.getInMinute();
		
		if(time_interval<=30){
			
			if(ci.getCardType()==CardTypeEnum.A){ 
				//1.同一地点，时间间隔<=30min，单程票，扣费成功
				this.getLogInfo(ci, ci.getCardMoney(), true);
				return OpResult.createOpResult(ReturnCodeEnum.I11,ci.getCardNo(),0);
			}
			//2.同一地点，时间间隔<=30min，非单程票，扣费为0
			this.getLogInfo(ci, 0, true);
			if(ci.getCardMoney()>=20){
				return OpResult.createOpResult(ReturnCodeEnum.I11,ci.getCardNo(),ci.getCardMoney());
			}
			return OpResult.createOpResult(ReturnCodeEnum.I12,ci.getCardNo(),ci.getCardMoney());
			
		}
		else{
			
			if(ci.getCardType()==CardTypeEnum.A){ //3.同一地点，时间间隔>30min，单程票，扣费成功/失败
				
				if(ci.getCardMoney()>=3){
					this.getLogInfo(ci, ci.getCardMoney(), true);
					return OpResult.createOpResult(ReturnCodeEnum.I11,ci.getCardNo(),0);
					
				}
				else{
					this.getLogInfo(ci, 0, false);
					return OpResult.createOpResult(ReturnCodeEnum.I13,ci.getCardNo(),ci.getCardMoney());
				}
			}
			else{//4.同一地点，时间间隔>30min，非单程票，扣费成功/提示余额不足/失败
				int remain=ci.getCardMoney()-3;
				//this.getLogInfo(ci, 0, false);
				return return_result(remain,3,ci);
				
			}

			
		}
	}
    
  //根据余额不同，返回不同的结果
    public OpResult return_result(int remain,int chargeMoney,ChargeCmdInfo ci) {
    	
		if(remain>=20){
			this.getLogInfo(ci, chargeMoney, true);
			return OpResult.createOpResult(ReturnCodeEnum.I11,ci.getCardNo(),remain);
		}
		else if(remain>=0){
			this.getLogInfo(ci, chargeMoney, true);
			return OpResult.createOpResult(ReturnCodeEnum.I12,ci.getCardNo(),remain);
		}else{
			this.getLogInfo(ci, 0, false);
		}return OpResult.createOpResult(ReturnCodeEnum.I13,ci.getCardNo(),ci.getCardMoney());
    }
    
    /**
     * 
     * @param ci cmd类
     * @param chargeMoney 收费钱
     * @param isCharged 是否收费成功
     */
    public void getLogInfo(ChargeCmdInfo ci,int chargeMoney,boolean isCharged){
    	
    	ChargeLogInfo cLogInfo = 
    			new ChargeLogInfo(ci.getCardNo(), ci.getInHour(),
    					ci.getInMinute(), ci.getInStation(), ci.getOutHour(),
    					ci.getOutMinute(), ci.getOutStation(), chargeMoney, isCharged);
    	if(list.size()<10)
    		list.add(cLogInfo);
    	
    	
    }
    /**
     * 判断时间参数是否有异常
     * 
     * @param cardNo int   ：查询卡号
     * @param startHour int：查询起始时间的小时部分
     * @param startMin int ：查询起始时间的分钟部分
     * @param endHour int  ：查询结束时间的小时部分
     * @param endMin int   ：查询结束时间的分钟部分
     *
     * @return true or false;
     */
    public boolean timeAndCardNoJudge(int cardNo,int startHour, int startMin,
            int endHour, int endMin){
    	
    	if(cardNo <0 ||cardNo>9 || startHour<0 || startHour>23 || startMin<0 || startMin>59|| endHour<0 || endHour>23 || endMin<0 || endMin>59)
    		return true;
    	else 
    		return false;
    	
    }
    
    /**
     * 判断时间参数是否有错误
     *
     * @param startHour int：查询起始时间的小时部分
     * @param startMin int ：查询起始时间的分钟部分
     * @param endHour int  ：查询结束时间的小时部分
     * @param endMin int   ：查询结束时间的分钟部分
     *
     * @return true or false;
     */ 
    public boolean timeJudge(int startHour,int startMin,int endHour,int endMin){
    	int newStartTime = normalizationTime(startHour,startMin);
		int newEndTime = normalizationTime(endHour,endMin);
		if(newStartTime>newEndTime)
			return true;
    	return false;
    }
    
    /**
     * 格式化时间参数
     *
     * @param startHour int：查询起始时间的小时部分
     * @param startMin int ：查询起始时间的分钟部分
     * @param endHour int  ：查询结束时间的小时部分
     * @param endMin int   ：查询结束时间的分钟部分
     *
     * @return true or false;
     */ 
    public int normalizationTime(int hour,int minute){
    	return hour*60+minute;
    }
    
    /**
     * 对list进行排序
     *
     * @param mylist List<ChargeLogInfo>：程序的ArrayList
     *
     */
    public void sortList(List<ChargeLogInfo> mylist){
		Collections.sort(mylist, new Comparator<ChargeLogInfo>(){
			
			public int compare(ChargeLogInfo logInfo1,ChargeLogInfo logInfo2){
				if(logInfo1.getCardNo() == logInfo2.getCardNo()){
    				Integer loginfo1OutTime = normalizationTime(logInfo1.getOutHour(),logInfo1.getOutMinute());
    				Integer loginfo2OutTime = normalizationTime(logInfo2.getOutHour(),logInfo2.getOutMinute());
    				return loginfo1OutTime.compareTo(loginfo2OutTime);
				}else{
					return Integer.valueOf(logInfo1.getCardNo()).compareTo(Integer.valueOf(logInfo2.getCardNo()));
				}
					
			}
		});
    }
    
    /**
     * 对cardNo == 0 的这一情况进行处理
     *
     * @param mylist List<ChargeLogInfo>：程序的ArrayList
     * @param mylogs ChargeLogInfo[]    ：程序声明的ChargeLogInfo数组，返回时使用
     * @param startTime int             ：格式化后的查询起始时间
     * @param endTime int               ：格式化后的查询终止时间
     * 
     *  @return true or false;
     */
    	public boolean handleCardNoEquals0(List<ChargeLogInfo> mylist,ChargeLogInfo[] mylogs,int startTime,int endTime){
			boolean flag = true;
			int i = 0;
			for(ChargeLogInfo loginfo:mylist){
				//loginfo 对象的出站时间
				int loginfoOutTime = normalizationTime(loginfo.getOutHour(),loginfo.getOutMinute());
				if(loginfoOutTime>= startTime && loginfoOutTime<= endTime){	
					mylogs[i] = loginfo;
					++i;
					flag = false;	
				}
			}
    		return flag;
    	}
    	
       /**
        * 对cardNo != 0 的这一情况进行处理
        * @param cardNo int                ：卡号
        * @param mylist List<ChargeLogInfo>：程序的ArrayList
        * @param mylogs ChargeLogInfo[]    ：程序声明的ChargeLogInfo数组，返回时使用
        * @param startTime int             ：格式化后的查询起始时间
        * @param endTime int               ：格式化后的查询终止时间
        * 
        *  @return true or false;
        *  
        */
        public boolean handleCardNoEqualsElse(int cardNo,List<ChargeLogInfo> mylist,ChargeLogInfo[] mylogs,int startTime,int endTime){
    		boolean flag = true;
    		int i = 0;
    		for(ChargeLogInfo loginfo:mylist){
    			//loginfo 对象的出站时间
				int loginfoCardNo = loginfo.getCardNo();
    			int loginfoOutTime = normalizationTime(loginfo.getOutHour(),loginfo.getOutMinute());
    			if(loginfoCardNo == cardNo && loginfoOutTime>= startTime && loginfoOutTime<= endTime){	
    				mylogs[i] = loginfo;
    				++i;
    				flag = false;	
    			}
    		}
       		return flag;
       	}    	










/////////////////针对h命令，只对扣费成功的进行处理///////

/**
 * 对cardNo == 0 的这一情况进行处理
 *
 * @param mylist List<ChargeLogInfo>：程序的ArrayList
 * @param mylogs ChargeLogInfo[]    ：程序声明的ChargeLogInfo数组，返回时使用
 * @param startTime int             ：格式化后的查询起始时间
 * @param endTime int               ：格式化后的查询终止时间
 * 
 *  @return true or false;
 */
	public boolean H_handleCardNoEquals0(List<ChargeLogInfo> mylist,ChargeLogInfo[] mylogs){
		boolean flag = true;
		int i = 0;
		for(ChargeLogInfo loginfo:mylist){
			if(loginfo.isCharged()==true){	
				mylogs[i] = loginfo;
				++i;
				flag = false;	
			}
		}
		return flag;
	}
	
   /**
    * 对cardNo != 0 的这一情况进行处理
    * @param cardNo int                ：卡号
    * @param mylist List<ChargeLogInfo>：程序的ArrayList
    * @param mylogs ChargeLogInfo[]    ：程序声明的ChargeLogInfo数组，返回时使用
    * @param startTime int             ：格式化后的查询起始时间
    * @param endTime int               ：格式化后的查询终止时间
    * 
    *  @return true or false;
    *  
    */
    public boolean H_handleCardNoEqualsElse(int cardNo,List<ChargeLogInfo> mylist,ChargeLogInfo[] mylogs){
		boolean flag = true;
		int i = 0;
		for(ChargeLogInfo loginfo:mylist){
			int loginfoCardNo = loginfo.getCardNo();
			if(loginfoCardNo == cardNo && loginfo.isCharged()==true){	
				mylogs[i] = loginfo;
				++i;
				flag = false;	
			}
		}
   		return flag;
   	}    	
}

