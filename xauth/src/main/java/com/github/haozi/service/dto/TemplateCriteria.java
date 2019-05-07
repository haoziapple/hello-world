package com.github.haozi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.github.haozi.domain.enumeration.ClientType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Template entity. This class is used in TemplateResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /templates?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TemplateCriteria implements Serializable {
    /**
     * Class for filtering ClientType
     */
    public static class ClientTypeFilter extends Filter<ClientType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter spaceId;

    private IntegerFilter siteId;

    private ClientTypeFilter clientType;

    private StringFilter name;

    private StringFilter desc;

    private StringFilter extmap;

    private LongFilter authId;

    private LongFilter menuId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(IntegerFilter spaceId) {
        this.spaceId = spaceId;
    }

    public IntegerFilter getSiteId() {
        return siteId;
    }

    public void setSiteId(IntegerFilter siteId) {
        this.siteId = siteId;
    }

    public ClientTypeFilter getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeFilter clientType) {
        this.clientType = clientType;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public LongFilter getAuthId() {
        return authId;
    }

    public void setAuthId(LongFilter authId) {
        this.authId = authId;
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
        final TemplateCriteria that = (TemplateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(spaceId, that.spaceId) &&
            Objects.equals(siteId, that.siteId) &&
            Objects.equals(clientType, that.clientType) &&
            Objects.equals(name, that.name) &&
            Objects.equals(desc, that.desc) &&
            Objects.equals(extmap, that.extmap) &&
            Objects.equals(authId, that.authId) &&
            Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        spaceId,
        siteId,
        clientType,
        name,
        desc,
        extmap,
        authId,
        menuId
        );
    }

    @Override
    public String toString() {
        return "TemplateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (spaceId != null ? "spaceId=" + spaceId + ", " : "") +
                (siteId != null ? "siteId=" + siteId + ", " : "") +
                (clientType != null ? "clientType=" + clientType + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (desc != null ? "desc=" + desc + ", " : "") +
                (extmap != null ? "extmap=" + extmap + ", " : "") +
                (authId != null ? "authId=" + authId + ", " : "") +
                (menuId != null ? "menuId=" + menuId + ", " : "") +
            "}";
    }

}
