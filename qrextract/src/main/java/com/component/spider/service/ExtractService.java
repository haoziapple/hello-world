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
    public static final Pattern pattern = Pattern.compile("[123][0-9]{31}");
    public static final String SKIP_STR = "{skip}";
    public static final String SKIP = "\\{skip\\}";
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
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new BizException("cannot get html from the url!");
            }
            Html html = new Html(doc);
            String traceNo = findMatch(html.toString(), pattern);
            traceNo = (traceNo == null ? findMatch(url, pattern) : traceNo);
            throw new CannotFindSiteException("cannot find match site!", traceNo);
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
            throw new BizException("cannot get html from the url!");
        }
        Html html = new Html(doc);

        Map<String, String> map = new HashMap<>();
        if ("xpath".equals(matchSite.getMatchType())) {
            for (Map.Entry<String, String> entry : matchSite.getSelectMap().entrySet()) {
                if (entry.getValue().contains(SKIP_STR)) {
                    int skipNum = Integer.parseInt(entry.getValue().split(SKIP)[1]);
                    String extractString = html.xpath(entry.getValue().split(SKIP)[0]).toString().trim();
                    map.put(entry.getKey(), extractString.substring(skipNum).trim());
                } else {
                    map.put(entry.getKey(), html.xpath(entry.getValue()).toString().trim());
                }
            }
        } else if ("css".equals(matchSite.getMatchType())) {
            for (Map.Entry<String, String> entry : matchSite.getSelectMap().entrySet()) {
                if (entry.getValue().contains(SKIP_STR)) {
                    int skipNum = Integer.parseInt(entry.getValue().split(SKIP)[1]);
                    String extractString = doc.select(entry.getValue().split(SKIP)[0]).text().trim();
                    map.put(entry.getKey(), extractString.substring(skipNum).trim());
                } else {
                    map.put(entry.getKey(), doc.select(entry.getValue()).text().trim());
                }
            }
        } else {
            throw new BizException("网站matchType配置有误，请调整配置！");
        }
        map.put("traceNo", findMatch(url, pattern));

        return map;
    }

    /**
     * 根据正则查找匹配的第一个字串，没匹配到返回null
     *
     * @param text
     * @param pattern
     * @return
     */
    private static String findMatch(String text, Pattern pattern) {
        Matcher m = pattern.matcher(text);
        if (m.find()) {
            return m.group();
        } else {
            return null;
        }
    }
}
