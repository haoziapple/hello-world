package com.github.haozi.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Workspace entity.
 */
public class WorkspaceDTO implements Serializable {

    private Long id;

    private String name;

    private String remark;

    private String extmap;


    private Long parentId;

    private Set<SiteDTO> sites = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long workspaceId) {
        this.parentId = workspaceId;
    }

    public Set<SiteDTO> getSites() {
        return sites;
    }

    public void setSites(Set<SiteDTO> sites) {
        this.sites = sites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkspaceDTO workspaceDTO = (WorkspaceDTO) o;
        if (workspaceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workspaceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkspaceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", remark='" + getRemark() + "'" +
            ", extmap='" + getExtmap() + "'" +
            ", parent=" + getParentId() +
            "}";
    }
}
