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

    /**
     * 网站为ajax时，接口的地址
     */
    private String interfaceUrl;

    private String charSet = "UTF-8";
    /**
     * xpath or css or json
     */
    private String matchType = "css";

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

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getInterfaceUrl() {
        return interfaceUrl;
    }

    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl = interfaceUrl;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SiteSet{");
        sb.append("domain='").append(domain).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", interfaceUrl='").append(interfaceUrl).append('\'');
        sb.append(", charSet='").append(charSet).append('\'');
        sb.append(", matchType='").append(matchType).append('\'');
        sb.append(", selectMap=").append(selectMap);
        sb.append('}');
        return sb.toString();
    }
}
