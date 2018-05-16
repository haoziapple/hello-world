package com.component.spider;

import com.component.spider.config.ConnectConfig;
import com.component.spider.exception.BizException;
import com.component.spider.service.ExtractHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author wanghao
 * @Description jsoup工具类
 * @date 2018-05-16 14:14
 */
public class JsoupUtil {
    private JsoupUtil() {
    }

    private static final Logger log = LoggerFactory.getLogger(JsoupUtil.class);

    /**
     * 根据url获取文档doc
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

}
