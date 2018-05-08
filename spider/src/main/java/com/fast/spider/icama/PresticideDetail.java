package com.fast.spider.icama;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author wanghao
 * @Description 农药详情
 * @date 2018-05-07 14:12
 */
@Data
@ApiModel("农药产品信息")
public class PresticideDetail {
    private String id;
    @ApiModelProperty("农药名称")
    private String pesticideName;
    @ApiModelProperty("农药登记证号")
    private String certificateCode;
    @ApiModelProperty("农药类别码")
    private String pesticideCategoryCode;
    @ApiModelProperty("农药类别")
    private String pesticideCategory;
    @ApiModelProperty("总含量")
    private String totalContent;
    @ApiModelProperty("毒性码")
    private String toxicityCode;
    @ApiModelProperty("毒性")
    private String toxicity;
    @ApiModelProperty("剂型码")
    private String dosageCode;
    @ApiModelProperty("剂型")
    private String dosage;
    @ApiModelProperty("有效期开始日期")
    private Long validStartDay;
    @ApiModelProperty("有效期截止日期")
    private Long validLastDay;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("持有人名称")
    private String holderName;
    @ApiModelProperty("持有人id")
    private String holderId;

    /**
     * 为了方便导出mysql脚本，重写了toString方法
     * @return
     */
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(");
        sb.append("'").append(id).append('\'');
        sb.append(",'").append(pesticideName).append('\'');
        sb.append(",'").append(certificateCode).append('\'');
        sb.append(",'").append(pesticideCategoryCode).append('\'');
        sb.append(",'").append(pesticideCategory).append('\'');
        sb.append(",'").append(totalContent).append('\'');
        sb.append(",'").append(toxicityCode).append('\'');
        sb.append(",'").append(toxicity).append('\'');
        sb.append(",'").append(dosageCode).append('\'');
        sb.append(",'").append(dosage).append('\'');
        sb.append(",'").append(validStartDay).append('\'');
        sb.append(",'").append(validLastDay).append('\'');
        sb.append(",'").append(status).append('\'');
        sb.append(",'").append(holderName).append('\'');
        sb.append(",'").append(holderId).append('\'');
        sb.append("),");
        return sb.toString();
    }
}
