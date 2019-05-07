package com.github.haozi.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.github.haozi.domain.enumeration.ClientType;
import com.github.haozi.domain.enumeration.RoleType;

/**
 * A DTO for the Role entity.
 */
public class RoleDTO implements Serializable {

    private Long id;

    private Integer spaceId;

    private Integer siteId;

    private ClientType clientType;

    private String name;

    private String remark;

    private String extmap;

    private RoleType roleType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtmap() {
        return extmap;
    }

    public void setExtmap(String extmap) {
        this.extmap = extmap;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoleDTO roleDTO = (RoleDTO) o;
        if (roleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
            "id=" + getId() +
            ", spaceId=" + getSpaceId() +
            ", siteId=" + getSiteId() +
            ", clientType='" + getClientType() + "'" +
            ", name='" + getName() + "'" +
            ", remark='" + getRemark() + "'" +
            ", extmap='" + getExtmap() + "'" +
            ", roleType='" + getRoleType() + "'" +
            "}";
    }
}
