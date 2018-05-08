package com.fast.spider.icama;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-07 16:51
 */
@Service
public class IcamaHttpTemplate {
    @Autowired
    private RestTemplate restTemplate;
    public static final String baseUrl = "https://www.icama.cn/PublicQuery";
    public static final String pesticideUrl = "/pesticide/jqGrid.do";
    public static final Gson gson = new GsonBuilder().create();


    public PageInfo<PresticideDetail> search(Boolean search, Long nd, Integer rows, Integer page, String sort, Integer flage, String queryJson) {
        String queryString = "?" +
                "_search" + "=" + search + "&" +
                "nd" + "=" + nd + "&" +
                "rows" + "=" + rows + "&" +
                "page" + "=" + page + "&" +
                "sort" + "=" + sort + "&" +
                "flage" + "=" + flage + "&" +
                "queryJson={queryJson}";
        HttpEntity<String> requestEntity = getRequestEntity();


        //发起请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + pesticideUrl + queryString, HttpMethod.GET, requestEntity, String.class, queryJson);


        // 设置类型信息
        Type objectType = new TypeToken<PageInfo<PresticideDetail>>() {
        }.getType();
        PageInfo<PresticideDetail> result = gson.fromJson(responseEntity.getBody(), objectType);
        return result;
    }

    private HttpEntity<String> getRequestEntity() {
        //请求头设置
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Cookie", "JSESSIONID=E510932B00C982BB8AB7CAB781FC62E8");
        requestHeaders.set("Host", "www.icama.cn");
        requestHeaders.set("Upgrade-Insecure-Requests", "1");
        //设置 签名 查询参数 访问令牌
        return new HttpEntity<String>(null, requestHeaders);
    }

    public String getPesticideDetail(String id) {
        return null;
    }
}
