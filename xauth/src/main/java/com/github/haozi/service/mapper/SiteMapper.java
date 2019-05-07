package com.github.haozi.service.mapper;

import com.github.haozi.domain.*;
import com.github.haozi.service.dto.SiteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Site and its DTO SiteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SiteMapper extends EntityMapper<SiteDTO, Site> {


    @Mapping(target = "workspaces", ignore = true)
    Site toEntity(SiteDTO siteDTO);

    default Site fromId(Long id) {
        if (id == null) {
            return null;
        }
        Site site = new Site();
        site.setId(id);
        return site;
    }
}
