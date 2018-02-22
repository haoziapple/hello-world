package com.wanghao.flowabletest;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author wanghao
 * @Description
 * @date 2018-02-22 16:03
 */
public class SendRejectionMail implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        System.out.println("Sending rejection mail for employee "
                + execution.getVariable("employee"));
    }
}
