package com.haozi.guice.helloworld.module;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-09 15:17
 */
public class DatabaseTransactionLog implements TransactionLog {
    @Override
    public String takeTransactionLog() {
        System.out.println("take database transaction log..");
        return "use DatabaseTransactionLog";
    }
}
