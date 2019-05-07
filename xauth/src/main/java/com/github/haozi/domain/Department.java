package com.github.haozi.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Department.
 */
@Entity
@Table(name = "department")
public class Department implements Serializable {

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

    @Column(name = "leaf")
    private Boolean leaf;

    @Column(name = "remark")
    private String remark;

    @Column(name = "ext_map")
    private String extMap;

    @ManyToOne
    @JsonIgnoreProperties("departments")
    private Workspace workspace;

    @ManyToOne
    @JsonIgnoreProperties("departments")
    private Department parent;

    @ManyToMany(mappedBy = "departments")
    @JsonIgnore
    private Set<Profile> profiles = new HashSet<>();

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

    public Department name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Department code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSeq() {
        return seq;
    }

    public Department seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Boolean isLeaf() {
        return leaf;
    }

    public Department leaf(Boolean leaf) {
        this.leaf = leaf;
        return this;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getRemark() {
        return remark;
    }

    public Department remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtMap() {
        return extMap;
    }

    public Department extMap(String extMap) {
        this.extMap = extMap;
        return this;
    }

    public void setExtMap(String extMap) {
        this.extMap = extMap;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public Department workspace(Workspace workspace) {
        this.workspace = workspace;
        return this;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public Department getParent() {
        return parent;
    }

    public Department parent(Department department) {
        this.parent = department;
        return this;
    }

    public void setParent(Department department) {
        this.parent = department;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public Department profiles(Set<Profile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public Department addProfile(Profile profile) {
        this.profiles.add(profile);
        profile.getDepartments().add(this);
        return this;
    }

    public Department removeProfile(Profile profile) {
        this.profiles.remove(profile);
        profile.getDepartments().remove(this);
        return this;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
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
        Department department = (Department) o;
        if (department.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), department.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Department{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", seq=" + getSeq() +
            ", leaf='" + isLeaf() + "'" +
            ", remark='" + getRemark() + "'" +
            ", extMap='" + getExtMap() + "'" +
            "}";
    }
}
