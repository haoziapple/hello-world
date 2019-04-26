package com.github.haozi.learnspring.aop;

import java.lang.annotation.*;

/**
 * @author wanghao
 * @Description
 * @date 2019-04-25 20:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Demo {
}
