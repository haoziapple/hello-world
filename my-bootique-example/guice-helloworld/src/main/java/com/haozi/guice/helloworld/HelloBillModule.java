package com.haozi.guice.helloworld;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.haozi.guice.helloworld.module.BillModule;
import com.haozi.guice.helloworld.module.BillService;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-09 15:24
 */
public class HelloBillModule {
    public static void main(String[] args) {
        /*
         * Guice.createInjector() takes your Modules, and returns a new Injector
         * instance. Most applications will call this method exactly once, in their
         * main() method.
         */
        Injector injector = Guice.createInjector(new BillModule());

        /*
         * Now that we've got the injector, we can build objects.
         */
        BillService billService = injector.getInstance(BillService.class);
        System.out.println(billService.takeOrder());
    }
}
