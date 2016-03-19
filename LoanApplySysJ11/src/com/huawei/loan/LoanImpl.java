package com.huawei.loan;

import com.huawei.exam.OperationResult;
import com.huawei.exam.ReturnCodeEnum;

import java.text.DecimalFormat;

/**
 * <p>
 * Title:考生可以根据自己的需求在本类中增加变量和函数
 * </p>
 * <p/>
 * <p>
 * Description:贷款操作实现类
 * </p>
 * <p/>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p/>
 * <p>
 * Company:
 * </p>
 * 
 * @author unknown
 * @version 1.0
 */
public class LoanImpl 
{

	/*
	 * 无参数构造函数，考生无须关心；
	 */
	public LoanImpl() {

	}

	/**
	 * 考生需要实现的接口 reboot命令借口：系统重启/初始化操作
	 * 
	 * @return OperationResult：处理结果，直接返回处理结果的枚举类型
	 */
	public OperationResult opReboot() {
		// TODO 考生完成其中的逻辑，此处代码仅供参考。
		return OperationResult.createReturnResult(ReturnCodeEnum.E001);

	}

	/**
	 * 考生需要实现的接口 request命令接口：贷款人信息初始化
	 * 
	 * @param paraArray
	 *            命令发送过来的参数列表，为split("-")的结果 参数类型：贷款编号-贷款人月收入-贷款本金-贷款年限-申请时间
	 * @return OperationResult：处理结果，直接返回处理结果的枚举类型
	 */
	public OperationResult opReqeustLoan(int[] paraArray) {
		// TODO 考生完成其中的逻辑，此处代码仅供参考。
		return OperationResult.createReturnResult(ReturnCodeEnum.E009);
	}

	/**
	 * 考生需要实现的接口 request命令接口：贷款人信息初始化
	 * 
	 * @param paraArray
	 *            参数类型：贷款编号-第x个月份-住房公积金还款金额-查询时间
	 * @return OperationResult：处理结果，直接返回处理结果的枚举类型
	 */
	public OperationResult opList(int[] paraArray) {
		
		// TODO 考生完成其中的逻辑，此处代码仅供参考。
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("\n");

		double[] calcResults = new double[] { 0, 0, 0 };
		// 四舍五入
		DecimalFormat df = new DecimalFormat("0");
		// 金额信息
		stringBuffer.append("第").append(paraArray[0]).append("个月份的还款本息总金额:")
				.append(df.format(calcResults[0])).append("\r\n")
				.append("需还款本息总金额:").append(df.format(calcResults[1]))
				.append("\r\n").append("需个人还款本息总金额:")
				.append(df.format(calcResults[2]));

		return new OperationResult(stringBuffer.toString());
	}

}
