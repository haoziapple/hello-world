package com.component.spider.exception;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-17 16:55
 */
public class ConfigErrException extends BizException {
    public ConfigErrException() {
    }

    public ConfigErrException(String message) {
        super(message);
    }

    public ConfigErrException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigErrException(Throwable cause) {
        super(cause);
    }

    public ConfigErrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
