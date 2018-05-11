package com.component.spider.exception;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-11 10:02
 */
public class CannotFindSiteException extends BizException {
    public CannotFindSiteException() {
    }

    public CannotFindSiteException(String message) {
        super(message);
    }

    public CannotFindSiteException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFindSiteException(Throwable cause) {
        super(cause);
    }

    public CannotFindSiteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
