package com.component.spider.config;

import java.util.Map;

/**
 * Created by ASUS on 2018/5/10.
 */
public class SiteSet {
    public static class Type {
        public static final String STATIC = "static";
        public static final String AJAX_GET = "ajaxget";
        public static final String AJAX_POST = "ajaxpost";
    }
    public static class MatchType {
        public static final String XPATH = "xpath";
        public static final String CSS = "css";
        public static final String JSON = "json";
    }

    private String domain;
    /**
     * static or ajaxget or ajaxpost
     */
    private String type = Type.STATIC;

    /**
     * 网站为ajax时，接口的地址
     */
    private String interfaceUrl;

    private String charSet = "UTF-8";
    /**
     * xpath or css or json
     */
    private String matchType = MatchType.CSS;

    /**
     * 选择器map
     */
    private Map<String, String> selectMap;

    /**
     * http请求header map
     */
    private Map<String, String> headerMap;

    /**
     * http请求form map
     */
    private Map<String, String> formMap;

    /**
     * http请求体
     */
    private String bodyString;

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

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public Map<String, String> getFormMap() {
        return formMap;
    }

    public void setFormMap(Map<String, String> formMap) {
        this.formMap = formMap;
    }

    public String getBodyString() {
        return bodyString;
    }

    public void setBodyString(String bodyString) {
        this.bodyString = bodyString;
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
        sb.append(", headerMap=").append(headerMap);
        sb.append(", formMap=").append(formMap);
        sb.append(", bodyString='").append(bodyString).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
