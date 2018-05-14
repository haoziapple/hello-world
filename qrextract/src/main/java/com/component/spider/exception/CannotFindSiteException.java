package com.component.spider.exception;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-11 10:02
 */
public class CannotFindSiteException extends BizException {
    private String inferredTraceNo;

    public CannotFindSiteException() {
    }

    public CannotFindSiteException(String message, String inferredTraceNo) {
        super(message);
        this.inferredTraceNo = inferredTraceNo;
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

    public String getInferredTraceNo() {
        return inferredTraceNo;
    }
}
