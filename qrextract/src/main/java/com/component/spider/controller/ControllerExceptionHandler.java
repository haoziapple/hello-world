package com.component.spider.controller;

import com.component.spider.exception.BizException;
import com.component.spider.exception.CannotFindSiteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-11 9:53
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    public static final int SYS_ERR = 100001;
    public static final int BIZ_ERR = 200001;
    public static final int CANNOT_FIND_SITE = 200002;
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ActionResult processException(Exception e) {
        log.error(e.getMessage(), e);
        return new ActionResult(SYS_ERR, e.getMessage());
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ActionResult processBizException(BizException e) {
        log.debug(e.getMessage());
        return new ActionResult(BIZ_ERR, e.getMessage());
    }

    @ExceptionHandler(CannotFindSiteException.class)
    @ResponseBody
    public ActionResult processCannotFindSiteException(CannotFindSiteException e) {
        log.debug(e.getMessage());
        return new ActionResult(CANNOT_FIND_SITE, e.getMessage());
    }
}
