package com.github.haozi.web.rest;
import com.github.haozi.service.TemplateService;
import com.github.haozi.web.rest.errors.BadRequestAlertException;
import com.github.haozi.web.rest.util.HeaderUtil;
import com.github.haozi.web.rest.util.PaginationUtil;
import com.github.haozi.service.dto.TemplateDTO;
import com.github.haozi.service.dto.TemplateCriteria;
import com.github.haozi.service.TemplateQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Template.
 */
@RestController
@RequestMapping("/api")
public class TemplateResource {

    private final Logger log = LoggerFactory.getLogger(TemplateResource.class);

    private static final String ENTITY_NAME = "template";

    private final TemplateService templateService;

    private final TemplateQueryService templateQueryService;

    public TemplateResource(TemplateService templateService, TemplateQueryService templateQueryService) {
        this.templateService = templateService;
        this.templateQueryService = templateQueryService;
    }

    /**
     * POST  /templates : Create a new template.
     *
     * @param templateDTO the templateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new templateDTO, or with status 400 (Bad Request) if the template has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/templates")
    public ResponseEntity<TemplateDTO> createTemplate(@RequestBody TemplateDTO templateDTO) throws URISyntaxException {
        log.debug("REST request to save Template : {}", templateDTO);
        if (templateDTO.getId() != null) {
            throw new BadRequestAlertException("A new template cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateDTO result = templateService.save(templateDTO);
        return ResponseEntity.created(new URI("/api/templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /templates : Updates an existing template.
     *
     * @param templateDTO the templateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated templateDTO,
     * or with status 400 (Bad Request) if the templateDTO is not valid,
     * or with status 500 (Internal Server Error) if the templateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/templates")
    public ResponseEntity<TemplateDTO> updateTemplate(@RequestBody TemplateDTO templateDTO) throws URISyntaxException {
        log.debug("REST request to update Template : {}", templateDTO);
        if (templateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TemplateDTO result = templateService.save(templateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, templateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /templates : get all the templates.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of templates in body
     */
    @GetMapping("/templates")
    public ResponseEntity<List<TemplateDTO>> getAllTemplates(TemplateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Templates by criteria: {}", criteria);
        Page<TemplateDTO> page = templateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/templates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /templates/count : count all the templates.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/templates/count")
    public ResponseEntity<Long> countTemplates(TemplateCriteria criteria) {
        log.debug("REST request to count Templates by criteria: {}", criteria);
        return ResponseEntity.ok().body(templateQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /templates/:id : get the "id" template.
     *
     * @param id the id of the templateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the templateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/templates/{id}")
    public ResponseEntity<TemplateDTO> getTemplate(@PathVariable Long id) {
        log.debug("REST request to get Template : {}", id);
        Optional<TemplateDTO> templateDTO = templateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateDTO);
    }

    /**
     * DELETE  /templates/:id : delete the "id" template.
     *
     * @param id the id of the templateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/templates/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        log.debug("REST request to delete Template : {}", id);
        templateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
