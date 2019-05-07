package com.github.haozi.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Auth.
 */
@Entity
@Table(name = "auth")
public class Auth implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "jhi_desc")
    private String desc;

    @Column(name = "extmap")
    private String extmap;

    @ManyToMany
    @JoinTable(name = "auth_role",
               joinColumns = @JoinColumn(name = "auth_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "auth_template",
               joinColumns = @JoinColumn(name = "auth_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "template_id", referencedColumnName = "id"))
    private Set<Template> templates = new HashSet<>();

    @ManyToMany(mappedBy = "auths")
    @JsonIgnore
    private Set<Menu> menus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Auth name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Auth code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Auth desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExtmap() {
        return extmap;
    }

    public Auth extmap(String extmap) {
        this.extmap = extmap;
        return this;
    }

    public void setExtmap(String extmap) {
        this.extmap = extmap;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Auth roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Auth addRole(Role role) {
        this.roles.add(role);
        role.getAuths().add(this);
        return this;
    }

    public Auth removeRole(Role role) {
        this.roles.remove(role);
        role.getAuths().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public Auth templates(Set<Template> templates) {
        this.templates = templates;
        return this;
    }

    public Auth addTemplate(Template template) {
        this.templates.add(template);
        template.getAuths().add(this);
        return this;
    }

    public Auth removeTemplate(Template template) {
        this.templates.remove(template);
        template.getAuths().remove(this);
        return this;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Auth menus(Set<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public Auth addMenu(Menu menu) {
        this.menus.add(menu);
        menu.getAuths().add(this);
        return this;
    }

    public Auth removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.getAuths().remove(this);
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
        Auth auth = (Auth) o;
        if (auth.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auth.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Auth{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", desc='" + getDesc() + "'" +
            ", extmap='" + getExtmap() + "'" +
            "}";
    }
}
