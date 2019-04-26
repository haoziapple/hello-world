package com.github.haozi.learnspring.dao;

import com.github.haozi.learnspring.aop.Demo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author wanghao
 * @Description
 * @date 2019-04-25 20:11
 */
@Mapper
@Repository
public interface TestMapper {
    /**
     * Mybatis的DAO方法上加注解，springboot2.0以上版本的aop可以拦截到方法
     * 参考：http://www.importnew.com/28788.html
     * @return
     */
    @Demo
    Integer test();
}
