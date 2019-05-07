package com.github.haozi.bootique.helloworld.service;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-07 14:32
 */
public class ConfigServiceImpl implements ConfigService {
    private int intProperty;
    private String stringProperty;

    private MyService myService;

    public ConfigServiceImpl(int intProperty, String stringProperty, MyService myService) {
        this.intProperty = intProperty;
        this.stringProperty = stringProperty;
        this.myService = myService;
    }

    @Override
    public String testConfig() {
        return myService.serviceName() + ":" + intProperty + ":" + stringProperty;
    }
}
