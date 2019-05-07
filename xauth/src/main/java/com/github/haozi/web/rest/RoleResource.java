package com.github.haozi.web.rest;
import com.github.haozi.service.RoleService;
import com.github.haozi.web.rest.errors.BadRequestAlertException;
import com.github.haozi.web.rest.util.HeaderUtil;
import com.github.haozi.web.rest.util.PaginationUtil;
import com.github.haozi.service.dto.RoleDTO;
import com.github.haozi.service.dto.RoleCriteria;
import com.github.haozi.service.RoleQueryService;
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
 * REST controller for managing Role.
 */
@RestController
@RequestMapping("/api")
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);

    private static final String ENTITY_NAME = "role";

    private final RoleService roleService;

    private final RoleQueryService roleQueryService;

    public RoleResource(RoleService roleService, RoleQueryService roleQueryService) {
        this.roleService = roleService;
        this.roleQueryService = roleQueryService;
    }

    /**
     * POST  /roles : Create a new role.
     *
     * @param roleDTO the roleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roleDTO, or with status 400 (Bad Request) if the role has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/roles")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) throws URISyntaxException {
        log.debug("REST request to save Role : {}", roleDTO);
        if (roleDTO.getId() != null) {
            throw new BadRequestAlertException("A new role cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleDTO result = roleService.save(roleDTO);
        return ResponseEntity.created(new URI("/api/roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /roles : Updates an existing role.
     *
     * @param roleDTO the roleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roleDTO,
     * or with status 400 (Bad Request) if the roleDTO is not valid,
     * or with status 500 (Internal Server Error) if the roleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/roles")
    public ResponseEntity<RoleDTO> updateRole(@RequestBody RoleDTO roleDTO) throws URISyntaxException {
        log.debug("REST request to update Role : {}", roleDTO);
        if (roleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoleDTO result = roleService.save(roleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /roles : get all the roles.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of roles in body
     */
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles(RoleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Roles by criteria: {}", criteria);
        Page<RoleDTO> page = roleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/roles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /roles/count : count all the roles.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/roles/count")
    public ResponseEntity<Long> countRoles(RoleCriteria criteria) {
        log.debug("REST request to count Roles by criteria: {}", criteria);
        return ResponseEntity.ok().body(roleQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /roles/:id : get the "id" role.
     *
     * @param id the id of the roleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable Long id) {
        log.debug("REST request to get Role : {}", id);
        Optional<RoleDTO> roleDTO = roleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleDTO);
    }

    /**
     * DELETE  /roles/:id : delete the "id" role.
     *
     * @param id the id of the roleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        log.debug("REST request to delete Role : {}", id);
        roleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
