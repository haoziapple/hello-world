package com.github.haozi.web.rest;

import com.github.haozi.XauthApp;

import com.github.haozi.domain.Template;
import com.github.haozi.domain.Auth;
import com.github.haozi.domain.Menu;
import com.github.haozi.repository.TemplateRepository;
import com.github.haozi.service.TemplateService;
import com.github.haozi.service.dto.TemplateDTO;
import com.github.haozi.service.mapper.TemplateMapper;
import com.github.haozi.web.rest.errors.ExceptionTranslator;
import com.github.haozi.service.dto.TemplateCriteria;
import com.github.haozi.service.TemplateQueryService;

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

import com.github.haozi.domain.enumeration.ClientType;
/**
 * Test class for the TemplateResource REST controller.
 *
 * @see TemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XauthApp.class)
public class TemplateResourceIntTest {

    private static final Integer DEFAULT_SPACE_ID = 1;
    private static final Integer UPDATED_SPACE_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final ClientType DEFAULT_CLIENT_TYPE = ClientType.PC;
    private static final ClientType UPDATED_CLIENT_TYPE = ClientType.APP;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_EXTMAP = "AAAAAAAAAA";
    private static final String UPDATED_EXTMAP = "BBBBBBBBBB";

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplateQueryService templateQueryService;

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

    private MockMvc restTemplateMockMvc;

    private Template template;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TemplateResource templateResource = new TemplateResource(templateService, templateQueryService);
        this.restTemplateMockMvc = MockMvcBuilders.standaloneSetup(templateResource)
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
    public static Template createEntity(EntityManager em) {
        Template template = new Template()
            .spaceId(DEFAULT_SPACE_ID)
            .siteId(DEFAULT_SITE_ID)
            .clientType(DEFAULT_CLIENT_TYPE)
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .extmap(DEFAULT_EXTMAP);
        return template;
    }

    @Before
    public void initTest() {
        template = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplate() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);
        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isCreated());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate + 1);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getSpaceId()).isEqualTo(DEFAULT_SPACE_ID);
        assertThat(testTemplate.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testTemplate.getClientType()).isEqualTo(DEFAULT_CLIENT_TYPE);
        assertThat(testTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplate.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testTemplate.getExtmap()).isEqualTo(DEFAULT_EXTMAP);
    }

    @Test
    @Transactional
    public void createTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template with an existing ID
        template.setId(1L);
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTemplates() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList
        restTemplateMockMvc.perform(get("/api/templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].spaceId").value(hasItem(DEFAULT_SPACE_ID)))
            .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
            .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP.toString())));
    }
    
    @Test
    @Transactional
    public void getTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(template.getId().intValue()))
            .andExpect(jsonPath("$.spaceId").value(DEFAULT_SPACE_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.clientType").value(DEFAULT_CLIENT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.extmap").value(DEFAULT_EXTMAP.toString()));
    }

    @Test
    @Transactional
    public void getAllTemplatesBySpaceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where spaceId equals to DEFAULT_SPACE_ID
        defaultTemplateShouldBeFound("spaceId.equals=" + DEFAULT_SPACE_ID);

        // Get all the templateList where spaceId equals to UPDATED_SPACE_ID
        defaultTemplateShouldNotBeFound("spaceId.equals=" + UPDATED_SPACE_ID);
    }

    @Test
    @Transactional
    public void getAllTemplatesBySpaceIdIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where spaceId in DEFAULT_SPACE_ID or UPDATED_SPACE_ID
        defaultTemplateShouldBeFound("spaceId.in=" + DEFAULT_SPACE_ID + "," + UPDATED_SPACE_ID);

        // Get all the templateList where spaceId equals to UPDATED_SPACE_ID
        defaultTemplateShouldNotBeFound("spaceId.in=" + UPDATED_SPACE_ID);
    }

    @Test
    @Transactional
    public void getAllTemplatesBySpaceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where spaceId is not null
        defaultTemplateShouldBeFound("spaceId.specified=true");

        // Get all the templateList where spaceId is null
        defaultTemplateShouldNotBeFound("spaceId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTemplatesBySpaceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where spaceId greater than or equals to DEFAULT_SPACE_ID
        defaultTemplateShouldBeFound("spaceId.greaterOrEqualThan=" + DEFAULT_SPACE_ID);

        // Get all the templateList where spaceId greater than or equals to UPDATED_SPACE_ID
        defaultTemplateShouldNotBeFound("spaceId.greaterOrEqualThan=" + UPDATED_SPACE_ID);
    }

    @Test
    @Transactional
    public void getAllTemplatesBySpaceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where spaceId less than or equals to DEFAULT_SPACE_ID
        defaultTemplateShouldNotBeFound("spaceId.lessThan=" + DEFAULT_SPACE_ID);

        // Get all the templateList where spaceId less than or equals to UPDATED_SPACE_ID
        defaultTemplateShouldBeFound("spaceId.lessThan=" + UPDATED_SPACE_ID);
    }


    @Test
    @Transactional
    public void getAllTemplatesBySiteIdIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where siteId equals to DEFAULT_SITE_ID
        defaultTemplateShouldBeFound("siteId.equals=" + DEFAULT_SITE_ID);

        // Get all the templateList where siteId equals to UPDATED_SITE_ID
        defaultTemplateShouldNotBeFound("siteId.equals=" + UPDATED_SITE_ID);
    }

    @Test
    @Transactional
    public void getAllTemplatesBySiteIdIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where siteId in DEFAULT_SITE_ID or UPDATED_SITE_ID
        defaultTemplateShouldBeFound("siteId.in=" + DEFAULT_SITE_ID + "," + UPDATED_SITE_ID);

        // Get all the templateList where siteId equals to UPDATED_SITE_ID
        defaultTemplateShouldNotBeFound("siteId.in=" + UPDATED_SITE_ID);
    }

    @Test
    @Transactional
    public void getAllTemplatesBySiteIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where siteId is not null
        defaultTemplateShouldBeFound("siteId.specified=true");

        // Get all the templateList where siteId is null
        defaultTemplateShouldNotBeFound("siteId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTemplatesBySiteIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where siteId greater than or equals to DEFAULT_SITE_ID
        defaultTemplateShouldBeFound("siteId.greaterOrEqualThan=" + DEFAULT_SITE_ID);

        // Get all the templateList where siteId greater than or equals to UPDATED_SITE_ID
        defaultTemplateShouldNotBeFound("siteId.greaterOrEqualThan=" + UPDATED_SITE_ID);
    }

    @Test
    @Transactional
    public void getAllTemplatesBySiteIdIsLessThanSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where siteId less than or equals to DEFAULT_SITE_ID
        defaultTemplateShouldNotBeFound("siteId.lessThan=" + DEFAULT_SITE_ID);

        // Get all the templateList where siteId less than or equals to UPDATED_SITE_ID
        defaultTemplateShouldBeFound("siteId.lessThan=" + UPDATED_SITE_ID);
    }


    @Test
    @Transactional
    public void getAllTemplatesByClientTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where clientType equals to DEFAULT_CLIENT_TYPE
        defaultTemplateShouldBeFound("clientType.equals=" + DEFAULT_CLIENT_TYPE);

        // Get all the templateList where clientType equals to UPDATED_CLIENT_TYPE
        defaultTemplateShouldNotBeFound("clientType.equals=" + UPDATED_CLIENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllTemplatesByClientTypeIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where clientType in DEFAULT_CLIENT_TYPE or UPDATED_CLIENT_TYPE
        defaultTemplateShouldBeFound("clientType.in=" + DEFAULT_CLIENT_TYPE + "," + UPDATED_CLIENT_TYPE);

        // Get all the templateList where clientType equals to UPDATED_CLIENT_TYPE
        defaultTemplateShouldNotBeFound("clientType.in=" + UPDATED_CLIENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllTemplatesByClientTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where clientType is not null
        defaultTemplateShouldBeFound("clientType.specified=true");

        // Get all the templateList where clientType is null
        defaultTemplateShouldNotBeFound("clientType.specified=false");
    }

    @Test
    @Transactional
    public void getAllTemplatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where name equals to DEFAULT_NAME
        defaultTemplateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the templateList where name equals to UPDATED_NAME
        defaultTemplateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTemplatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTemplateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the templateList where name equals to UPDATED_NAME
        defaultTemplateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTemplatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where name is not null
        defaultTemplateShouldBeFound("name.specified=true");

        // Get all the templateList where name is null
        defaultTemplateShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTemplatesByDescIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where desc equals to DEFAULT_DESC
        defaultTemplateShouldBeFound("desc.equals=" + DEFAULT_DESC);

        // Get all the templateList where desc equals to UPDATED_DESC
        defaultTemplateShouldNotBeFound("desc.equals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllTemplatesByDescIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where desc in DEFAULT_DESC or UPDATED_DESC
        defaultTemplateShouldBeFound("desc.in=" + DEFAULT_DESC + "," + UPDATED_DESC);

        // Get all the templateList where desc equals to UPDATED_DESC
        defaultTemplateShouldNotBeFound("desc.in=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllTemplatesByDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where desc is not null
        defaultTemplateShouldBeFound("desc.specified=true");

        // Get all the templateList where desc is null
        defaultTemplateShouldNotBeFound("desc.specified=false");
    }

    @Test
    @Transactional
    public void getAllTemplatesByExtmapIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where extmap equals to DEFAULT_EXTMAP
        defaultTemplateShouldBeFound("extmap.equals=" + DEFAULT_EXTMAP);

        // Get all the templateList where extmap equals to UPDATED_EXTMAP
        defaultTemplateShouldNotBeFound("extmap.equals=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllTemplatesByExtmapIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where extmap in DEFAULT_EXTMAP or UPDATED_EXTMAP
        defaultTemplateShouldBeFound("extmap.in=" + DEFAULT_EXTMAP + "," + UPDATED_EXTMAP);

        // Get all the templateList where extmap equals to UPDATED_EXTMAP
        defaultTemplateShouldNotBeFound("extmap.in=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllTemplatesByExtmapIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where extmap is not null
        defaultTemplateShouldBeFound("extmap.specified=true");

        // Get all the templateList where extmap is null
        defaultTemplateShouldNotBeFound("extmap.specified=false");
    }

    @Test
    @Transactional
    public void getAllTemplatesByAuthIsEqualToSomething() throws Exception {
        // Initialize the database
        Auth auth = AuthResourceIntTest.createEntity(em);
        em.persist(auth);
        em.flush();
        template.addAuth(auth);
        templateRepository.saveAndFlush(template);
        Long authId = auth.getId();

        // Get all the templateList where auth equals to authId
        defaultTemplateShouldBeFound("authId.equals=" + authId);

        // Get all the templateList where auth equals to authId + 1
        defaultTemplateShouldNotBeFound("authId.equals=" + (authId + 1));
    }


    @Test
    @Transactional
    public void getAllTemplatesByMenuIsEqualToSomething() throws Exception {
        // Initialize the database
        Menu menu = MenuResourceIntTest.createEntity(em);
        em.persist(menu);
        em.flush();
        template.addMenu(menu);
        templateRepository.saveAndFlush(template);
        Long menuId = menu.getId();

        // Get all the templateList where menu equals to menuId
        defaultTemplateShouldBeFound("menuId.equals=" + menuId);

        // Get all the templateList where menu equals to menuId + 1
        defaultTemplateShouldNotBeFound("menuId.equals=" + (menuId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTemplateShouldBeFound(String filter) throws Exception {
        restTemplateMockMvc.perform(get("/api/templates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].spaceId").value(hasItem(DEFAULT_SPACE_ID)))
            .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
            .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP)));

        // Check, that the count call also returns 1
        restTemplateMockMvc.perform(get("/api/templates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTemplateShouldNotBeFound(String filter) throws Exception {
        restTemplateMockMvc.perform(get("/api/templates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTemplateMockMvc.perform(get("/api/templates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTemplate() throws Exception {
        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Update the template
        Template updatedTemplate = templateRepository.findById(template.getId()).get();
        // Disconnect from session so that the updates on updatedTemplate are not directly saved in db
        em.detach(updatedTemplate);
        updatedTemplate
            .spaceId(UPDATED_SPACE_ID)
            .siteId(UPDATED_SITE_ID)
            .clientType(UPDATED_CLIENT_TYPE)
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .extmap(UPDATED_EXTMAP);
        TemplateDTO templateDTO = templateMapper.toDto(updatedTemplate);

        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isOk());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getSpaceId()).isEqualTo(UPDATED_SPACE_ID);
        assertThat(testTemplate.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testTemplate.getClientType()).isEqualTo(UPDATED_CLIENT_TYPE);
        assertThat(testTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplate.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testTemplate.getExtmap()).isEqualTo(UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void updateNonExistingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        int databaseSizeBeforeDelete = templateRepository.findAll().size();

        // Delete the template
        restTemplateMockMvc.perform(delete("/api/templates/{id}", template.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Template.class);
        Template template1 = new Template();
        template1.setId(1L);
        Template template2 = new Template();
        template2.setId(template1.getId());
        assertThat(template1).isEqualTo(template2);
        template2.setId(2L);
        assertThat(template1).isNotEqualTo(template2);
        template1.setId(null);
        assertThat(template1).isNotEqualTo(template2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateDTO.class);
        TemplateDTO templateDTO1 = new TemplateDTO();
        templateDTO1.setId(1L);
        TemplateDTO templateDTO2 = new TemplateDTO();
        assertThat(templateDTO1).isNotEqualTo(templateDTO2);
        templateDTO2.setId(templateDTO1.getId());
        assertThat(templateDTO1).isEqualTo(templateDTO2);
        templateDTO2.setId(2L);
        assertThat(templateDTO1).isNotEqualTo(templateDTO2);
        templateDTO1.setId(null);
        assertThat(templateDTO1).isNotEqualTo(templateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(templateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(templateMapper.fromId(null)).isNull();
    }
}
