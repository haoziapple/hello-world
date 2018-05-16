package com.component.spider.config;

/**
 * @author wanghao
 * @Description 连接配置类
 * @date 2018-05-16 14:09
 */
public class ConnectConfig {
    private Integer timeout = 30000;

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ConnectConfig{");
        sb.append("timeout=").append(timeout);
        sb.append('}');
        return sb.toString();
    }
}
