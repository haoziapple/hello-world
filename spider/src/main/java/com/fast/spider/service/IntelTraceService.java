package com.fast.spider.service;

import com.fast.spider.controller.ProductInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @author wanghao
 * @Description http://www.inteltrace.com查询service
 * @date 2018-05-09 18:36
 */
@Service
public class IntelTraceService {
    public static final String WEB_URL = "http://www.inteltrace.com/index.php/qx/code/";
    public static final String BR = "<br>";
    private RestTemplate restTemplate;
    @Autowired
    public IntelTraceService(RestTemplate restTemplate) {
        this.restTemplate = new RestTemplate();
        BeanUtils.copyProperties(restTemplate, this.restTemplate);
        this.restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public ProductInfo queryProduct(String qrUrl) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(type);
        // 设置请求头
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        //发起请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(qrUrl, HttpMethod.GET, requestEntity, String.class);
        String html = responseEntity.getBody();

        Document doc = Jsoup.parse(html);
        ProductInfo info = new ProductInfo();
        info.setCompanyName(doc.select("#tab-one > div > p:nth-child(2)").text());
        String[] productNameNproductSpec = doc.select("body > div.content > div.container > p").html().split(BR);
        if(productNameNproductSpec.length > 1) {
            info.setProductName(productNameNproductSpec[0]);
            info.setProductSpec(productNameNproductSpec[1]);
        }

        return info;
    }
}
