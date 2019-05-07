package com.github.haozi.web.rest;

import com.github.haozi.XauthApp;

import com.github.haozi.domain.Role;
import com.github.haozi.domain.Auth;
import com.github.haozi.domain.Menu;
import com.github.haozi.domain.Profile;
import com.github.haozi.repository.RoleRepository;
import com.github.haozi.service.RoleService;
import com.github.haozi.service.dto.RoleDTO;
import com.github.haozi.service.mapper.RoleMapper;
import com.github.haozi.web.rest.errors.ExceptionTranslator;
import com.github.haozi.service.dto.RoleCriteria;
import com.github.haozi.service.RoleQueryService;

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
import com.github.haozi.domain.enumeration.RoleType;
/**
 * Test class for the RoleResource REST controller.
 *
 * @see RoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XauthApp.class)
public class RoleResourceIntTest {

    private static final Integer DEFAULT_SPACE_ID = 1;
    private static final Integer UPDATED_SPACE_ID = 2;

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final ClientType DEFAULT_CLIENT_TYPE = ClientType.PC;
    private static final ClientType UPDATED_CLIENT_TYPE = ClientType.APP;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_EXTMAP = "AAAAAAAAAA";
    private static final String UPDATED_EXTMAP = "BBBBBBBBBB";

    private static final RoleType DEFAULT_ROLE_TYPE = RoleType.SYSTEM_ADMIN;
    private static final RoleType UPDATED_ROLE_TYPE = RoleType.ADMIN;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleQueryService roleQueryService;

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

    private MockMvc restRoleMockMvc;

    private Role role;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoleResource roleResource = new RoleResource(roleService, roleQueryService);
        this.restRoleMockMvc = MockMvcBuilders.standaloneSetup(roleResource)
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
    public static Role createEntity(EntityManager em) {
        Role role = new Role()
            .spaceId(DEFAULT_SPACE_ID)
            .siteId(DEFAULT_SITE_ID)
            .clientType(DEFAULT_CLIENT_TYPE)
            .name(DEFAULT_NAME)
            .remark(DEFAULT_REMARK)
            .extmap(DEFAULT_EXTMAP)
            .roleType(DEFAULT_ROLE_TYPE);
        return role;
    }

    @Before
    public void initTest() {
        role = createEntity(em);
    }

    @Test
    @Transactional
    public void createRole() throws Exception {
        int databaseSizeBeforeCreate = roleRepository.findAll().size();

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);
        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isCreated());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeCreate + 1);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getSpaceId()).isEqualTo(DEFAULT_SPACE_ID);
        assertThat(testRole.getSiteId()).isEqualTo(DEFAULT_SITE_ID);
        assertThat(testRole.getClientType()).isEqualTo(DEFAULT_CLIENT_TYPE);
        assertThat(testRole.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRole.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testRole.getExtmap()).isEqualTo(DEFAULT_EXTMAP);
        assertThat(testRole.getRoleType()).isEqualTo(DEFAULT_ROLE_TYPE);
    }

    @Test
    @Transactional
    public void createRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleRepository.findAll().size();

        // Create the Role with an existing ID
        role.setId(1L);
        RoleDTO roleDTO = roleMapper.toDto(role);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRoles() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(role.getId().intValue())))
            .andExpect(jsonPath("$.[*].spaceId").value(hasItem(DEFAULT_SPACE_ID)))
            .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
            .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP.toString())))
            .andExpect(jsonPath("$.[*].roleType").value(hasItem(DEFAULT_ROLE_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", role.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(role.getId().intValue()))
            .andExpect(jsonPath("$.spaceId").value(DEFAULT_SPACE_ID))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.clientType").value(DEFAULT_CLIENT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.extmap").value(DEFAULT_EXTMAP.toString()))
            .andExpect(jsonPath("$.roleType").value(DEFAULT_ROLE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllRolesBySpaceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where spaceId equals to DEFAULT_SPACE_ID
        defaultRoleShouldBeFound("spaceId.equals=" + DEFAULT_SPACE_ID);

        // Get all the roleList where spaceId equals to UPDATED_SPACE_ID
        defaultRoleShouldNotBeFound("spaceId.equals=" + UPDATED_SPACE_ID);
    }

    @Test
    @Transactional
    public void getAllRolesBySpaceIdIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where spaceId in DEFAULT_SPACE_ID or UPDATED_SPACE_ID
        defaultRoleShouldBeFound("spaceId.in=" + DEFAULT_SPACE_ID + "," + UPDATED_SPACE_ID);

        // Get all the roleList where spaceId equals to UPDATED_SPACE_ID
        defaultRoleShouldNotBeFound("spaceId.in=" + UPDATED_SPACE_ID);
    }

    @Test
    @Transactional
    public void getAllRolesBySpaceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where spaceId is not null
        defaultRoleShouldBeFound("spaceId.specified=true");

        // Get all the roleList where spaceId is null
        defaultRoleShouldNotBeFound("spaceId.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesBySpaceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where spaceId greater than or equals to DEFAULT_SPACE_ID
        defaultRoleShouldBeFound("spaceId.greaterOrEqualThan=" + DEFAULT_SPACE_ID);

        // Get all the roleList where spaceId greater than or equals to UPDATED_SPACE_ID
        defaultRoleShouldNotBeFound("spaceId.greaterOrEqualThan=" + UPDATED_SPACE_ID);
    }

    @Test
    @Transactional
    public void getAllRolesBySpaceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where spaceId less than or equals to DEFAULT_SPACE_ID
        defaultRoleShouldNotBeFound("spaceId.lessThan=" + DEFAULT_SPACE_ID);

        // Get all the roleList where spaceId less than or equals to UPDATED_SPACE_ID
        defaultRoleShouldBeFound("spaceId.lessThan=" + UPDATED_SPACE_ID);
    }


    @Test
    @Transactional
    public void getAllRolesBySiteIdIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where siteId equals to DEFAULT_SITE_ID
        defaultRoleShouldBeFound("siteId.equals=" + DEFAULT_SITE_ID);

        // Get all the roleList where siteId equals to UPDATED_SITE_ID
        defaultRoleShouldNotBeFound("siteId.equals=" + UPDATED_SITE_ID);
    }

    @Test
    @Transactional
    public void getAllRolesBySiteIdIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where siteId in DEFAULT_SITE_ID or UPDATED_SITE_ID
        defaultRoleShouldBeFound("siteId.in=" + DEFAULT_SITE_ID + "," + UPDATED_SITE_ID);

        // Get all the roleList where siteId equals to UPDATED_SITE_ID
        defaultRoleShouldNotBeFound("siteId.in=" + UPDATED_SITE_ID);
    }

    @Test
    @Transactional
    public void getAllRolesBySiteIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where siteId is not null
        defaultRoleShouldBeFound("siteId.specified=true");

        // Get all the roleList where siteId is null
        defaultRoleShouldNotBeFound("siteId.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesBySiteIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where siteId greater than or equals to DEFAULT_SITE_ID
        defaultRoleShouldBeFound("siteId.greaterOrEqualThan=" + DEFAULT_SITE_ID);

        // Get all the roleList where siteId greater than or equals to UPDATED_SITE_ID
        defaultRoleShouldNotBeFound("siteId.greaterOrEqualThan=" + UPDATED_SITE_ID);
    }

    @Test
    @Transactional
    public void getAllRolesBySiteIdIsLessThanSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where siteId less than or equals to DEFAULT_SITE_ID
        defaultRoleShouldNotBeFound("siteId.lessThan=" + DEFAULT_SITE_ID);

        // Get all the roleList where siteId less than or equals to UPDATED_SITE_ID
        defaultRoleShouldBeFound("siteId.lessThan=" + UPDATED_SITE_ID);
    }


    @Test
    @Transactional
    public void getAllRolesByClientTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where clientType equals to DEFAULT_CLIENT_TYPE
        defaultRoleShouldBeFound("clientType.equals=" + DEFAULT_CLIENT_TYPE);

        // Get all the roleList where clientType equals to UPDATED_CLIENT_TYPE
        defaultRoleShouldNotBeFound("clientType.equals=" + UPDATED_CLIENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllRolesByClientTypeIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where clientType in DEFAULT_CLIENT_TYPE or UPDATED_CLIENT_TYPE
        defaultRoleShouldBeFound("clientType.in=" + DEFAULT_CLIENT_TYPE + "," + UPDATED_CLIENT_TYPE);

        // Get all the roleList where clientType equals to UPDATED_CLIENT_TYPE
        defaultRoleShouldNotBeFound("clientType.in=" + UPDATED_CLIENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllRolesByClientTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where clientType is not null
        defaultRoleShouldBeFound("clientType.specified=true");

        // Get all the roleList where clientType is null
        defaultRoleShouldNotBeFound("clientType.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where name equals to DEFAULT_NAME
        defaultRoleShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the roleList where name equals to UPDATED_NAME
        defaultRoleShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRoleShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the roleList where name equals to UPDATED_NAME
        defaultRoleShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where name is not null
        defaultRoleShouldBeFound("name.specified=true");

        // Get all the roleList where name is null
        defaultRoleShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where remark equals to DEFAULT_REMARK
        defaultRoleShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the roleList where remark equals to UPDATED_REMARK
        defaultRoleShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllRolesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultRoleShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the roleList where remark equals to UPDATED_REMARK
        defaultRoleShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllRolesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where remark is not null
        defaultRoleShouldBeFound("remark.specified=true");

        // Get all the roleList where remark is null
        defaultRoleShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByExtmapIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where extmap equals to DEFAULT_EXTMAP
        defaultRoleShouldBeFound("extmap.equals=" + DEFAULT_EXTMAP);

        // Get all the roleList where extmap equals to UPDATED_EXTMAP
        defaultRoleShouldNotBeFound("extmap.equals=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllRolesByExtmapIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where extmap in DEFAULT_EXTMAP or UPDATED_EXTMAP
        defaultRoleShouldBeFound("extmap.in=" + DEFAULT_EXTMAP + "," + UPDATED_EXTMAP);

        // Get all the roleList where extmap equals to UPDATED_EXTMAP
        defaultRoleShouldNotBeFound("extmap.in=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllRolesByExtmapIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where extmap is not null
        defaultRoleShouldBeFound("extmap.specified=true");

        // Get all the roleList where extmap is null
        defaultRoleShouldNotBeFound("extmap.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByRoleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleType equals to DEFAULT_ROLE_TYPE
        defaultRoleShouldBeFound("roleType.equals=" + DEFAULT_ROLE_TYPE);

        // Get all the roleList where roleType equals to UPDATED_ROLE_TYPE
        defaultRoleShouldNotBeFound("roleType.equals=" + UPDATED_ROLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleTypeIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleType in DEFAULT_ROLE_TYPE or UPDATED_ROLE_TYPE
        defaultRoleShouldBeFound("roleType.in=" + DEFAULT_ROLE_TYPE + "," + UPDATED_ROLE_TYPE);

        // Get all the roleList where roleType equals to UPDATED_ROLE_TYPE
        defaultRoleShouldNotBeFound("roleType.in=" + UPDATED_ROLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleType is not null
        defaultRoleShouldBeFound("roleType.specified=true");

        // Get all the roleList where roleType is null
        defaultRoleShouldNotBeFound("roleType.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByAuthIsEqualToSomething() throws Exception {
        // Initialize the database
        Auth auth = AuthResourceIntTest.createEntity(em);
        em.persist(auth);
        em.flush();
        role.addAuth(auth);
        roleRepository.saveAndFlush(role);
        Long authId = auth.getId();

        // Get all the roleList where auth equals to authId
        defaultRoleShouldBeFound("authId.equals=" + authId);

        // Get all the roleList where auth equals to authId + 1
        defaultRoleShouldNotBeFound("authId.equals=" + (authId + 1));
    }


    @Test
    @Transactional
    public void getAllRolesByMenuIsEqualToSomething() throws Exception {
        // Initialize the database
        Menu menu = MenuResourceIntTest.createEntity(em);
        em.persist(menu);
        em.flush();
        role.addMenu(menu);
        roleRepository.saveAndFlush(role);
        Long menuId = menu.getId();

        // Get all the roleList where menu equals to menuId
        defaultRoleShouldBeFound("menuId.equals=" + menuId);

        // Get all the roleList where menu equals to menuId + 1
        defaultRoleShouldNotBeFound("menuId.equals=" + (menuId + 1));
    }


    @Test
    @Transactional
    public void getAllRolesByProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        Profile profile = ProfileResourceIntTest.createEntity(em);
        em.persist(profile);
        em.flush();
        role.addProfile(profile);
        roleRepository.saveAndFlush(role);
        Long profileId = profile.getId();

        // Get all the roleList where profile equals to profileId
        defaultRoleShouldBeFound("profileId.equals=" + profileId);

        // Get all the roleList where profile equals to profileId + 1
        defaultRoleShouldNotBeFound("profileId.equals=" + (profileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRoleShouldBeFound(String filter) throws Exception {
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(role.getId().intValue())))
            .andExpect(jsonPath("$.[*].spaceId").value(hasItem(DEFAULT_SPACE_ID)))
            .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID)))
            .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP)))
            .andExpect(jsonPath("$.[*].roleType").value(hasItem(DEFAULT_ROLE_TYPE.toString())));

        // Check, that the count call also returns 1
        restRoleMockMvc.perform(get("/api/roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRoleShouldNotBeFound(String filter) throws Exception {
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoleMockMvc.perform(get("/api/roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRole() throws Exception {
        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Update the role
        Role updatedRole = roleRepository.findById(role.getId()).get();
        // Disconnect from session so that the updates on updatedRole are not directly saved in db
        em.detach(updatedRole);
        updatedRole
            .spaceId(UPDATED_SPACE_ID)
            .siteId(UPDATED_SITE_ID)
            .clientType(UPDATED_CLIENT_TYPE)
            .name(UPDATED_NAME)
            .remark(UPDATED_REMARK)
            .extmap(UPDATED_EXTMAP)
            .roleType(UPDATED_ROLE_TYPE);
        RoleDTO roleDTO = roleMapper.toDto(updatedRole);

        restRoleMockMvc.perform(put("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isOk());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getSpaceId()).isEqualTo(UPDATED_SPACE_ID);
        assertThat(testRole.getSiteId()).isEqualTo(UPDATED_SITE_ID);
        assertThat(testRole.getClientType()).isEqualTo(UPDATED_CLIENT_TYPE);
        assertThat(testRole.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRole.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testRole.getExtmap()).isEqualTo(UPDATED_EXTMAP);
        assertThat(testRole.getRoleType()).isEqualTo(UPDATED_ROLE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingRole() throws Exception {
        int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleMockMvc.perform(put("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeDelete = roleRepository.findAll().size();

        // Delete the role
        restRoleMockMvc.perform(delete("/api/roles/{id}", role.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Role.class);
        Role role1 = new Role();
        role1.setId(1L);
        Role role2 = new Role();
        role2.setId(role1.getId());
        assertThat(role1).isEqualTo(role2);
        role2.setId(2L);
        assertThat(role1).isNotEqualTo(role2);
        role1.setId(null);
        assertThat(role1).isNotEqualTo(role2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleDTO.class);
        RoleDTO roleDTO1 = new RoleDTO();
        roleDTO1.setId(1L);
        RoleDTO roleDTO2 = new RoleDTO();
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
        roleDTO2.setId(roleDTO1.getId());
        assertThat(roleDTO1).isEqualTo(roleDTO2);
        roleDTO2.setId(2L);
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
        roleDTO1.setId(null);
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(roleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(roleMapper.fromId(null)).isNull();
    }
}
