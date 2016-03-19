package com.huawei.loan;

import com.huawei.exam.Command;
import com.huawei.exam.ExamCommand;
import com.huawei.exam.ExamServer;

/**
 * <p>Title: 主执行类</p>
 * <p/>
 * <p>Description: 考生不得修改，亦无须关注此文件内容</p>
 * <p/>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p/>
 * <p>Company: </p>
 *
 * @author unknown
 * @version 1.0
 */

public class LoanMain {
    public static void main(String[] args) 
    {
        /**
         * 启动Socket服务侦听5555端口，从Socket获取命令，会丢给Command类的command函数执行
         * Command类的command函数已经实现了从Socket接收到字符串后的解析与分发 考生只需要实现LoanImpl类的各命令接口即可。
         */
        Command cmd = new ExamCommand();
        ExamServer examServer = new ExamServer(cmd);
        examServer.run(args);
    }

    /**
     * 私有构造函数,此类无需实例化
     */
    private LoanMain() {

    }
}
