package com.github.haozi.bootique.helloworld.factory;

import com.github.haozi.bootique.helloworld.service.ConfigService;
import com.github.haozi.bootique.helloworld.service.ConfigServiceImpl;
import com.github.haozi.bootique.helloworld.service.MyService;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-07 14:38
 */
public class ConfigFactory {

    private int intProperty;
    private String stringProperty;

    public void setIntProperty(int i) {
        this.intProperty = i;
    }

    public void setStringProperty(String s) {
        this.stringProperty = s;
    }

    // factory method
    public ConfigService createConfigService(MyService myService) {
        return new ConfigServiceImpl(intProperty, stringProperty, myService);
    }
}
