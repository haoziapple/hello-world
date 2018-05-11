package com.component.spider.service;

import com.component.spider.config.ExtractConfig;
import com.component.spider.config.SiteSet;
import com.component.spider.exception.BizException;
import com.component.spider.exception.CannotFindSiteException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ASUS on 2018/5/10.
 */
@Service
public class ExtractService {
    public static final String HTTP = "http://";
    public static final Pattern pattern = Pattern.compile("[0-9]{32}");
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
            throw new CannotFindSiteException("cannot find match site!");
        }
        return matchSite;
    }

    public static String filterWords(String extractString, ExtractConfig extractConfig) {
        for (String skipWord : extractConfig.getSkipWords()) {
            extractString = extractString.replaceAll(skipWord, "").trim();
        }
        return extractString;
    }


    public Map<String, String> extract(String url) {
        SiteSet matchSite = determinSite(url, this.extractConfig);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BizException("cannot get html from the url!");
        }
        Html html = new Html(doc);

        Map<String, String> map = new HashMap<>();
        if ("xpath".equals(matchSite.getMatchType())) {
            for (Map.Entry<String, String> entry : matchSite.getSelectMap().entrySet()) {
                map.put(entry.getKey(), filterWords(html.xpath(entry.getValue()).toString(), this.extractConfig));
            }
        } else if ("css".equals(matchSite.getMatchType())) {
            for (Map.Entry<String, String> entry : matchSite.getSelectMap().entrySet()) {
                map.put(entry.getKey(), filterWords(doc.select(entry.getValue()).text(), this.extractConfig));
            }
        } else {
            throw new BizException("网站matchType配置有误，请调整配置！");
        }
        Matcher m = pattern.matcher(url);
        if (m.find()) {
            map.put("traceNo", m.group());
        }

        return map;
    }
}
