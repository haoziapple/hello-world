package com.github.haozi.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.github.haozi.domain.enumeration.ClientType;

/**
 * A Template.
 */
@Entity
@Table(name = "template")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "space_id")
    private Integer spaceId;

    @Column(name = "site_id")
    private Integer siteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_type")
    private ClientType clientType;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_desc")
    private String desc;

    @Column(name = "extmap")
    private String extmap;

    @ManyToMany(mappedBy = "templates")
    @JsonIgnore
    private Set<Auth> auths = new HashSet<>();

    @ManyToMany(mappedBy = "templates")
    @JsonIgnore
    private Set<Menu> menus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public Template spaceId(Integer spaceId) {
        this.spaceId = spaceId;
        return this;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Template siteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public Template clientType(ClientType clientType) {
        this.clientType = clientType;
        return this;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getName() {
        return name;
    }

    public Template name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public Template desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExtmap() {
        return extmap;
    }

    public Template extmap(String extmap) {
        this.extmap = extmap;
        return this;
    }

    public void setExtmap(String extmap) {
        this.extmap = extmap;
    }

    public Set<Auth> getAuths() {
        return auths;
    }

    public Template auths(Set<Auth> auths) {
        this.auths = auths;
        return this;
    }

    public Template addAuth(Auth auth) {
        this.auths.add(auth);
        auth.getTemplates().add(this);
        return this;
    }

    public Template removeAuth(Auth auth) {
        this.auths.remove(auth);
        auth.getTemplates().remove(this);
        return this;
    }

    public void setAuths(Set<Auth> auths) {
        this.auths = auths;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Template menus(Set<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public Template addMenu(Menu menu) {
        this.menus.add(menu);
        menu.getTemplates().add(this);
        return this;
    }

    public Template removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.getTemplates().remove(this);
        return this;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Template template = (Template) o;
        if (template.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), template.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Template{" +
            "id=" + getId() +
            ", spaceId=" + getSpaceId() +
            ", siteId=" + getSiteId() +
            ", clientType='" + getClientType() + "'" +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", extmap='" + getExtmap() + "'" +
            "}";
    }
}
