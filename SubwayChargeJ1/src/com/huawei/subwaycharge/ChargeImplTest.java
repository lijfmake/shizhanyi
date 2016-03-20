
package com.huawei.subwaycharge;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


//import com.huawei.exam.ReturnCodeEnum;
import com.huawei.subwaycharge.CardTypeEnum;
import com.huawei.subwaycharge.ChargeCmdInfo;
import com.huawei.subwaycharge.ChargeImpl;
import com.huawei.subwaycharge.ChargeLogInfo;
import com.huawei.subwaycharge.DistanceInfo;
import com.huawei.subwaycharge.OpResult;
import com.huawei.subwaycharge.ReturnCodeEnum;

import junit.framework.Assert;

public class ChargeImplTest {
	ChargeImpl chargeImpl = new ChargeImpl();;
	DistanceInfo dis1=new DistanceInfo("S3","S4",3);
	DistanceInfo dis2=new DistanceInfo("S6","S5",6);
	DistanceInfo dis3=new DistanceInfo("S1","S2",4);
	DistanceInfo dis4=new DistanceInfo("S4","S5",2);
	DistanceInfo dis5=new DistanceInfo("S3","S2",1);
	
	DistanceInfo[] distances={dis1,dis2,dis3,dis4,dis5};
	@Before
	public void setUp() throws Exception {
		
		

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOpReset() {
		//fail("Not yet implemented");
		//
		Assert.assertEquals(chargeImpl.opReset().toString(), OpResult.createOpResult(ReturnCodeEnum.I00).toString());
	
	}



	@Test
	public void testOpQuery2() {
		//q 9 20:10 19.10
		Assert.assertEquals(chargeImpl.opQuery(9, 20, 10, 19, 10).toString(),OpResult.createOpResult(ReturnCodeEnum.E02).toString());

	}

	@Test
	public void testOpQuery3() {
		
		//q 0 00:00 23:59
		Assert.assertEquals(chargeImpl.opQuery(0, 0, 0, 23, 59).toString(),OpResult.createOpResult(ReturnCodeEnum.E21).toString());
		////25  q 0 20:00 23:59
		
	}
	@Test
	public void testOpQuery4() {
		
    	//26  q 0 00:00 23:59
    	//<1><09:30><S2><9:59><s3><2><成功>
    	//<1><10:30><S3><11:59><S4><2><成功>
    	//<2><8:30><S3><9:30><S4><2><成功>
    	ChargeLogInfo logs1=new ChargeLogInfo(1,9,30,"S2",9,59,"S3",1,true);
    	ChargeLogInfo logs2=new ChargeLogInfo(1,10,30,"S3",11,59,"S4",2,true);
    	ChargeLogInfo logs3=new ChargeLogInfo(2,8,30,"S2",9,30,"S4",3,true);
    	ChargeLogInfo[] logs26={logs1,logs2,logs3};
    	ChargeCmdInfo ci1 = new ChargeCmdInfo(1, CardTypeEnum.B, 100, 9,
				30, "S2", 9, 59, "S3");
    	ChargeCmdInfo ci2 = new ChargeCmdInfo(1, CardTypeEnum.A, 2, 10,
				30, "S3", 11, 59, "S4");
    	ChargeCmdInfo ci3 = new ChargeCmdInfo(2, CardTypeEnum.C, 100, 8,
				30, "S2", 9, 30, "S4");
    	chargeImpl.opCharge(ci3, distances);
		chargeImpl.opCharge(ci2, distances);
		chargeImpl.opCharge(ci1, distances);
		
		Assert.assertEquals(chargeImpl.opQuery(0,0,0,23,59).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I20,logs26).toString());
	
	}

	@Test
	public void testOpQuery5() {
    	//27  q 1 8:00 9:00
		
		Assert.assertEquals(chargeImpl.opQuery(1,8,0,9,0).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.E21).toString());
 

	}
	@Test
	public void testOpQuery6() {
		 
    	//28  q 1 00:00 23:59
    	//<1><09:30><S2><9:59><s3><2><成功>
    	//<1><10:30><S3><11:59><S4><2><成功>
    	ChargeLogInfo logs1=new ChargeLogInfo(1,9,30,"S2",9,59,"S3",1,true);
    	ChargeLogInfo logs2=new ChargeLogInfo(1,10,30,"S3",11,59,"S4",2,true);
    	ChargeLogInfo logs3=new ChargeLogInfo(2,8,30,"S2",9,30,"S4",3,true);
    	
    	ChargeCmdInfo ci1 = new ChargeCmdInfo(1, CardTypeEnum.B, 100, 9,
				30, "S2", 9, 59, "S3");
    	ChargeCmdInfo ci2 = new ChargeCmdInfo(1, CardTypeEnum.A, 2, 10,
				30, "S3", 11, 59, "S4");
    	ChargeCmdInfo ci3 = new ChargeCmdInfo(2, CardTypeEnum.C, 100, 8,
				30, "S2", 9, 30, "S4");
    	chargeImpl.opCharge(ci3, distances);
		chargeImpl.opCharge(ci2, distances);
		chargeImpl.opCharge(ci1, distances);

    	ChargeLogInfo[] logs28={logs1,logs2};  
    		
		Assert.assertEquals(OpResult.createOpResult(ReturnCodeEnum.I20,logs28).toString(),
				chargeImpl.opQuery(1,0,0,23,59).toString()
				);
	
	}
	@Test
	public void testOpQuery7() {
		//25  q 0 20:00 23:59
				Assert.assertEquals(chargeImpl.opQuery(0,20,0,23,59).toString(), 
						OpResult.createOpResult(ReturnCodeEnum.E21).toString());
	
	}

	@Test
	public void testOpCharge1() {
		//fail("Not yet implemented");
		// c 卡号 卡类型 卡扣费前余额 进站时间 进站站点名称 出站时间 出站站点名称
		//c 1 B 100 10:30 S2 10:59 S3
		ChargeCmdInfo ci12 = new ChargeCmdInfo(1, CardTypeEnum.B, 100, 10,
				30, "S2", 10, 59, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci12, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,99).toString());
	}
	@Test
	public void testOpCharge2() {
		//fail("Not yet implemented");
		
		//C 9 C 20 12:05 S1 11:20 S4
		ChargeCmdInfo ci13 = new ChargeCmdInfo(1, CardTypeEnum.C, 20, 12,
				05, "S1", 11, 20, "S4");
		Assert.assertEquals(chargeImpl.opCharge(ci13, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.E02).toString());
				
	}
	@Test
	public void testOpCharge3() {
		//fail("Not yet implemented");
		
		//c 1 A 1 09:30 S2 09:59 S3
		ChargeCmdInfo ci3 = new ChargeCmdInfo(1, CardTypeEnum.A, 1, 9,
				30, "S2", 9, 59, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci3, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I13,1,1).toString());
		
				
	}
	
	@Test
	public void testOpCharge4() {
		//fail("Not yet implemented");
		
		//c 1 A 3 09:30 S1 09:59 S2
		ChargeCmdInfo ci4 = new ChargeCmdInfo(1, CardTypeEnum.A, 3, 9,
				30, "S1", 9, 59, "S2");
		Assert.assertEquals(chargeImpl.opCharge(ci4, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,0).toString());
	}
	@Test
	public void testOpCharge5() {
		//fail("Not yet implemented");
		
		//c 1 A 4 09:30 S5 09:59 S6
		ChargeCmdInfo ci5 = new ChargeCmdInfo(1, CardTypeEnum.A, 4, 9,
				30, "S5", 9, 59, "S6");
		Assert.assertEquals(chargeImpl.opCharge(ci5, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,0).toString());
	}
	@Test
	public void testOpCharge6(){
		//c 1 A 1 09:30 S2 09:59 S3
		
		ChargeCmdInfo ci1 = new ChargeCmdInfo(1, CardTypeEnum.A, 1, 9,
				30, "S2", 9, 59, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci1, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I13,1,1).toString());		
	}
	@Test
	public void testOpCharge7(){
		//c 1 A 100 09:30 S3 09:39 S3
		ChargeCmdInfo ci16 = new ChargeCmdInfo(1, CardTypeEnum.A, 100, 9,
				30, "S3", 9, 59, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci16, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,0).toString());		
	}
	@Test
	public void testOpCharge8(){
		//c 1 B/C 1 09:30 S3 10:39 S3
		ChargeCmdInfo ci211 = new ChargeCmdInfo(1, CardTypeEnum.B,100, 9,
				30, "S3", 10, 39, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci211, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,97).toString());
	}
	@Test
	public void testOpCharge9(){
		//c 1 B/C 1 09:30 S3 10:39 S3
		ChargeCmdInfo ci212 = new ChargeCmdInfo(1, CardTypeEnum.C,100, 9,
				30, "S3", 10, 39, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci212, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,97).toString());
	}
	@Test
	public void testOpCharge10(){
		//c 1 A 100 09:30 S3 09:39 S3
		ChargeCmdInfo ci18 = new ChargeCmdInfo(1, CardTypeEnum.A,100, 9,
				30, "S3", 9, 39, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci18, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,0).toString());		
	}
	@Test
	public void testOpCharge11(){
		//c 1 B/C 1 09:30 S3 09:39 S3
		ChargeCmdInfo ci19 = new ChargeCmdInfo(1, CardTypeEnum.C,1, 9,
				30, "S3", 9, 39, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci19, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I12,1,1).toString());	
	}
	@Test
	public void testOpCharge12(){
		//C 3 C 85 09:05 S3 10:201 S6
		ChargeCmdInfo ci15 = new ChargeCmdInfo(3, CardTypeEnum.C, 85, 9,
				05, "S3", 10, 201, "S6");
		Assert.assertEquals(chargeImpl.opCharge(ci15, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.E01).toString());
	}
	@Test
	public void testOpCharge13(){
		//c 1 A 100 09:30 S3 10:39 S3
		ChargeCmdInfo ci20 = new ChargeCmdInfo(1, CardTypeEnum.A,100, 9,
				30, "S3", 10, 39, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci20, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,0).toString());	
	}
	@Test
	public void testOpCharge14(){
		//c 1 A 100 09:30 S3 10:39 S3
		ChargeCmdInfo ci21 = new ChargeCmdInfo(1, CardTypeEnum.A,100, 9,
				30, "S3", 10, 39, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci21, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,0).toString());
	}
	@Test
	public void testOpCharge15(){
		//c 1 A 100 09:30 S2 09:59 S3
		ChargeCmdInfo ci01 = new ChargeCmdInfo(1, CardTypeEnum.A, 100, 9,
				30, "S2", 9, 59, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci01, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,0).toString());
	}
	@Test
	public void testOpCharge16(){
		//c 1 A 1 09:30 S2 09:59 S3
		ChargeCmdInfo ci02 = new ChargeCmdInfo(1, CardTypeEnum.A, 1, 9,
				30, "S2", 9, 59, "S3");
		
		Assert.assertEquals(chargeImpl.opCharge(ci02, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I13,1,1).toString());
	}
	@Test
	public void testOpCharge17(){
    	//c 1 A 5 09:30 S3 09:59 S6
		ChargeCmdInfo ci6 = new ChargeCmdInfo(1, CardTypeEnum.A, 5, 9,
				30, "S3", 9, 59, "S6");
		Assert.assertEquals(chargeImpl.opCharge(ci6, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,0).toString());
	}
	@Test
	public void testOpCharge18(){
        //c 1 B 100 09:30 S2 09:59 S3
		ChargeCmdInfo ci7 = new ChargeCmdInfo(1, CardTypeEnum.B, 100, 9,
				30, "S2", 9, 59, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci7, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,99).toString());  
	}
	@Test
	public void testOpCharge19(){
        //c 1 C 100 07:30 S2 08:59 S3
		ChargeCmdInfo ci9 = new ChargeCmdInfo(1, CardTypeEnum.C, 100, 7,
				30, "S2", 8, 59, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci9, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,98).toString());  
	}
	@Test
	public void testOpCharge20(){
        //c 1 B 100 07:30 S2 08:59 S3
		ChargeCmdInfo ci10 = new ChargeCmdInfo(1, CardTypeEnum.B, 100, 7,
				30, "S2", 8, 59, "S3");
		Assert.assertEquals(chargeImpl.opCharge(ci10, distances).toString(), 
				OpResult.createOpResult(ReturnCodeEnum.I11,1,98).toString());
	}
}

