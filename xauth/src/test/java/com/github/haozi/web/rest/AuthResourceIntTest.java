package com.github.haozi.web.rest;

import com.github.haozi.XauthApp;

import com.github.haozi.domain.Auth;
import com.github.haozi.domain.Role;
import com.github.haozi.domain.Template;
import com.github.haozi.domain.Menu;
import com.github.haozi.repository.AuthRepository;
import com.github.haozi.service.AuthService;
import com.github.haozi.service.dto.AuthDTO;
import com.github.haozi.service.mapper.AuthMapper;
import com.github.haozi.web.rest.errors.ExceptionTranslator;
import com.github.haozi.service.dto.AuthCriteria;
import com.github.haozi.service.AuthQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.github.haozi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuthResource REST controller.
 *
 * @see AuthResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XauthApp.class)
public class AuthResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_EXTMAP = "AAAAAAAAAA";
    private static final String UPDATED_EXTMAP = "BBBBBBBBBB";

    @Autowired
    private AuthRepository authRepository;

    @Mock
    private AuthRepository authRepositoryMock;

    @Autowired
    private AuthMapper authMapper;

    @Mock
    private AuthService authServiceMock;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthQueryService authQueryService;

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

    private MockMvc restAuthMockMvc;

    private Auth auth;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuthResource authResource = new AuthResource(authService, authQueryService);
        this.restAuthMockMvc = MockMvcBuilders.standaloneSetup(authResource)
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
    public static Auth createEntity(EntityManager em) {
        Auth auth = new Auth()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .desc(DEFAULT_DESC)
            .extmap(DEFAULT_EXTMAP);
        return auth;
    }

    @Before
    public void initTest() {
        auth = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuth() throws Exception {
        int databaseSizeBeforeCreate = authRepository.findAll().size();

        // Create the Auth
        AuthDTO authDTO = authMapper.toDto(auth);
        restAuthMockMvc.perform(post("/api/auths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authDTO)))
            .andExpect(status().isCreated());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeCreate + 1);
        Auth testAuth = authList.get(authList.size() - 1);
        assertThat(testAuth.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuth.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuth.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testAuth.getExtmap()).isEqualTo(DEFAULT_EXTMAP);
    }

    @Test
    @Transactional
    public void createAuthWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = authRepository.findAll().size();

        // Create the Auth with an existing ID
        auth.setId(1L);
        AuthDTO authDTO = authMapper.toDto(auth);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthMockMvc.perform(post("/api/auths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuths() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList
        restAuthMockMvc.perform(get("/api/auths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auth.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAuthsWithEagerRelationshipsIsEnabled() throws Exception {
        AuthResource authResource = new AuthResource(authServiceMock, authQueryService);
        when(authServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAuthMockMvc = MockMvcBuilders.standaloneSetup(authResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAuthMockMvc.perform(get("/api/auths?eagerload=true"))
        .andExpect(status().isOk());

        verify(authServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAuthsWithEagerRelationshipsIsNotEnabled() throws Exception {
        AuthResource authResource = new AuthResource(authServiceMock, authQueryService);
            when(authServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAuthMockMvc = MockMvcBuilders.standaloneSetup(authResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAuthMockMvc.perform(get("/api/auths?eagerload=true"))
        .andExpect(status().isOk());

            verify(authServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAuth() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get the auth
        restAuthMockMvc.perform(get("/api/auths/{id}", auth.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auth.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.extmap").value(DEFAULT_EXTMAP.toString()));
    }

    @Test
    @Transactional
    public void getAllAuthsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where name equals to DEFAULT_NAME
        defaultAuthShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the authList where name equals to UPDATED_NAME
        defaultAuthShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAuthsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAuthShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the authList where name equals to UPDATED_NAME
        defaultAuthShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAuthsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where name is not null
        defaultAuthShouldBeFound("name.specified=true");

        // Get all the authList where name is null
        defaultAuthShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where code equals to DEFAULT_CODE
        defaultAuthShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the authList where code equals to UPDATED_CODE
        defaultAuthShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAuthsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where code in DEFAULT_CODE or UPDATED_CODE
        defaultAuthShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the authList where code equals to UPDATED_CODE
        defaultAuthShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAuthsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where code is not null
        defaultAuthShouldBeFound("code.specified=true");

        // Get all the authList where code is null
        defaultAuthShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthsByDescIsEqualToSomething() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where desc equals to DEFAULT_DESC
        defaultAuthShouldBeFound("desc.equals=" + DEFAULT_DESC);

        // Get all the authList where desc equals to UPDATED_DESC
        defaultAuthShouldNotBeFound("desc.equals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllAuthsByDescIsInShouldWork() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where desc in DEFAULT_DESC or UPDATED_DESC
        defaultAuthShouldBeFound("desc.in=" + DEFAULT_DESC + "," + UPDATED_DESC);

        // Get all the authList where desc equals to UPDATED_DESC
        defaultAuthShouldNotBeFound("desc.in=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllAuthsByDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where desc is not null
        defaultAuthShouldBeFound("desc.specified=true");

        // Get all the authList where desc is null
        defaultAuthShouldNotBeFound("desc.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthsByExtmapIsEqualToSomething() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where extmap equals to DEFAULT_EXTMAP
        defaultAuthShouldBeFound("extmap.equals=" + DEFAULT_EXTMAP);

        // Get all the authList where extmap equals to UPDATED_EXTMAP
        defaultAuthShouldNotBeFound("extmap.equals=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllAuthsByExtmapIsInShouldWork() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where extmap in DEFAULT_EXTMAP or UPDATED_EXTMAP
        defaultAuthShouldBeFound("extmap.in=" + DEFAULT_EXTMAP + "," + UPDATED_EXTMAP);

        // Get all the authList where extmap equals to UPDATED_EXTMAP
        defaultAuthShouldNotBeFound("extmap.in=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllAuthsByExtmapIsNullOrNotNull() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList where extmap is not null
        defaultAuthShouldBeFound("extmap.specified=true");

        // Get all the authList where extmap is null
        defaultAuthShouldNotBeFound("extmap.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthsByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        Role role = RoleResourceIntTest.createEntity(em);
        em.persist(role);
        em.flush();
        auth.addRole(role);
        authRepository.saveAndFlush(auth);
        Long roleId = role.getId();

        // Get all the authList where role equals to roleId
        defaultAuthShouldBeFound("roleId.equals=" + roleId);

        // Get all the authList where role equals to roleId + 1
        defaultAuthShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }


    @Test
    @Transactional
    public void getAllAuthsByTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        Template template = TemplateResourceIntTest.createEntity(em);
        em.persist(template);
        em.flush();
        auth.addTemplate(template);
        authRepository.saveAndFlush(auth);
        Long templateId = template.getId();

        // Get all the authList where template equals to templateId
        defaultAuthShouldBeFound("templateId.equals=" + templateId);

        // Get all the authList where template equals to templateId + 1
        defaultAuthShouldNotBeFound("templateId.equals=" + (templateId + 1));
    }


    @Test
    @Transactional
    public void getAllAuthsByMenuIsEqualToSomething() throws Exception {
        // Initialize the database
        Menu menu = MenuResourceIntTest.createEntity(em);
        em.persist(menu);
        em.flush();
        auth.addMenu(menu);
        authRepository.saveAndFlush(auth);
        Long menuId = menu.getId();

        // Get all the authList where menu equals to menuId
        defaultAuthShouldBeFound("menuId.equals=" + menuId);

        // Get all the authList where menu equals to menuId + 1
        defaultAuthShouldNotBeFound("menuId.equals=" + (menuId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAuthShouldBeFound(String filter) throws Exception {
        restAuthMockMvc.perform(get("/api/auths?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auth.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP)));

        // Check, that the count call also returns 1
        restAuthMockMvc.perform(get("/api/auths/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAuthShouldNotBeFound(String filter) throws Exception {
        restAuthMockMvc.perform(get("/api/auths?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuthMockMvc.perform(get("/api/auths/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAuth() throws Exception {
        // Get the auth
        restAuthMockMvc.perform(get("/api/auths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuth() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        int databaseSizeBeforeUpdate = authRepository.findAll().size();

        // Update the auth
        Auth updatedAuth = authRepository.findById(auth.getId()).get();
        // Disconnect from session so that the updates on updatedAuth are not directly saved in db
        em.detach(updatedAuth);
        updatedAuth
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .desc(UPDATED_DESC)
            .extmap(UPDATED_EXTMAP);
        AuthDTO authDTO = authMapper.toDto(updatedAuth);

        restAuthMockMvc.perform(put("/api/auths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authDTO)))
            .andExpect(status().isOk());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeUpdate);
        Auth testAuth = authList.get(authList.size() - 1);
        assertThat(testAuth.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuth.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuth.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testAuth.getExtmap()).isEqualTo(UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void updateNonExistingAuth() throws Exception {
        int databaseSizeBeforeUpdate = authRepository.findAll().size();

        // Create the Auth
        AuthDTO authDTO = authMapper.toDto(auth);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthMockMvc.perform(put("/api/auths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAuth() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        int databaseSizeBeforeDelete = authRepository.findAll().size();

        // Delete the auth
        restAuthMockMvc.perform(delete("/api/auths/{id}", auth.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auth.class);
        Auth auth1 = new Auth();
        auth1.setId(1L);
        Auth auth2 = new Auth();
        auth2.setId(auth1.getId());
        assertThat(auth1).isEqualTo(auth2);
        auth2.setId(2L);
        assertThat(auth1).isNotEqualTo(auth2);
        auth1.setId(null);
        assertThat(auth1).isNotEqualTo(auth2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthDTO.class);
        AuthDTO authDTO1 = new AuthDTO();
        authDTO1.setId(1L);
        AuthDTO authDTO2 = new AuthDTO();
        assertThat(authDTO1).isNotEqualTo(authDTO2);
        authDTO2.setId(authDTO1.getId());
        assertThat(authDTO1).isEqualTo(authDTO2);
        authDTO2.setId(2L);
        assertThat(authDTO1).isNotEqualTo(authDTO2);
        authDTO1.setId(null);
        assertThat(authDTO1).isNotEqualTo(authDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(authMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(authMapper.fromId(null)).isNull();
    }
}
