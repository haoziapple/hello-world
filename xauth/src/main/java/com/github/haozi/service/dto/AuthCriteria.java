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
 * Criteria class for the Auth entity. This class is used in AuthResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /auths?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AuthCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter desc;

    private StringFilter extmap;

    private LongFilter roleId;

    private LongFilter templateId;

    private LongFilter menuId;

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

    public StringFilter getDesc() {
        return desc;
    }

    public void setDesc(StringFilter desc) {
        this.desc = desc;
    }

    public StringFilter getExtmap() {
        return extmap;
    }

    public void setExtmap(StringFilter extmap) {
        this.extmap = extmap;
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

    public LongFilter getMenuId() {
        return menuId;
    }

    public void setMenuId(LongFilter menuId) {
        this.menuId = menuId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AuthCriteria that = (AuthCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(desc, that.desc) &&
            Objects.equals(extmap, that.extmap) &&
            Objects.equals(roleId, that.roleId) &&
            Objects.equals(templateId, that.templateId) &&
            Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        desc,
        extmap,
        roleId,
        templateId,
        menuId
        );
    }

    @Override
    public String toString() {
        return "AuthCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (desc != null ? "desc=" + desc + ", " : "") +
                (extmap != null ? "extmap=" + extmap + ", " : "") +
                (roleId != null ? "roleId=" + roleId + ", " : "") +
                (templateId != null ? "templateId=" + templateId + ", " : "") +
                (menuId != null ? "menuId=" + menuId + ", " : "") +
            "}";
    }

}
