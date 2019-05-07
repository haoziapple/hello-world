package com.github.haozi.service.mapper;

import com.github.haozi.domain.*;
import com.github.haozi.service.dto.RoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Role and its DTO RoleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {


    @Mapping(target = "auths", ignore = true)
    @Mapping(target = "menus", ignore = true)
    @Mapping(target = "profiles", ignore = true)
    Role toEntity(RoleDTO roleDTO);

    default Role fromId(Long id) {
        if (id == null) {
            return null;
        }
        Role role = new Role();
        role.setId(id);
        return role;
    }
}
