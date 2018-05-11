package com.component.spider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

/**
 * 抽取配置类
 * Created by ASUS on 2018/5/10.
 */
@ConfigurationProperties(prefix="extract")
@Component
public class ExtractConfig {
    private Map<String, SiteSet> site;

    private String[] skipWords;

    public Map<String, SiteSet> getSite() {
        return site;
    }

    public void setSite(Map<String, SiteSet> site) {
        this.site = site;
    }

    public String[] getSkipWords() {
        return skipWords;
    }

    public void setSkipWords(String[] skipWords) {
        this.skipWords = skipWords;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ExtractConfig{");
        sb.append("site=").append(site);
        sb.append(", skipWords=").append(skipWords == null ? "null" : Arrays.asList(skipWords).toString());
        sb.append('}');
        return sb.toString();
    }
}
