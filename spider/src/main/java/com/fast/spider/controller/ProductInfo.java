package com.fast.spider.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-09 16:06
 */
@Data
@ToString
@ApiModel("产品信息")
public class ProductInfo {
    @ApiModelProperty("公司名称")
    private String companyName;
    @ApiModelProperty("产品商标")
    private String productMark;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品规格")
    private String productSpec;
    @ApiModelProperty("产品类别")
    private String productCategory;
}
