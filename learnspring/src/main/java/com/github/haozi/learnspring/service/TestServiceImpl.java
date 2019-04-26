package com.github.haozi.learnspring.service;

import com.github.haozi.learnspring.dao.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanghao
 * @Description
 * @date 2019-04-26 9:14
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Override
    public Integer test() {
        return testMapper.test();
    }
}
