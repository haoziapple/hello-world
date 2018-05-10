package com.component.spider.config;

import java.util.Map;

/**
 * Created by ASUS on 2018/5/10.
 */
public class SiteSet {
    private String domain;
    /**
     * ajax or static
     */
    private String type = "static";

    private String charSet = "UTF-8";

    private Map<String, String> selectMap;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getSelectMap() {
        return selectMap;
    }

    public void setSelectMap(Map<String, String> selectMap) {
        this.selectMap = selectMap;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    @Override
    public String toString() {
        return "SiteSet{" +
                "domain='" + domain + '\'' +
                ", type='" + type + '\'' +
                ", charSet='" + charSet + '\'' +
                ", selectMap=" + selectMap +
                '}';
    }
}
