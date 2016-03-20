/*
 * Copyright Notice:
 *      Copyright  1998-2009, Huawei Technologies Co., Ltd.  ALL Rights Reserved.
 *
 *      Warning: This computer software sourcecode is protected by copyright law
 *      and international treaties. Unauthorized reproduction or distribution
 *      of this sourcecode, or any portion of it, may result in severe civil and
 *      criminal penalties, and will be prosecuted to the maximum extent
 *      possible under the law.
 */
package com.huawei.subwaycharge.ut;

import org.junit.After;
import org.junit.Assert;
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

public class ChargeImplUT
{
    ChargeImpl chargeImpl = null;
	DistanceInfo[] distances = {new DistanceInfo("S1","S2",4),
			new DistanceInfo("S2", "S3", 1), new DistanceInfo("S3", "S4", 3), 
			new DistanceInfo("S4", "S5", 2), new DistanceInfo("S5", "S6", 10)};
    
    @Before
    public void setUp()
        throws Exception
    {
        chargeImpl = new ChargeImpl();
    }
    
    @After
    public void tearDown()
        throws Exception
    {
        chargeImpl = null;
    }
    
    @Test
    public void testOpReset()
    {
        OpResult actual = chargeImpl.opReset();       
        OpResult expected = OpResult.createOpResult(ReturnCodeEnum.I00);
        Assert.assertNotNull(actual);
        Assert.assertEquals("testOpReset:", expected.toString(), actual.toString());
    }
    
    @Test
    public void testOpQueryCase1()
    {
    	OpResult actual = chargeImpl.opReset(); 
        OpResult actual1 = chargeImpl.opQuery(0, 20, 0, 17, 30);
        OpResult expected = OpResult.createOpResult(ReturnCodeEnum.I00);
        OpResult expected1 = OpResult.createOpResult(ReturnCodeEnum.E02);
        Assert.assertNotNull(actual);
        Assert.assertEquals("testOpReset:", expected.toString(), actual.toString());
        Assert.assertEquals("time error:", expected1.toString(), actual1.toString());
    }
    
    @Test
    public void testOpQueryCase2()
    {
    	
    	ChargeLogInfo[] logs = new ChargeLogInfo[2];
    	ChargeCmdInfo c1 = new ChargeCmdInfo(4, CardTypeEnum.B, 20, 10, 5, "S1", 11, 20, "S4");
    	ChargeCmdInfo c2 = new ChargeCmdInfo(3, CardTypeEnum.C, 85, 9, 5, "S3", 11, 20, "S8");
    	logs[0] = new ChargeLogInfo(c2, 0 , false);
    	logs[1] = new ChargeLogInfo(c1, 2 , true);
    	OpResult actual = chargeImpl.opReset(); 
        OpResult actual1 = chargeImpl.opCharge(c1, distances);
        OpResult actual2 = chargeImpl.opCharge(c2, distances);
        OpResult actual3 = chargeImpl.opQuery(0, 0, 0, 23, 59);
        OpResult expected = OpResult.createOpResult(ReturnCodeEnum.I00);
        OpResult expected1 = OpResult.createOpResult(ReturnCodeEnum.I12, 4, 18);
        OpResult expected2 = OpResult.createOpResult(ReturnCodeEnum.I10, 3, 85);
        OpResult expected3 = OpResult.createOpResult(ReturnCodeEnum.I20, logs);
        Assert.assertNotNull(actual);
        Assert.assertEquals("testOpReset:", expected.toString(), actual.toString());
        Assert.assertEquals("testOpCharge:", expected1.toString(), actual1.toString());
        Assert.assertEquals("testOpCharge:", expected2.toString(), actual2.toString());
        Assert.assertEquals("right output:", expected3.toString(), actual3.toString());
    }
    
    @Test
    public void testOpQueryCase4()
    {
    	
    	ChargeLogInfo[] logs = new ChargeLogInfo[10];
    	ChargeCmdInfo c1 = new ChargeCmdInfo(4, CardTypeEnum.B, 20, 10, 5, "S1", 11, 20, "S4");
    	ChargeCmdInfo c2 = new ChargeCmdInfo(4, CardTypeEnum.C, 30, 10, 15, "S1", 9, 25, "S5");
    	OpResult actual = chargeImpl.opReset();
    	for(int i = 0;i < 10; i++){
    		chargeImpl.opCharge(c1, distances);
        	logs[i] = new ChargeLogInfo(c1, 2 , true);
    	}
    	chargeImpl.opCharge(c2, distances);
    	OpResult actual2 = chargeImpl.opQuery(0, 0, 0, 23, 59);
        OpResult expected = OpResult.createOpResult(ReturnCodeEnum.I00);
        OpResult expected2 = OpResult.createOpResult(ReturnCodeEnum.I20, logs);
        Assert.assertEquals("testOpReset:", expected.toString(), actual.toString());
        Assert.assertEquals("right output:", expected2.toString(), actual2.toString());
    }
    
    @Test
    public void testOpQueryCase3()
    {
    	OpResult actual = chargeImpl.opReset(); 
        OpResult actual1 = chargeImpl.opQuery(0, 0, 0, 23, 59);
        OpResult expected = OpResult.createOpResult(ReturnCodeEnum.I00);
        OpResult expected1 = OpResult.createOpResult(ReturnCodeEnum.E21);
        Assert.assertNotNull(actual);
        Assert.assertEquals("testOpReset:", expected.toString(), actual.toString());
        Assert.assertEquals("no logs:", expected1.toString(), actual1.toString());
    }
    
    @Test
    public void testOpCharge()
    {

    	//用例1
    	ChargeCmdInfo c1 = new ChargeCmdInfo(9, CardTypeEnum.C, 20, 12, 5, "S1", 11, 20, "S4");
		OpResult actual1 = chargeImpl.opCharge(c1 , distances);
		OpResult expected1 = OpResult.createOpResult(ReturnCodeEnum.E02);
		Assert.assertNotNull(actual1);
		Assert.assertEquals("请求扣费命令参数非法", expected1.toString(), actual1.toString());
		//用例2
		ChargeCmdInfo c2 = new ChargeCmdInfo(3, CardTypeEnum.C, 85, 9, 5, "S3", 10, 20, "S8");
		OpResult actual2 = chargeImpl.opCharge(c2 , distances);
		OpResult expected2 = OpResult.createOpResult(ReturnCodeEnum.I10, 3, 85);
		Assert.assertNotNull(actual1);
		Assert.assertEquals("无效线路，扣费失败", expected2.toString(), actual2.toString());
		//用例3
		ChargeCmdInfo c3 = new ChargeCmdInfo(1, CardTypeEnum.A, 3, 7, 0, "S4", 8, 25, "S4");
		OpResult actual3 = chargeImpl.opCharge(c3 , distances);
		OpResult expected3 = OpResult.createOpResult(ReturnCodeEnum.I11, 1, 0);
		Assert.assertNotNull(actual3);
		Assert.assertEquals("正常扣费成功", expected3.toString(), actual3.toString());
		//用例4
		ChargeCmdInfo c4 = new ChargeCmdInfo(4, CardTypeEnum.B, 20, 10, 5, "S1", 11, 20, "S4");
		OpResult actual4 = chargeImpl.opCharge(c4 , distances);
		OpResult expected4 = OpResult.createOpResult(ReturnCodeEnum.I12, 4, 18);
		Assert.assertNotNull(actual4);
		Assert.assertEquals("正常扣费成功，卡内余额偏低", expected4.toString(), actual4.toString());
		//用例5
		ChargeCmdInfo c5 = new ChargeCmdInfo(4, CardTypeEnum.B, 1, 15, 0, "S1", 15, 55, "S4");
		OpResult actual5 = chargeImpl.opCharge(c5 , distances);
		OpResult expected5 = OpResult.createOpResult(ReturnCodeEnum.I13, 4, 1);
		Assert.assertNotNull(actual5);
		Assert.assertEquals("余额不足，扣费失败", expected5.toString(), actual5.toString());
    }
    
}
