package com.github.haozi.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.github.haozi.domain.enumeration.ClientType;

/**
 * A DTO for the Template entity.
 */
public class TemplateDTO implements Serializable {

    private Long id;

    private Integer spaceId;

    private Integer siteId;

    private ClientType clientType;

    private String name;

    private String desc;

    private String extmap;


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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExtmap() {
        return extmap;
    }

    public void setExtmap(String extmap) {
        this.extmap = extmap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TemplateDTO templateDTO = (TemplateDTO) o;
        if (templateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), templateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TemplateDTO{" +
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
