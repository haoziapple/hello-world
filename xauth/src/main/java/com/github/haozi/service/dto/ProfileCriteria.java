package com.github.haozi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.github.haozi.domain.enumeration.Sex;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Profile entity. This class is used in ProfileResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /profiles?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProfileCriteria implements Serializable {
    /**
     * Class for filtering Sex
     */
    public static class SexFilter extends Filter<Sex> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter loginName;

    private StringFilter password;

    private StringFilter mobile;

    private StringFilter email;

    private SexFilter sex;

    private BooleanFilter locked;

    private StringFilter remark;

    private StringFilter extMap;

    private LongFilter workspaceId;

    private LongFilter departmentId;

    private LongFilter roleId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getLoginName() {
        return loginName;
    }

    public void setLoginName(StringFilter loginName) {
        this.loginName = loginName;
    }

    public StringFilter getPassword() {
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public SexFilter getSex() {
        return sex;
    }

    public void setSex(SexFilter sex) {
        this.sex = sex;
    }

    public BooleanFilter getLocked() {
        return locked;
    }

    public void setLocked(BooleanFilter locked) {
        this.locked = locked;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public StringFilter getExtMap() {
        return extMap;
    }

    public void setExtMap(StringFilter extMap) {
        this.extMap = extMap;
    }

    public LongFilter getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(LongFilter workspaceId) {
        this.workspaceId = workspaceId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getRoleId() {
        return roleId;
    }

    public void setRoleId(LongFilter roleId) {
        this.roleId = roleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProfileCriteria that = (ProfileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(loginName, that.loginName) &&
            Objects.equals(password, that.password) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(email, that.email) &&
            Objects.equals(sex, that.sex) &&
            Objects.equals(locked, that.locked) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(extMap, that.extMap) &&
            Objects.equals(workspaceId, that.workspaceId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        loginName,
        password,
        mobile,
        email,
        sex,
        locked,
        remark,
        extMap,
        workspaceId,
        departmentId,
        roleId
        );
    }

    @Override
    public String toString() {
        return "ProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (loginName != null ? "loginName=" + loginName + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (sex != null ? "sex=" + sex + ", " : "") +
                (locked != null ? "locked=" + locked + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (extMap != null ? "extMap=" + extMap + ", " : "") +
                (workspaceId != null ? "workspaceId=" + workspaceId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
                (roleId != null ? "roleId=" + roleId + ", " : "") +
            "}";
    }

}
