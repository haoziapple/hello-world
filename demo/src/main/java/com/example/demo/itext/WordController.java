package com.example.demo.itext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author wanghao
 * @Description
 * @date 2018-04-20 10:33
 */
@Controller
public class WordController {

    @Autowired
    private WordService wordService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(HttpServletResponse res) {
        System.out.println("test");
    }

    @RequestMapping(value = "/testDownload", method = RequestMethod.GET)
    public void testDownload(HttpServletResponse res) {
        String fileName = "test.doc";
//        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            wordService.createRTFContext(os);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("success");
    }
}
