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
 * Criteria class for the Menu entity. This class is used in MenuResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /menus?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MenuCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private IntegerFilter seq;

    private StringFilter state;

    private StringFilter url;

    private BooleanFilter leaf;

    private BooleanFilter showFlag;

    private StringFilter extmap;

    private LongFilter parentId;

    private LongFilter roleId;

    private LongFilter templateId;

    private LongFilter authId;

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

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public BooleanFilter getLeaf() {
        return leaf;
    }

    public void setLeaf(BooleanFilter leaf) {
        this.leaf = leaf;
    }

    public BooleanFilter getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(BooleanFilter showFlag) {
        this.showFlag = showFlag;
    }

    public StringFilter getExtmap() {
        return extmap;
    }

    public void setExtmap(StringFilter extmap) {
        this.extmap = extmap;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getRoleId() {
        return roleId;
    }

    public void setRoleId(LongFilter roleId) {
        this.roleId = roleId;
    }

    public LongFilter getTemplateId() {
        return templateId;
    }

    public void setTemplateId(LongFilter templateId) {
        this.templateId = templateId;
    }

    public LongFilter getAuthId() {
        return authId;
    }

    public void setAuthId(LongFilter authId) {
        this.authId = authId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MenuCriteria that = (MenuCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(seq, that.seq) &&
            Objects.equals(state, that.state) &&
            Objects.equals(url, that.url) &&
            Objects.equals(leaf, that.leaf) &&
            Objects.equals(showFlag, that.showFlag) &&
            Objects.equals(extmap, that.extmap) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(roleId, that.roleId) &&
            Objects.equals(templateId, that.templateId) &&
            Objects.equals(authId, that.authId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        seq,
        state,
        url,
        leaf,
        showFlag,
        extmap,
        parentId,
        roleId,
        templateId,
        authId
        );
    }

    @Override
    public String toString() {
        return "MenuCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (seq != null ? "seq=" + seq + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (leaf != null ? "leaf=" + leaf + ", " : "") +
                (showFlag != null ? "showFlag=" + showFlag + ", " : "") +
                (extmap != null ? "extmap=" + extmap + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (roleId != null ? "roleId=" + roleId + ", " : "") +
                (templateId != null ? "templateId=" + templateId + ", " : "") +
                (authId != null ? "authId=" + authId + ", " : "") +
            "}";
    }

}
