package com.component.spider.controller;

import com.component.spider.config.ExtractConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置文件controller
 * Created by ASUS on 2018/5/10.
 */
@RestController
@RequestMapping("/prop")
public class PropController {

    @Value("${extract:test}")
    private String extract;
    @Autowired
    private ExtractConfig extractConfig;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return extractConfig.toString();
    }
}
