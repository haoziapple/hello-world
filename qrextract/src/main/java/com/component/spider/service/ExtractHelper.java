package com.component.spider.service;

import com.component.spider.JsoupUtil;
import com.component.spider.config.ExtractConfig;
import com.component.spider.config.SiteSet;
import com.component.spider.exception.BizException;
import com.component.spider.exception.CannotFindSiteException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-15 8:45
 */
public class ExtractHelper {
    public static final String HTTP = "http://";
    public static final Pattern TRACE_NO_P = Pattern.compile("[123][0-9]{31}");
    public static final String SKIP_STR = "{skip}";
    public static final String SKIP_REGREX = "\\{skip\\}";

    private static final Logger log = LoggerFactory.getLogger(ExtractHelper.class);


    private ExtractHelper() {
    }

    /**
     * 根据url查找匹配的站点
     *
     * @param url
     * @param extractConfig
     * @return
     */
    public static SiteSet determinSite(String url, ExtractConfig extractConfig, String htmlStr) {
        SiteSet matchSite = null;
        // 搜索域名匹配的站点
        for (Map.Entry<String, SiteSet> entry : extractConfig.getSite().entrySet()) {
            if (url.startsWith(HTTP + entry.getValue().getDomain())) {
                matchSite = entry.getValue();
                break;
            }
        }
        if (matchSite == null) {
            String traceNo = findMatch(htmlStr, TRACE_NO_P);
            traceNo = (traceNo == null ? findMatch(url, TRACE_NO_P) : traceNo);
            throw new CannotFindSiteException("cannot find match site!", traceNo);
        }
        return matchSite;
    }

    /**
     * 根据正则查找匹配的第一个字串，没匹配到返回null
     *
     * @param text
     * @param pattern
     * @return
     */
    public static String findMatch(String text, Pattern pattern) {
        Matcher m = pattern.matcher(text);
        if (m.find()) {
            return m.group();
        } else {
            return null;
        }
    }


    /**
     * 根据表达式查找匹配值
     *
     * @param doc
     * @param expression
     * @param type       css or xpath
     * @return
     */
    public static String extractValue(Document doc, String expression, String type) {
        int skipNum = 0;
        if (expression.contains(SKIP_STR)) {
            skipNum = Integer.parseInt(expression.split(SKIP_REGREX)[1]);
            expression = expression.split(SKIP_REGREX)[0];
        }
        if (SiteSet.MatchType.XPATH.equals(type)) {
            return new Html(doc).xpath(expression).toString().trim().substring(skipNum).trim();
        } else if (SiteSet.MatchType.CSS.equals(type)) {
            return doc.select(expression).text().trim().substring(skipNum).trim();
        }else {
            throw new BizException("网站matchType配置有误，请调整配置！");
        }
    }
}
