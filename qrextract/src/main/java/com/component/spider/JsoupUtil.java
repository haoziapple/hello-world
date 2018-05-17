package com.component.spider;

import com.component.spider.config.ConnectConfig;
import com.component.spider.config.SiteSet;
import com.component.spider.exception.BizException;
import com.component.spider.exception.ConfigErrException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author wanghao
 * @Description jsoup工具类
 * @date 2018-05-16 14:14
 */
public class JsoupUtil {
    public static final String TRACE_NO_REGREX = "\\{traceNo\\}";

    private JsoupUtil() {
    }

    private static final Logger log = LoggerFactory.getLogger(JsoupUtil.class);

    /**
     * 根据url获取文档doc
     *
     * @param url
     * @param config
     * @return
     */
    public static Document getDoc(String url, ConnectConfig config) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(config.getTimeout()).get();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BizException("cannot get html from the url!");
        }
        return doc;
    }

    /**
     * 接口请求：ajax类型的网站
     *
     * @param siteConfig
     * @param connectConfig
     * @return
     */
    public static Document getAjaxDoc(String traceNo, SiteSet siteConfig, ConnectConfig connectConfig) {
        Document doc = null;
        try {
            Connection connect = Jsoup
                    .connect(siteConfig.getInterfaceUrl().replaceAll(TRACE_NO_REGREX, traceNo))
                    .timeout(connectConfig.getTimeout());
            // 设置请求头
            if (siteConfig.getHeaderMap() != null && !siteConfig.getHeaderMap().isEmpty()) {
                for (Map.Entry<String, String> headerEntry : siteConfig.getHeaderMap().entrySet()) {
                    connect.header(headerEntry.getKey(), headerEntry.getValue().replaceAll(TRACE_NO_REGREX, traceNo));
                }
            }
            // 设置请求form
            if (siteConfig.getFormMap() != null && !siteConfig.getFormMap().isEmpty()) {
                for (Map.Entry<String, String> formEntry : siteConfig.getFormMap().entrySet()) {
                    connect.data(formEntry.getKey(), formEntry.getValue().replaceAll(TRACE_NO_REGREX, traceNo));
                }
            }
            // 设置请求体
            if (siteConfig.getBodyString() != null) {
                connect.requestBody(siteConfig.getBodyString().replaceAll(TRACE_NO_REGREX, traceNo));
            }

            if (SiteSet.Type.AJAX_GET.equals(siteConfig.getType())) {
                doc = connect.get();
            } else if (SiteSet.Type.AJAX_POST.equals(siteConfig.getType())) {
                doc = connect.post();
            } else {
                throw new ConfigErrException("站点类型,type配置错误: " + siteConfig.getType());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BizException("cannot get html from the url!");
        }
        return doc;
    }

}
