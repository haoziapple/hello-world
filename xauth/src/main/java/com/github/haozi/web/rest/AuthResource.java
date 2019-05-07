package com.github.haozi.web.rest;
import com.github.haozi.service.AuthService;
import com.github.haozi.web.rest.errors.BadRequestAlertException;
import com.github.haozi.web.rest.util.HeaderUtil;
import com.github.haozi.web.rest.util.PaginationUtil;
import com.github.haozi.service.dto.AuthDTO;
import com.github.haozi.service.dto.AuthCriteria;
import com.github.haozi.service.AuthQueryService;
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
 * REST controller for managing Auth.
 */
@RestController
@RequestMapping("/api")
public class AuthResource {

    private final Logger log = LoggerFactory.getLogger(AuthResource.class);

    private static final String ENTITY_NAME = "auth";

    private final AuthService authService;

    private final AuthQueryService authQueryService;

    public AuthResource(AuthService authService, AuthQueryService authQueryService) {
        this.authService = authService;
        this.authQueryService = authQueryService;
    }

    /**
     * POST  /auths : Create a new auth.
     *
     * @param authDTO the authDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authDTO, or with status 400 (Bad Request) if the auth has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auths")
    public ResponseEntity<AuthDTO> createAuth(@RequestBody AuthDTO authDTO) throws URISyntaxException {
        log.debug("REST request to save Auth : {}", authDTO);
        if (authDTO.getId() != null) {
            throw new BadRequestAlertException("A new auth cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthDTO result = authService.save(authDTO);
        return ResponseEntity.created(new URI("/api/auths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auths : Updates an existing auth.
     *
     * @param authDTO the authDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authDTO,
     * or with status 400 (Bad Request) if the authDTO is not valid,
     * or with status 500 (Internal Server Error) if the authDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auths")
    public ResponseEntity<AuthDTO> updateAuth(@RequestBody AuthDTO authDTO) throws URISyntaxException {
        log.debug("REST request to update Auth : {}", authDTO);
        if (authDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AuthDTO result = authService.save(authDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auths : get all the auths.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of auths in body
     */
    @GetMapping("/auths")
    public ResponseEntity<List<AuthDTO>> getAllAuths(AuthCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Auths by criteria: {}", criteria);
        Page<AuthDTO> page = authQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/auths");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /auths/count : count all the auths.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/auths/count")
    public ResponseEntity<Long> countAuths(AuthCriteria criteria) {
        log.debug("REST request to count Auths by criteria: {}", criteria);
        return ResponseEntity.ok().body(authQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /auths/:id : get the "id" auth.
     *
     * @param id the id of the authDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authDTO, or with status 404 (Not Found)
     */
    @GetMapping("/auths/{id}")
    public ResponseEntity<AuthDTO> getAuth(@PathVariable Long id) {
        log.debug("REST request to get Auth : {}", id);
        Optional<AuthDTO> authDTO = authService.findOne(id);
        return ResponseUtil.wrapOrNotFound(authDTO);
    }

    /**
     * DELETE  /auths/:id : delete the "id" auth.
     *
     * @param id the id of the authDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auths/{id}")
    public ResponseEntity<Void> deleteAuth(@PathVariable Long id) {
        log.debug("REST request to delete Auth : {}", id);
        authService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
