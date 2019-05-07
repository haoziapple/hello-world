package com.github.haozi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Department entity. This class is used in DepartmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /departments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepartmentCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private IntegerFilter seq;

    private BooleanFilter leaf;

    private StringFilter remark;

    private StringFilter extMap;

    private LongFilter workspaceId;

    private LongFilter parentId;

    private LongFilter profileId;

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

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public IntegerFilter getSeq() {
        return seq;
    }

    public void setSeq(IntegerFilter seq) {
        this.seq = seq;
    }

    public BooleanFilter getLeaf() {
        return leaf;
    }

    public void setLeaf(BooleanFilter leaf) {
        this.leaf = leaf;
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

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getProfileId() {
        return profileId;
    }

    public void setProfileId(LongFilter profileId) {
        this.profileId = profileId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DepartmentCriteria that = (DepartmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(seq, that.seq) &&
            Objects.equals(leaf, that.leaf) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(extMap, that.extMap) &&
            Objects.equals(workspaceId, that.workspaceId) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(profileId, that.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        seq,
        leaf,
        remark,
        extMap,
        workspaceId,
        parentId,
        profileId
        );
    }

    @Override
    public String toString() {
        return "DepartmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (seq != null ? "seq=" + seq + ", " : "") +
                (leaf != null ? "leaf=" + leaf + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (extMap != null ? "extMap=" + extMap + ", " : "") +
                (workspaceId != null ? "workspaceId=" + workspaceId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (profileId != null ? "profileId=" + profileId + ", " : "") +
            "}";
    }

}
