package com.github.haozi.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.github.haozi.domain.Workspace;
import com.github.haozi.domain.*; // for static metamodels
import com.github.haozi.repository.WorkspaceRepository;
import com.github.haozi.service.dto.WorkspaceCriteria;
import com.github.haozi.service.dto.WorkspaceDTO;
import com.github.haozi.service.mapper.WorkspaceMapper;

/**
 * Service for executing complex queries for Workspace entities in the database.
 * The main input is a {@link WorkspaceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkspaceDTO} or a {@link Page} of {@link WorkspaceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkspaceQueryService extends QueryService<Workspace> {

    private final Logger log = LoggerFactory.getLogger(WorkspaceQueryService.class);

    private final WorkspaceRepository workspaceRepository;

    private final WorkspaceMapper workspaceMapper;

    public WorkspaceQueryService(WorkspaceRepository workspaceRepository, WorkspaceMapper workspaceMapper) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
    }

    /**
     * Return a {@link List} of {@link WorkspaceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkspaceDTO> findByCriteria(WorkspaceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Workspace> specification = createSpecification(criteria);
        return workspaceMapper.toDto(workspaceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WorkspaceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkspaceDTO> findByCriteria(WorkspaceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Workspace> specification = createSpecification(criteria);
        return workspaceRepository.findAll(specification, page)
            .map(workspaceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkspaceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Workspace> specification = createSpecification(criteria);
        return workspaceRepository.count(specification);
    }

    /**
     * Function to convert WorkspaceCriteria to a {@link Specification}
     */
    private Specification<Workspace> createSpecification(WorkspaceCriteria criteria) {
        Specification<Workspace> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Workspace_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Workspace_.name));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), Workspace_.remark));
            }
            if (criteria.getExtmap() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtmap(), Workspace_.extmap));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(Workspace_.parent, JoinType.LEFT).get(Workspace_.id)));
            }
            if (criteria.getSiteId() != null) {
                specification = specification.and(buildSpecification(criteria.getSiteId(),
                    root -> root.join(Workspace_.sites, JoinType.LEFT).get(Site_.id)));
            }
        }
        return specification;
    }
}
