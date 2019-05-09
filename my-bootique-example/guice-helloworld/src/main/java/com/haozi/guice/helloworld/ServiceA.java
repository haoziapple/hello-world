package com.haozi.guice.helloworld;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-09 14:25
 */
public class ServiceA {
    public ServiceA() {
        System.out.println("construct ServiceA..");
    }

    public void print() {
        System.out.println("ServiceA is printing..");
    }
}
