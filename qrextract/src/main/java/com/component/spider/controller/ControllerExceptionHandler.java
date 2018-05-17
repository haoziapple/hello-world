package com.component.spider.controller;

import com.component.spider.exception.BizException;
import com.component.spider.exception.CannotFindSiteException;
import com.component.spider.exception.ConfigErrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-11 9:53
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    public static final int SYS_ERR = 100001;
    /**
     * 业务异常
     */
    public static final int BIZ_ERR = 200001;
    /**
     * 找不到匹配站点
     */
    public static final int CANNOT_FIND_SITE = 200002;
    /**
     * 配置错误
     */
    public static final int CONFIG_ERR = 200003;
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(CannotFindSiteException.class)
    @ResponseBody
    public ActionResult processCannotFindSiteException(CannotFindSiteException e) {
        log.debug(e.getMessage() + "inferredTraceNo: " + e.getInferredTraceNo());
        Map<String, String> map = new HashMap<>();
        map.put("traceNo", e.getInferredTraceNo());
        return new ActionResult(CANNOT_FIND_SITE, e.getMessage(), map);
    }

    @ExceptionHandler(ConfigErrException.class)
    @ResponseBody
    public ActionResult processCannotFindSiteException(ConfigErrException e) {
        log.debug(e.getMessage());
        return new ActionResult(CONFIG_ERR, e.getMessage());
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ActionResult processBizException(BizException e) {
        log.debug(e.getMessage());
        return new ActionResult(BIZ_ERR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ActionResult processException(Exception e) {
        log.error(e.getMessage(), e);
        return new ActionResult(SYS_ERR, e.getMessage());
    }
}
