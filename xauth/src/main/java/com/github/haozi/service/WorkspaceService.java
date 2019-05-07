package com.github.haozi.service;

import com.github.haozi.domain.Workspace;
import com.github.haozi.repository.WorkspaceRepository;
import com.github.haozi.service.dto.WorkspaceDTO;
import com.github.haozi.service.mapper.WorkspaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Workspace.
 */
@Service
@Transactional
public class WorkspaceService {

    private final Logger log = LoggerFactory.getLogger(WorkspaceService.class);

    private final WorkspaceRepository workspaceRepository;

    private final WorkspaceMapper workspaceMapper;

    public WorkspaceService(WorkspaceRepository workspaceRepository, WorkspaceMapper workspaceMapper) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
    }

    /**
     * Save a workspace.
     *
     * @param workspaceDTO the entity to save
     * @return the persisted entity
     */
    public WorkspaceDTO save(WorkspaceDTO workspaceDTO) {
        log.debug("Request to save Workspace : {}", workspaceDTO);
        Workspace workspace = workspaceMapper.toEntity(workspaceDTO);
        workspace = workspaceRepository.save(workspace);
        return workspaceMapper.toDto(workspace);
    }

    /**
     * Get all the workspaces.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WorkspaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Workspaces");
        return workspaceRepository.findAll(pageable)
            .map(workspaceMapper::toDto);
    }

    /**
     * Get all the Workspace with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<WorkspaceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return workspaceRepository.findAllWithEagerRelationships(pageable).map(workspaceMapper::toDto);
    }
    

    /**
     * Get one workspace by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<WorkspaceDTO> findOne(Long id) {
        log.debug("Request to get Workspace : {}", id);
        return workspaceRepository.findOneWithEagerRelationships(id)
            .map(workspaceMapper::toDto);
    }

    /**
     * Delete the workspace by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Workspace : {}", id);
        workspaceRepository.deleteById(id);
    }
}
