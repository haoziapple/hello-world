package com.github.haozi.learnspring;

import com.github.haozi.learnspring.aop.Demo;
import com.github.haozi.learnspring.dao.TestMapper;
import com.github.haozi.learnspring.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghao
 * @Description
 * @date 2019-04-25 20:13
 */
@RestController
public class TestController {
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private TestService testService;

    @Demo
    @GetMapping("/test")
    public String test() {
        return testMapper.test().toString();
    }

    @GetMapping("/test2")
    public String test2() {
        return testService.test().toString();
    }

}
