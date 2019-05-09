package com.haozi.guice.helloworld.module;

import com.google.inject.AbstractModule;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-09 15:22
 */
public class BillModule extends AbstractModule {

    @Override
    protected void configure() {

        /*
         * This tells Guice that whenever it sees a dependency on a TransactionLog,
         * it should satisfy the dependency using a DatabaseTransactionLog.
         */
        bind(TransactionLog.class).to(DatabaseTransactionLog.class);

        /*
         * Similarly, this binding tells Guice that when CreditCardProcessor is used in
         * a dependency, that should be satisfied with a PaypalCreditCardProcessor.
         */
        bind(CreditCardProcessor.class).to(PaypalCreditCardProcessor.class);
    }
}
