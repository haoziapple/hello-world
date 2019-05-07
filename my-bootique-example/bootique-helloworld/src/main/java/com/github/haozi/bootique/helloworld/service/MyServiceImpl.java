package com.github.haozi.bootique.helloworld.service;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-07 10:46
 */
public class MyServiceImpl implements MyService {
    @Override
    public String serviceName() {
        return "wang hao service";
    }
}
