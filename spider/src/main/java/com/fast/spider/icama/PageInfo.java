package com.fast.spider.icama;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author wanghao
 * @Description icama网站专用分页bean
 * @date 2018-05-07 14:10
 */
@Data
@ToString
public class PageInfo <T>  {
    private List<T> rows;
    private Integer total;
    private Integer records;
    private Integer page;
}
