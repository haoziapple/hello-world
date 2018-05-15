package com.component.spider.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.component.spider.config.ExtractConfig;
import com.component.spider.config.SiteSet;
import com.component.spider.exception.BizException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 2018/5/10.
 */
@Service
public class ExtractService {
    public static final String TRACE_NO_REGREX = "\\{traceNo\\}";
    private static final Logger log = LoggerFactory.getLogger(ExtractService.class);
    @Autowired
    private ExtractConfig extractConfig;

    public Map<String, String> extract(String url) {
        SiteSet matchSite = ExtractHelper.determinSite(url, this.extractConfig);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BizException("cannot get html from the url!");
        }
        Html html = new Html(doc);

        Map<String, String> map = new HashMap<>();
        // 尝试从url里获取traceNo
        map.put("traceNo", ExtractHelper.findMatch(url, ExtractHelper.TRACE_NO_P));

        if ("ajax".equals(matchSite.getType()) && matchSite.getInterfaceUrl() != null) {
            // 接口类型的信息抓取
            String traceNo = ExtractHelper.findMatch(url, ExtractHelper.TRACE_NO_P);
            Document interDoc = null;
            try {
                interDoc = Jsoup.connect(matchSite.getInterfaceUrl().replaceFirst(TRACE_NO_REGREX, traceNo)).get();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new BizException("cannot get html from the url!");
            }
            Html interHtml = new Html(interDoc);
            String json = interHtml.xpath("/html/body/text()").toString();
            JSONObject jsonObject = JSON.parseObject(json);
            for (Map.Entry<String, String> entry : matchSite.getSelectMap().entrySet()) {
                map.put(entry.getKey(), JSONPath.eval(jsonObject, (entry.getValue())).toString());
            }
        } else {
            // 静态网站类型的信息抓取
            for (Map.Entry<String, String> entry : matchSite.getSelectMap().entrySet()) {
                map.put(entry.getKey(), ExtractHelper.extractValue(doc, entry.getValue(), matchSite.getMatchType()));
            }
        }

        return map;
    }


}
