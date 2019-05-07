package com.github.haozi.web.rest;
import com.github.haozi.service.MenuService;
import com.github.haozi.web.rest.errors.BadRequestAlertException;
import com.github.haozi.web.rest.util.HeaderUtil;
import com.github.haozi.web.rest.util.PaginationUtil;
import com.github.haozi.service.dto.MenuDTO;
import com.github.haozi.service.dto.MenuCriteria;
import com.github.haozi.service.MenuQueryService;
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
 * REST controller for managing Menu.
 */
@RestController
@RequestMapping("/api")
public class MenuResource {

    private final Logger log = LoggerFactory.getLogger(MenuResource.class);

    private static final String ENTITY_NAME = "menu";

    private final MenuService menuService;

    private final MenuQueryService menuQueryService;

    public MenuResource(MenuService menuService, MenuQueryService menuQueryService) {
        this.menuService = menuService;
        this.menuQueryService = menuQueryService;
    }

    /**
     * POST  /menus : Create a new menu.
     *
     * @param menuDTO the menuDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new menuDTO, or with status 400 (Bad Request) if the menu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/menus")
    public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO) throws URISyntaxException {
        log.debug("REST request to save Menu : {}", menuDTO);
        if (menuDTO.getId() != null) {
            throw new BadRequestAlertException("A new menu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MenuDTO result = menuService.save(menuDTO);
        return ResponseEntity.created(new URI("/api/menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /menus : Updates an existing menu.
     *
     * @param menuDTO the menuDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated menuDTO,
     * or with status 400 (Bad Request) if the menuDTO is not valid,
     * or with status 500 (Internal Server Error) if the menuDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/menus")
    public ResponseEntity<MenuDTO> updateMenu(@RequestBody MenuDTO menuDTO) throws URISyntaxException {
        log.debug("REST request to update Menu : {}", menuDTO);
        if (menuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MenuDTO result = menuService.save(menuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, menuDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /menus : get all the menus.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of menus in body
     */
    @GetMapping("/menus")
    public ResponseEntity<List<MenuDTO>> getAllMenus(MenuCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Menus by criteria: {}", criteria);
        Page<MenuDTO> page = menuQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/menus");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /menus/count : count all the menus.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/menus/count")
    public ResponseEntity<Long> countMenus(MenuCriteria criteria) {
        log.debug("REST request to count Menus by criteria: {}", criteria);
        return ResponseEntity.ok().body(menuQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /menus/:id : get the "id" menu.
     *
     * @param id the id of the menuDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the menuDTO, or with status 404 (Not Found)
     */
    @GetMapping("/menus/{id}")
    public ResponseEntity<MenuDTO> getMenu(@PathVariable Long id) {
        log.debug("REST request to get Menu : {}", id);
        Optional<MenuDTO> menuDTO = menuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(menuDTO);
    }

    /**
     * DELETE  /menus/:id : delete the "id" menu.
     *
     * @param id the id of the menuDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/menus/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        log.debug("REST request to delete Menu : {}", id);
        menuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
