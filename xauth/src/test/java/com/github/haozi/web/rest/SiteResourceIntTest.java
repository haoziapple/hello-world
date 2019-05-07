package com.github.haozi.web.rest;

import com.github.haozi.XauthApp;

import com.github.haozi.domain.Site;
import com.github.haozi.domain.Workspace;
import com.github.haozi.repository.SiteRepository;
import com.github.haozi.service.SiteService;
import com.github.haozi.service.dto.SiteDTO;
import com.github.haozi.service.mapper.SiteMapper;
import com.github.haozi.web.rest.errors.ExceptionTranslator;
import com.github.haozi.service.dto.SiteCriteria;
import com.github.haozi.service.SiteQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.github.haozi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SiteResource REST controller.
 *
 * @see SiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XauthApp.class)
public class SiteResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_URL = "AAAAAAAAAA";
    private static final String UPDATED_HOME_URL = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_EXTMAP = "AAAAAAAAAA";
    private static final String UPDATED_EXTMAP = "BBBBBBBBBB";

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private SiteService siteService;

    @Autowired
    private SiteQueryService siteQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSiteMockMvc;

    private Site site;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SiteResource siteResource = new SiteResource(siteService, siteQueryService);
        this.restSiteMockMvc = MockMvcBuilders.standaloneSetup(siteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Site createEntity(EntityManager em) {
        Site site = new Site()
            .name(DEFAULT_NAME)
            .homeUrl(DEFAULT_HOME_URL)
            .remark(DEFAULT_REMARK)
            .extmap(DEFAULT_EXTMAP);
        return site;
    }

    @Before
    public void initTest() {
        site = createEntity(em);
    }

    @Test
    @Transactional
    public void createSite() throws Exception {
        int databaseSizeBeforeCreate = siteRepository.findAll().size();

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);
        restSiteMockMvc.perform(post("/api/sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isCreated());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeCreate + 1);
        Site testSite = siteList.get(siteList.size() - 1);
        assertThat(testSite.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSite.getHomeUrl()).isEqualTo(DEFAULT_HOME_URL);
        assertThat(testSite.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testSite.getExtmap()).isEqualTo(DEFAULT_EXTMAP);
    }

    @Test
    @Transactional
    public void createSiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siteRepository.findAll().size();

        // Create the Site with an existing ID
        site.setId(1L);
        SiteDTO siteDTO = siteMapper.toDto(site);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteMockMvc.perform(post("/api/sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSites() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList
        restSiteMockMvc.perform(get("/api/sites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(site.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].homeUrl").value(hasItem(DEFAULT_HOME_URL.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP.toString())));
    }
    
    @Test
    @Transactional
    public void getSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get the site
        restSiteMockMvc.perform(get("/api/sites/{id}", site.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(site.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.homeUrl").value(DEFAULT_HOME_URL.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.extmap").value(DEFAULT_EXTMAP.toString()));
    }

    @Test
    @Transactional
    public void getAllSitesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where name equals to DEFAULT_NAME
        defaultSiteShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the siteList where name equals to UPDATED_NAME
        defaultSiteShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSitesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSiteShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the siteList where name equals to UPDATED_NAME
        defaultSiteShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSitesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where name is not null
        defaultSiteShouldBeFound("name.specified=true");

        // Get all the siteList where name is null
        defaultSiteShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSitesByHomeUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where homeUrl equals to DEFAULT_HOME_URL
        defaultSiteShouldBeFound("homeUrl.equals=" + DEFAULT_HOME_URL);

        // Get all the siteList where homeUrl equals to UPDATED_HOME_URL
        defaultSiteShouldNotBeFound("homeUrl.equals=" + UPDATED_HOME_URL);
    }

    @Test
    @Transactional
    public void getAllSitesByHomeUrlIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where homeUrl in DEFAULT_HOME_URL or UPDATED_HOME_URL
        defaultSiteShouldBeFound("homeUrl.in=" + DEFAULT_HOME_URL + "," + UPDATED_HOME_URL);

        // Get all the siteList where homeUrl equals to UPDATED_HOME_URL
        defaultSiteShouldNotBeFound("homeUrl.in=" + UPDATED_HOME_URL);
    }

    @Test
    @Transactional
    public void getAllSitesByHomeUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where homeUrl is not null
        defaultSiteShouldBeFound("homeUrl.specified=true");

        // Get all the siteList where homeUrl is null
        defaultSiteShouldNotBeFound("homeUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllSitesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where remark equals to DEFAULT_REMARK
        defaultSiteShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the siteList where remark equals to UPDATED_REMARK
        defaultSiteShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllSitesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultSiteShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the siteList where remark equals to UPDATED_REMARK
        defaultSiteShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllSitesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where remark is not null
        defaultSiteShouldBeFound("remark.specified=true");

        // Get all the siteList where remark is null
        defaultSiteShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    public void getAllSitesByExtmapIsEqualToSomething() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where extmap equals to DEFAULT_EXTMAP
        defaultSiteShouldBeFound("extmap.equals=" + DEFAULT_EXTMAP);

        // Get all the siteList where extmap equals to UPDATED_EXTMAP
        defaultSiteShouldNotBeFound("extmap.equals=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllSitesByExtmapIsInShouldWork() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where extmap in DEFAULT_EXTMAP or UPDATED_EXTMAP
        defaultSiteShouldBeFound("extmap.in=" + DEFAULT_EXTMAP + "," + UPDATED_EXTMAP);

        // Get all the siteList where extmap equals to UPDATED_EXTMAP
        defaultSiteShouldNotBeFound("extmap.in=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllSitesByExtmapIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList where extmap is not null
        defaultSiteShouldBeFound("extmap.specified=true");

        // Get all the siteList where extmap is null
        defaultSiteShouldNotBeFound("extmap.specified=false");
    }

    @Test
    @Transactional
    public void getAllSitesByWorkspaceIsEqualToSomething() throws Exception {
        // Initialize the database
        Workspace workspace = WorkspaceResourceIntTest.createEntity(em);
        em.persist(workspace);
        em.flush();
        site.addWorkspace(workspace);
        siteRepository.saveAndFlush(site);
        Long workspaceId = workspace.getId();

        // Get all the siteList where workspace equals to workspaceId
        defaultSiteShouldBeFound("workspaceId.equals=" + workspaceId);

        // Get all the siteList where workspace equals to workspaceId + 1
        defaultSiteShouldNotBeFound("workspaceId.equals=" + (workspaceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSiteShouldBeFound(String filter) throws Exception {
        restSiteMockMvc.perform(get("/api/sites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(site.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].homeUrl").value(hasItem(DEFAULT_HOME_URL)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP)));

        // Check, that the count call also returns 1
        restSiteMockMvc.perform(get("/api/sites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSiteShouldNotBeFound(String filter) throws Exception {
        restSiteMockMvc.perform(get("/api/sites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSiteMockMvc.perform(get("/api/sites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSite() throws Exception {
        // Get the site
        restSiteMockMvc.perform(get("/api/sites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        int databaseSizeBeforeUpdate = siteRepository.findAll().size();

        // Update the site
        Site updatedSite = siteRepository.findById(site.getId()).get();
        // Disconnect from session so that the updates on updatedSite are not directly saved in db
        em.detach(updatedSite);
        updatedSite
            .name(UPDATED_NAME)
            .homeUrl(UPDATED_HOME_URL)
            .remark(UPDATED_REMARK)
            .extmap(UPDATED_EXTMAP);
        SiteDTO siteDTO = siteMapper.toDto(updatedSite);

        restSiteMockMvc.perform(put("/api/sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isOk());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
        Site testSite = siteList.get(siteList.size() - 1);
        assertThat(testSite.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSite.getHomeUrl()).isEqualTo(UPDATED_HOME_URL);
        assertThat(testSite.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testSite.getExtmap()).isEqualTo(UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void updateNonExistingSite() throws Exception {
        int databaseSizeBeforeUpdate = siteRepository.findAll().size();

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteMockMvc.perform(put("/api/sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        int databaseSizeBeforeDelete = siteRepository.findAll().size();

        // Delete the site
        restSiteMockMvc.perform(delete("/api/sites/{id}", site.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Site.class);
        Site site1 = new Site();
        site1.setId(1L);
        Site site2 = new Site();
        site2.setId(site1.getId());
        assertThat(site1).isEqualTo(site2);
        site2.setId(2L);
        assertThat(site1).isNotEqualTo(site2);
        site1.setId(null);
        assertThat(site1).isNotEqualTo(site2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteDTO.class);
        SiteDTO siteDTO1 = new SiteDTO();
        siteDTO1.setId(1L);
        SiteDTO siteDTO2 = new SiteDTO();
        assertThat(siteDTO1).isNotEqualTo(siteDTO2);
        siteDTO2.setId(siteDTO1.getId());
        assertThat(siteDTO1).isEqualTo(siteDTO2);
        siteDTO2.setId(2L);
        assertThat(siteDTO1).isNotEqualTo(siteDTO2);
        siteDTO1.setId(null);
        assertThat(siteDTO1).isNotEqualTo(siteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(siteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(siteMapper.fromId(null)).isNull();
    }
}
