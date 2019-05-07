package com.github.haozi.service;

import com.github.haozi.domain.Template;
import com.github.haozi.repository.TemplateRepository;
import com.github.haozi.service.dto.TemplateDTO;
import com.github.haozi.service.mapper.TemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Template.
 */
@Service
@Transactional
public class TemplateService {

    private final Logger log = LoggerFactory.getLogger(TemplateService.class);

    private final TemplateRepository templateRepository;

    private final TemplateMapper templateMapper;

    public TemplateService(TemplateRepository templateRepository, TemplateMapper templateMapper) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
    }

    /**
     * Save a template.
     *
     * @param templateDTO the entity to save
     * @return the persisted entity
     */
    public TemplateDTO save(TemplateDTO templateDTO) {
        log.debug("Request to save Template : {}", templateDTO);
        Template template = templateMapper.toEntity(templateDTO);
        template = templateRepository.save(template);
        return templateMapper.toDto(template);
    }

    /**
     * Get all the templates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Templates");
        return templateRepository.findAll(pageable)
            .map(templateMapper::toDto);
    }


    /**
     * Get one template by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TemplateDTO> findOne(Long id) {
        log.debug("Request to get Template : {}", id);
        return templateRepository.findById(id)
            .map(templateMapper::toDto);
    }

    /**
     * Delete the template by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Template : {}", id);
        templateRepository.deleteById(id);
    }
}
