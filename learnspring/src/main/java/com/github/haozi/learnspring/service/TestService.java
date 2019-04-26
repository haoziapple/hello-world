package com.github.haozi.learnspring.service;

import com.github.haozi.learnspring.aop.Demo;

/**
 * @author wanghao
 * @Description
 * @date 2019-04-26 9:14
 */
public interface TestService {
    /**
     * 接口类的方法上加注解，aop并不能拦截到方法执行
     * @return
     */
    @Demo
    Integer test();
}
