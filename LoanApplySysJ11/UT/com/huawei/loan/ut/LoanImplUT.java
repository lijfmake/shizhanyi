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
package com.huawei.loan.ut;
import static org.junit.Assert.*;

import org.junit.*;
import com.huawei.exam.*;
import com.huawei.loan.*;

public class LoanImplUT
{
    LoanImpl loanImpl = null;
    
    @Before
    public void setUp()
        throws Exception
    {
        loanImpl = new LoanImpl();
    }
    
    @After
    public void tearDown()
        throws Exception
    {
        loanImpl = null;
    }
    
    @Test
    public void testOpReboot()
    {
        OperationResult actual = loanImpl.opReboot();        
        OperationResult expected = OperationResult.createReturnResult(ReturnCodeEnum.E001);
        Assert.assertNotNull(actual);
        Assert.assertEquals("", expected.toString(), actual.toString());
    }
    
    @Test
    public void testOpReqeustLoan()
    {
        //TODO:
        fail("Not yet implemented");
    }
    
    @Test
    public void testOpList()
    {
        //TODO:
        fail("Not yet implemented");
    }
    
}
