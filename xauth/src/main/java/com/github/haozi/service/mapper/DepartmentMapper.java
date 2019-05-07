package com.github.haozi.service.mapper;

import com.github.haozi.domain.*;
import com.github.haozi.service.dto.DepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Department and its DTO DepartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkspaceMapper.class})
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {

    @Mapping(source = "workspace.id", target = "workspaceId")
    @Mapping(source = "parent.id", target = "parentId")
    DepartmentDTO toDto(Department department);

    @Mapping(source = "workspaceId", target = "workspace")
    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "profiles", ignore = true)
    Department toEntity(DepartmentDTO departmentDTO);

    default Department fromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}
