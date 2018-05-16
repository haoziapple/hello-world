package com.example.demo.controller;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author wanghao
 * @Description 尝试一些array操作
 * @date 2018-05-16 14:43
 */
@RestController
@RequestMapping("/array")
public class ArrayController {

    @RequestMapping(value = "/merge", method = RequestMethod.GET)
    public String[] merge(String[] a, String[] b) {
        String[] result = new String[a.length + b.length];
//        for (int i = 0; i < a.length; i++) {
//            result[i] = a[i];
//        }
//        for (int j = a.length; j < a.length + b.length; j++) {
//            result[j] = b[j - a.length];
//        }
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);

        return result;
    }
}
