package com.component.spider.service;

import com.component.spider.config.ExtractConfig;
import com.component.spider.config.SiteSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 2018/5/10.
 */
@Service
public class ExtractService {
    public static final String HTTP = "http://";
    private static final Logger log = LoggerFactory.getLogger(ExtractService.class);
    @Autowired
    private ExtractConfig extractConfig;

    public static SiteSet determinSite(String url, ExtractConfig extractConfig) {
        SiteSet matchSite = null;
        // 搜索域名匹配的站点
        for (Map.Entry<String, SiteSet> entry : extractConfig.getSite().entrySet()) {
            if (url.startsWith(HTTP + entry.getValue().getDomain())) {
                matchSite = entry.getValue();
                break;
            }
        }
        if (matchSite == null) {
            throw new RuntimeException("cannot find match site!");
        }

        return matchSite;
    }

    public Map<String, String> extract(String url) {
        SiteSet matchSite = determinSite(url, this.extractConfig);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("cannot get html from the url!");
        }

        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> entry : matchSite.getSelectMap().entrySet()) {
            map.put(entry.getKey(), doc.select(entry.getValue()).text());
        }

        return map;
    }
}
