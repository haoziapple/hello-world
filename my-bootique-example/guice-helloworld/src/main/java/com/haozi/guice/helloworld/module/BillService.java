package com.haozi.guice.helloworld.module;

import com.google.inject.Inject;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-09 15:13
 */
public class BillService {
    private final CreditCardProcessor processor;
    private final TransactionLog transactionLog;

    @Inject
    BillService(CreditCardProcessor processor,
                TransactionLog transactionLog) {
        this.processor = processor;
        this.transactionLog = transactionLog;
    }

    public String takeOrder() {
        System.out.println(processor.useCreditCard());
        System.out.println(transactionLog.takeTransactionLog());

        return "takeOrder success";
    }
}
