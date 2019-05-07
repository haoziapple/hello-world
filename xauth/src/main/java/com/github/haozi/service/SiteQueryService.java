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

import com.github.haozi.domain.Site;
import com.github.haozi.domain.*; // for static metamodels
import com.github.haozi.repository.SiteRepository;
import com.github.haozi.service.dto.SiteCriteria;
import com.github.haozi.service.dto.SiteDTO;
import com.github.haozi.service.mapper.SiteMapper;

/**
 * Service for executing complex queries for Site entities in the database.
 * The main input is a {@link SiteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SiteDTO} or a {@link Page} of {@link SiteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SiteQueryService extends QueryService<Site> {

    private final Logger log = LoggerFactory.getLogger(SiteQueryService.class);

    private final SiteRepository siteRepository;

    private final SiteMapper siteMapper;

    public SiteQueryService(SiteRepository siteRepository, SiteMapper siteMapper) {
        this.siteRepository = siteRepository;
        this.siteMapper = siteMapper;
    }

    /**
     * Return a {@link List} of {@link SiteDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SiteDTO> findByCriteria(SiteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Site> specification = createSpecification(criteria);
        return siteMapper.toDto(siteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SiteDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteDTO> findByCriteria(SiteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Site> specification = createSpecification(criteria);
        return siteRepository.findAll(specification, page)
            .map(siteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SiteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Site> specification = createSpecification(criteria);
        return siteRepository.count(specification);
    }

    /**
     * Function to convert SiteCriteria to a {@link Specification}
     */
    private Specification<Site> createSpecification(SiteCriteria criteria) {
        Specification<Site> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Site_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Site_.name));
            }
            if (criteria.getHomeUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHomeUrl(), Site_.homeUrl));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), Site_.remark));
            }
            if (criteria.getExtmap() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtmap(), Site_.extmap));
            }
            if (criteria.getWorkspaceId() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkspaceId(),
                    root -> root.join(Site_.workspaces, JoinType.LEFT).get(Workspace_.id)));
            }
        }
        return specification;
    }
}
