package com.component.spider.controller;

import com.component.spider.service.ExtractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by ASUS on 2018/5/10.
 */
@RestController
@RequestMapping("/extract")
public class ExtractController {
    @Autowired
    private ExtractService extractService;
    private static final Logger log = LoggerFactory.getLogger(ExtractController.class);

    @RequestMapping(value = "/staticHtml", method = RequestMethod.GET)
    public ActionResult<Map<String, String>> staticHtml(@RequestParam("url") String url) {
        log.debug(url);
        Map<String, String> resultMap = extractService.extract(url);
        log.debug("extract map:" + resultMap);
        return new ActionResult<>(resultMap);
    }
}
