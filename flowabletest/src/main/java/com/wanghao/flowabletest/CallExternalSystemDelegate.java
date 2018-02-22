package com.wanghao.flowabletest;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author wanghao
 * @Description
 * @date 2018-02-22 15:54
 */
public class CallExternalSystemDelegate implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        System.out.println("Calling the external system for employee "
                + execution.getVariable("employee"));
    }
}
