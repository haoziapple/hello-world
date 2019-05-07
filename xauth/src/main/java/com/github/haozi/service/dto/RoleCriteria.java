package com.github.haozi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.github.haozi.domain.enumeration.ClientType;
import com.github.haozi.domain.enumeration.RoleType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Role entity. This class is used in RoleResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /roles?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoleCriteria implements Serializable {
    /**
     * Class for filtering ClientType
     */
    public static class ClientTypeFilter extends Filter<ClientType> {
    }
    /**
     * Class for filtering RoleType
     */
    public static class RoleTypeFilter extends Filter<RoleType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter spaceId;

    private IntegerFilter siteId;

    private ClientTypeFilter clientType;

    private StringFilter name;

    private StringFilter remark;

    private StringFilter extmap;

    private RoleTypeFilter roleType;

    private LongFilter authId;

    private LongFilter menuId;

    private LongFilter profileId;

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

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public StringFilter getExtmap() {
        return extmap;
    }

    public void setExtmap(StringFilter extmap) {
        this.extmap = extmap;
    }

    public RoleTypeFilter getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleTypeFilter roleType) {
        this.roleType = roleType;
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
        final RoleCriteria that = (RoleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(spaceId, that.spaceId) &&
            Objects.equals(siteId, that.siteId) &&
            Objects.equals(clientType, that.clientType) &&
            Objects.equals(name, that.name) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(extmap, that.extmap) &&
            Objects.equals(roleType, that.roleType) &&
            Objects.equals(authId, that.authId) &&
            Objects.equals(menuId, that.menuId) &&
            Objects.equals(profileId, that.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        spaceId,
        siteId,
        clientType,
        name,
        remark,
        extmap,
        roleType,
        authId,
        menuId,
        profileId
        );
    }

    @Override
    public String toString() {
        return "RoleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (spaceId != null ? "spaceId=" + spaceId + ", " : "") +
                (siteId != null ? "siteId=" + siteId + ", " : "") +
                (clientType != null ? "clientType=" + clientType + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (extmap != null ? "extmap=" + extmap + ", " : "") +
                (roleType != null ? "roleType=" + roleType + ", " : "") +
                (authId != null ? "authId=" + authId + ", " : "") +
                (menuId != null ? "menuId=" + menuId + ", " : "") +
                (profileId != null ? "profileId=" + profileId + ", " : "") +
            "}";
    }

}
