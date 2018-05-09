package com.fast.spider.controller;

import lombok.Data;
import lombok.ToString;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-09 16:04
 */
@Data
@ToString
public class ActionResult<T> {
    /**
     * 响应码
     */
    protected int code;

    /**
     * 响应消息
     */
    protected String message;

    /**
     * 响应对象
     */
    protected T value;

    public ActionResult()
    {
        code = 0;
        message="成功";

    }
    public ActionResult(T value) {
        this.code = 0;
        this.message = "成功";
        this.value = value;
    }

    public ActionResult(int code, String message, T value)
    {
        this.code = code;
        this.message = message;
        this.value = value;
    }
    public ActionResult(int code, String message)
    {
        this.code = code;
        this.message = message;
        this.value = null;
    }
}
