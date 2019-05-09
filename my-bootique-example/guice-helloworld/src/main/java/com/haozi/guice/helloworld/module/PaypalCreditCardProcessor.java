package com.haozi.guice.helloworld.module;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-09 15:15
 */
public class PaypalCreditCardProcessor implements CreditCardProcessor {
    @Override
    public String useCreditCard() {
        System.out.println("process Paypal credit card..");
        return "use paypal";
    }
}
