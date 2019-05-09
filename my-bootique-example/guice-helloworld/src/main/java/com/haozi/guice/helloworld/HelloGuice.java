package com.haozi.guice.helloworld;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-09 14:08
 */
public class HelloGuice {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        ServiceA serviceA = injector.getInstance(ServiceA.class);
        serviceA.print();
    }
}
