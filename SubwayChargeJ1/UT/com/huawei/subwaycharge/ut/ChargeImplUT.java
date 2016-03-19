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

import static org.junit.Assert.*;
import org.junit.*;

import com.huawei.exam.ReturnCodeEnum;
import com.huawei.subwaycharge.*;

public class ChargeImplUT
{
    ChargeImpl chargeImpl = null;
    
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
        OpResult expected = OpResult.createOpResult(ReturnCodeEnum.E01);
        Assert.assertNotNull(actual);
        Assert.assertEquals("", expected.toString(), actual.toString());
    }
    
    @Test
    public void testOpQuery()
    {
        //TODO:
        fail("Not yet implemented");
    }
    
    @Test
    public void testOpCharge()
    {
        //TODO:
        fail("Not yet implemented");
    }
    
}
