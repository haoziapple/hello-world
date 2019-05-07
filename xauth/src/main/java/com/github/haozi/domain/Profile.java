package com.github.haozi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.github.haozi.domain.enumeration.Sex;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "jhi_locked")
    private Boolean locked;

    @Column(name = "remark")
    private String remark;

    @Column(name = "ext_map")
    private String extMap;

    @ManyToOne
    @JsonIgnoreProperties("profiles")
    private Workspace workspace;

    @ManyToMany
    @JoinTable(name = "profile_department",
               joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "department_id", referencedColumnName = "id"))
    private Set<Department> departments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "profile_role",
               joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

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

    public Profile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public Profile loginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public Profile password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public Profile mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public Profile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sex getSex() {
        return sex;
    }

    public Profile sex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Boolean isLocked() {
        return locked;
    }

    public Profile locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getRemark() {
        return remark;
    }

    public Profile remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtMap() {
        return extMap;
    }

    public Profile extMap(String extMap) {
        this.extMap = extMap;
        return this;
    }

    public void setExtMap(String extMap) {
        this.extMap = extMap;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public Profile workspace(Workspace workspace) {
        this.workspace = workspace;
        return this;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public Profile departments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    public Profile addDepartment(Department department) {
        this.departments.add(department);
        department.getProfiles().add(this);
        return this;
    }

    public Profile removeDepartment(Department department) {
        this.departments.remove(department);
        department.getProfiles().remove(this);
        return this;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Profile roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Profile addRole(Role role) {
        this.roles.add(role);
        role.getProfiles().add(this);
        return this;
    }

    public Profile removeRole(Role role) {
        this.roles.remove(role);
        role.getProfiles().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
        Profile profile = (Profile) o;
        if (profile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", loginName='" + getLoginName() + "'" +
            ", password='" + getPassword() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", sex='" + getSex() + "'" +
            ", locked='" + isLocked() + "'" +
            ", remark='" + getRemark() + "'" +
            ", extMap='" + getExtMap() + "'" +
            "}";
    }
}
