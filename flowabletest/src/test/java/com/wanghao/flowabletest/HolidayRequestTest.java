package com.wanghao.flowabletest;

import org.flowable.engine.ProcessEngine;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/6/5 0005.
 */
public class HolidayRequestTest {
    private ProcessEngine processEngine;

    @org.junit.Before
    public void setUp() throws Exception {
        processEngine = HolidayRequest.getProcessEngine("mysql");
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void main() throws Exception {

    }

}