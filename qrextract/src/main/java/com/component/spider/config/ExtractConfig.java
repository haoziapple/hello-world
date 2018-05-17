package com.component.spider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 抽取配置类
 * Created by ASUS on 2018/5/10.
 */
@ConfigurationProperties(prefix = "extract")
@Component
public class ExtractConfig  {
    private Map<String, SiteSet> site;

    private ConnectConfig connect;

    public Map<String, SiteSet> getSite() {
        return site;
    }

    public void setSite(Map<String, SiteSet> site) {
        this.site = site;
    }

    public ConnectConfig getConnect() {
        return connect;
    }

    public void setConnect(ConnectConfig connect) {
        this.connect = connect;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ExtractConfig{");
        sb.append("site=").append(site);
        sb.append(", connect=").append(connect);
        sb.append('}');
        return sb.toString();
    }
}
