package com.haozi.trymybatis;

import java.util.Date;

/**
 * 第三方授权绑定表
 * @author wanghao
 * @Description
 * @date 2018-05-24 15:51
 */
public class ThirdpartyOauthDO {
    //自增主键
    private Integer id;
    //应用id
    private String appId;
    //用户id
    private String userId;
    //第三方账户id(微信openId)
    private String thirdpartyId;
    //绑定类型(1-微信)
    private Integer bindType;
    //第三方元数据
    private String metaData;
    //备注
    private String remark;
    //绑定状态(0-未绑定 1-已绑定 2-已解绑)
    private Integer bindStatus;
    //绑定时间
    private Date bindTime;
    //更新时间
    private Date updateTime;

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }
    public String getAppId() {
        return appId;
    }
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
    public String getUserId() {
        return userId;
    }
    public void setThirdpartyId(String thirdpartyId) {
        this.thirdpartyId = thirdpartyId == null ? null : thirdpartyId.trim();
    }
    public String getThirdpartyId() {
        return thirdpartyId;
    }
    public void setBindType(Integer bindType) {
        this.bindType = bindType;
    }
    public Integer getBindType() {
        return bindType;
    }
    public void setMetaData(String metaData) {
        this.metaData = metaData == null ? null : metaData.trim();
    }
    public String getMetaData() {
        return metaData;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    public String getRemark() {
        return remark;
    }
    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }
    public Integer getBindStatus() {
        return bindStatus;
    }
    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }
    public Date getBindTime() {
        return bindTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ThirdpartyOauthDO{");
        sb.append("id=").append(id);
        sb.append(", appId='").append(appId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", thirdpartyId='").append(thirdpartyId).append('\'');
        sb.append(", bindType=").append(bindType);
        sb.append(", metaData='").append(metaData).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", bindStatus=").append(bindStatus);
        sb.append(", bindTime=").append(bindTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
