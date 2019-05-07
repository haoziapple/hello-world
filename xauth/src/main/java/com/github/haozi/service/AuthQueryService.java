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

import com.github.haozi.domain.Auth;
import com.github.haozi.domain.*; // for static metamodels
import com.github.haozi.repository.AuthRepository;
import com.github.haozi.service.dto.AuthCriteria;
import com.github.haozi.service.dto.AuthDTO;
import com.github.haozi.service.mapper.AuthMapper;

/**
 * Service for executing complex queries for Auth entities in the database.
 * The main input is a {@link AuthCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AuthDTO} or a {@link Page} of {@link AuthDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AuthQueryService extends QueryService<Auth> {

    private final Logger log = LoggerFactory.getLogger(AuthQueryService.class);

    private final AuthRepository authRepository;

    private final AuthMapper authMapper;

    public AuthQueryService(AuthRepository authRepository, AuthMapper authMapper) {
        this.authRepository = authRepository;
        this.authMapper = authMapper;
    }

    /**
     * Return a {@link List} of {@link AuthDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuthDTO> findByCriteria(AuthCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Auth> specification = createSpecification(criteria);
        return authMapper.toDto(authRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AuthDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthDTO> findByCriteria(AuthCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Auth> specification = createSpecification(criteria);
        return authRepository.findAll(specification, page)
            .map(authMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuthCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Auth> specification = createSpecification(criteria);
        return authRepository.count(specification);
    }

    /**
     * Function to convert AuthCriteria to a {@link Specification}
     */
    private Specification<Auth> createSpecification(AuthCriteria criteria) {
        Specification<Auth> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Auth_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Auth_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Auth_.code));
            }
            if (criteria.getDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesc(), Auth_.desc));
            }
            if (criteria.getExtmap() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtmap(), Auth_.extmap));
            }
            if (criteria.getRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoleId(),
                    root -> root.join(Auth_.roles, JoinType.LEFT).get(Role_.id)));
            }
            if (criteria.getTemplateId() != null) {
                specification = specification.and(buildSpecification(criteria.getTemplateId(),
                    root -> root.join(Auth_.templates, JoinType.LEFT).get(Template_.id)));
            }
            if (criteria.getMenuId() != null) {
                specification = specification.and(buildSpecification(criteria.getMenuId(),
                    root -> root.join(Auth_.menus, JoinType.LEFT).get(Menu_.id)));
            }
        }
        return specification;
    }
}
