package com.github.haozi.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Menu entity.
 */
public class MenuDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Integer seq;

    private String state;

    private String url;

    private Boolean leaf;

    private Boolean showFlag;

    private String extmap;


    private Long parentId;

    private Set<RoleDTO> roles = new HashSet<>();

    private Set<TemplateDTO> templates = new HashSet<>();

    private Set<AuthDTO> auths = new HashSet<>();

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean isShowFlag() {
        return showFlag;
    }

    public void setShowFlag(Boolean showFlag) {
        this.showFlag = showFlag;
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

    public void setParentId(Long menuId) {
        this.parentId = menuId;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public Set<TemplateDTO> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<TemplateDTO> templates) {
        this.templates = templates;
    }

    public Set<AuthDTO> getAuths() {
        return auths;
    }

    public void setAuths(Set<AuthDTO> auths) {
        this.auths = auths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuDTO menuDTO = (MenuDTO) o;
        if (menuDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menuDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", seq=" + getSeq() +
            ", state='" + getState() + "'" +
            ", url='" + getUrl() + "'" +
            ", leaf='" + isLeaf() + "'" +
            ", showFlag='" + isShowFlag() + "'" +
            ", extmap='" + getExtmap() + "'" +
            ", parent=" + getParentId() +
            "}";
    }
}
