package com.github.haozi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "seq")
    private Integer seq;

    @Column(name = "state")
    private String state;

    @Column(name = "url")
    private String url;

    @Column(name = "leaf")
    private Boolean leaf;

    @Column(name = "show_flag")
    private Boolean showFlag;

    @Column(name = "extmap")
    private String extmap;

    @ManyToOne
    @JsonIgnoreProperties("menus")
    private Menu parent;

    @ManyToMany
    @JoinTable(name = "menu_role",
               joinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "menu_template",
               joinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "template_id", referencedColumnName = "id"))
    private Set<Template> templates = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "menu_auth",
               joinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "auth_id", referencedColumnName = "id"))
    private Set<Auth> auths = new HashSet<>();

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

    public Menu name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Menu code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSeq() {
        return seq;
    }

    public Menu seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getState() {
        return state;
    }

    public Menu state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public Menu url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isLeaf() {
        return leaf;
    }

    public Menu leaf(Boolean leaf) {
        this.leaf = leaf;
        return this;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean isShowFlag() {
        return showFlag;
    }

    public Menu showFlag(Boolean showFlag) {
        this.showFlag = showFlag;
        return this;
    }

    public void setShowFlag(Boolean showFlag) {
        this.showFlag = showFlag;
    }

    public String getExtmap() {
        return extmap;
    }

    public Menu extmap(String extmap) {
        this.extmap = extmap;
        return this;
    }

    public void setExtmap(String extmap) {
        this.extmap = extmap;
    }

    public Menu getParent() {
        return parent;
    }

    public Menu parent(Menu menu) {
        this.parent = menu;
        return this;
    }

    public void setParent(Menu menu) {
        this.parent = menu;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Menu roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Menu addRole(Role role) {
        this.roles.add(role);
        role.getMenus().add(this);
        return this;
    }

    public Menu removeRole(Role role) {
        this.roles.remove(role);
        role.getMenus().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public Menu templates(Set<Template> templates) {
        this.templates = templates;
        return this;
    }

    public Menu addTemplate(Template template) {
        this.templates.add(template);
        template.getMenus().add(this);
        return this;
    }

    public Menu removeTemplate(Template template) {
        this.templates.remove(template);
        template.getMenus().remove(this);
        return this;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    public Set<Auth> getAuths() {
        return auths;
    }

    public Menu auths(Set<Auth> auths) {
        this.auths = auths;
        return this;
    }

    public Menu addAuth(Auth auth) {
        this.auths.add(auth);
        auth.getMenus().add(this);
        return this;
    }

    public Menu removeAuth(Auth auth) {
        this.auths.remove(auth);
        auth.getMenus().remove(this);
        return this;
    }

    public void setAuths(Set<Auth> auths) {
        this.auths = auths;
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
        Menu menu = (Menu) o;
        if (menu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", seq=" + getSeq() +
            ", state='" + getState() + "'" +
            ", url='" + getUrl() + "'" +
            ", leaf='" + isLeaf() + "'" +
            ", showFlag='" + isShowFlag() + "'" +
            ", extmap='" + getExtmap() + "'" +
            "}";
    }
}
