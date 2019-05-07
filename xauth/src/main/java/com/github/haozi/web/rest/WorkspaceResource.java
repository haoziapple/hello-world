package com.github.haozi.web.rest;
import com.github.haozi.service.WorkspaceService;
import com.github.haozi.web.rest.errors.BadRequestAlertException;
import com.github.haozi.web.rest.util.HeaderUtil;
import com.github.haozi.web.rest.util.PaginationUtil;
import com.github.haozi.service.dto.WorkspaceDTO;
import com.github.haozi.service.dto.WorkspaceCriteria;
import com.github.haozi.service.WorkspaceQueryService;
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
 * REST controller for managing Workspace.
 */
@RestController
@RequestMapping("/api")
public class WorkspaceResource {

    private final Logger log = LoggerFactory.getLogger(WorkspaceResource.class);

    private static final String ENTITY_NAME = "workspace";

    private final WorkspaceService workspaceService;

    private final WorkspaceQueryService workspaceQueryService;

    public WorkspaceResource(WorkspaceService workspaceService, WorkspaceQueryService workspaceQueryService) {
        this.workspaceService = workspaceService;
        this.workspaceQueryService = workspaceQueryService;
    }

    /**
     * POST  /workspaces : Create a new workspace.
     *
     * @param workspaceDTO the workspaceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workspaceDTO, or with status 400 (Bad Request) if the workspace has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workspaces")
    public ResponseEntity<WorkspaceDTO> createWorkspace(@RequestBody WorkspaceDTO workspaceDTO) throws URISyntaxException {
        log.debug("REST request to save Workspace : {}", workspaceDTO);
        if (workspaceDTO.getId() != null) {
            throw new BadRequestAlertException("A new workspace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkspaceDTO result = workspaceService.save(workspaceDTO);
        return ResponseEntity.created(new URI("/api/workspaces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workspaces : Updates an existing workspace.
     *
     * @param workspaceDTO the workspaceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workspaceDTO,
     * or with status 400 (Bad Request) if the workspaceDTO is not valid,
     * or with status 500 (Internal Server Error) if the workspaceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workspaces")
    public ResponseEntity<WorkspaceDTO> updateWorkspace(@RequestBody WorkspaceDTO workspaceDTO) throws URISyntaxException {
        log.debug("REST request to update Workspace : {}", workspaceDTO);
        if (workspaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkspaceDTO result = workspaceService.save(workspaceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workspaceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workspaces : get all the workspaces.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of workspaces in body
     */
    @GetMapping("/workspaces")
    public ResponseEntity<List<WorkspaceDTO>> getAllWorkspaces(WorkspaceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Workspaces by criteria: {}", criteria);
        Page<WorkspaceDTO> page = workspaceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workspaces");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /workspaces/count : count all the workspaces.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/workspaces/count")
    public ResponseEntity<Long> countWorkspaces(WorkspaceCriteria criteria) {
        log.debug("REST request to count Workspaces by criteria: {}", criteria);
        return ResponseEntity.ok().body(workspaceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /workspaces/:id : get the "id" workspace.
     *
     * @param id the id of the workspaceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workspaceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/workspaces/{id}")
    public ResponseEntity<WorkspaceDTO> getWorkspace(@PathVariable Long id) {
        log.debug("REST request to get Workspace : {}", id);
        Optional<WorkspaceDTO> workspaceDTO = workspaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workspaceDTO);
    }

    /**
     * DELETE  /workspaces/:id : delete the "id" workspace.
     *
     * @param id the id of the workspaceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workspaces/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long id) {
        log.debug("REST request to delete Workspace : {}", id);
        workspaceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
