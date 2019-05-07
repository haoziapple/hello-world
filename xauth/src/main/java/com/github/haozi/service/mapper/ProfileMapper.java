package com.github.haozi.service.mapper;

import com.github.haozi.domain.*;
import com.github.haozi.service.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Profile and its DTO ProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkspaceMapper.class, DepartmentMapper.class, RoleMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

    @Mapping(source = "workspace.id", target = "workspaceId")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "workspaceId", target = "workspace")
    Profile toEntity(ProfileDTO profileDTO);

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}
