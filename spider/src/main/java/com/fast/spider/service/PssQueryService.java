package com.fast.spider.service;

import com.fast.spider.controller.ProductInfo;
import com.fast.spider.icama.IcamaHttpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author wanghao
 * @Description http://pss.veyong.com查询产品service
 * @date 2018-05-09 16:14
 */
@Service
public class PssQueryService {
    public static final String WEB_URL = "http://pss.veyong.com/myQRCode.aspx?code=";

    public static final String query_URL = "http://pss.veyong.com/GetData.ashx?";
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询商品信息
     *
     * @param qrUrl
     * @return
     */
    public ProductInfo queryProduct(String qrUrl) {
        String traceNo = qrUrl.substring(WEB_URL.length());
        String queryString = "BOTTLE_NUMBER=" + traceNo;
        // 设置请求头
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, new HttpHeaders());
        //发起请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(query_URL + queryString, HttpMethod.GET, requestEntity, String.class);

        Map<String, String> map = IcamaHttpTemplate.gson.fromJson(responseEntity.getBody(), Map.class);
        ProductInfo info = new ProductInfo();
        info.setCompanyName(map.get("COMPANY_NAME"));
        info.setProductCategory(map.get("PRODUCT_CATEGORY"));
        info.setProductMark(map.get("PRODUCT_MARK"));
        info.setProductName(map.get("PRODUCT_NAME"));
        info.setProductSpec(map.get("PRODUCT_SPEC"));

        return info;
    }


}
