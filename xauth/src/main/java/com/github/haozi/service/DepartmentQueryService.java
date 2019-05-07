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

import com.github.haozi.domain.Department;
import com.github.haozi.domain.*; // for static metamodels
import com.github.haozi.repository.DepartmentRepository;
import com.github.haozi.service.dto.DepartmentCriteria;
import com.github.haozi.service.dto.DepartmentDTO;
import com.github.haozi.service.mapper.DepartmentMapper;

/**
 * Service for executing complex queries for Department entities in the database.
 * The main input is a {@link DepartmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DepartmentDTO} or a {@link Page} of {@link DepartmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepartmentQueryService extends QueryService<Department> {

    private final Logger log = LoggerFactory.getLogger(DepartmentQueryService.class);

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public DepartmentQueryService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Return a {@link List} of {@link DepartmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findByCriteria(DepartmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentMapper.toDto(departmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DepartmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartmentDTO> findByCriteria(DepartmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentRepository.findAll(specification, page)
            .map(departmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepartmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentRepository.count(specification);
    }

    /**
     * Function to convert DepartmentCriteria to a {@link Specification}
     */
    private Specification<Department> createSpecification(DepartmentCriteria criteria) {
        Specification<Department> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Department_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Department_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Department_.code));
            }
            if (criteria.getSeq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeq(), Department_.seq));
            }
            if (criteria.getLeaf() != null) {
                specification = specification.and(buildSpecification(criteria.getLeaf(), Department_.leaf));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), Department_.remark));
            }
            if (criteria.getExtMap() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtMap(), Department_.extMap));
            }
            if (criteria.getWorkspaceId() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkspaceId(),
                    root -> root.join(Department_.workspace, JoinType.LEFT).get(Workspace_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(Department_.parent, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfileId(),
                    root -> root.join(Department_.profiles, JoinType.LEFT).get(Profile_.id)));
            }
        }
        return specification;
    }
}
