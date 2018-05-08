package com.fast.spider.icama;

import lombok.Data;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-08 13:48
 */
@Data
public class PresticideCert {
    private String id;
    private String creditCode;
    private String attention;
    private String productPerformance;
    private String emergency;
    private String storageTransportation;
    private String useMethod;
    private String instructions;

    /**
     * 为了方便导出mysql脚本，重写了toString方法
     * @return
     */
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(");
        sb.append("'").append(id).append('\'');
        sb.append(",'").append(creditCode).append('\'');
        sb.append(",'").append(attention).append('\'');
        sb.append(",'").append(productPerformance).append('\'');
        sb.append(",'").append(emergency).append('\'');
        sb.append(",'").append(storageTransportation).append('\'');
        sb.append(",'").append(useMethod).append('\'');
        sb.append(",'").append(instructions).append('\'');
        sb.append("),");
        return sb.toString();
    }
}
