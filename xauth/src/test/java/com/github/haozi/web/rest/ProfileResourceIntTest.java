package com.github.haozi.web.rest;

import com.github.haozi.XauthApp;

import com.github.haozi.domain.Profile;
import com.github.haozi.domain.Workspace;
import com.github.haozi.domain.Department;
import com.github.haozi.domain.Role;
import com.github.haozi.repository.ProfileRepository;
import com.github.haozi.service.ProfileService;
import com.github.haozi.service.dto.ProfileDTO;
import com.github.haozi.service.mapper.ProfileMapper;
import com.github.haozi.web.rest.errors.ExceptionTranslator;
import com.github.haozi.service.dto.ProfileCriteria;
import com.github.haozi.service.ProfileQueryService;

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

import com.github.haozi.domain.enumeration.Sex;
/**
 * Test class for the ProfileResource REST controller.
 *
 * @see ProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XauthApp.class)
public class ProfileResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Sex DEFAULT_SEX = Sex.MALE;
    private static final Sex UPDATED_SEX = Sex.FEMALE;

    private static final Boolean DEFAULT_LOCKED = false;
    private static final Boolean UPDATED_LOCKED = true;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_EXT_MAP = "AAAAAAAAAA";
    private static final String UPDATED_EXT_MAP = "BBBBBBBBBB";

    @Autowired
    private ProfileRepository profileRepository;

    @Mock
    private ProfileRepository profileRepositoryMock;

    @Autowired
    private ProfileMapper profileMapper;

    @Mock
    private ProfileService profileServiceMock;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileQueryService profileQueryService;

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

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileResource profileResource = new ProfileResource(profileService, profileQueryService);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
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
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .name(DEFAULT_NAME)
            .loginName(DEFAULT_LOGIN_NAME)
            .password(DEFAULT_PASSWORD)
            .mobile(DEFAULT_MOBILE)
            .email(DEFAULT_EMAIL)
            .sex(DEFAULT_SEX)
            .locked(DEFAULT_LOCKED)
            .remark(DEFAULT_REMARK)
            .extMap(DEFAULT_EXT_MAP);
        return profile;
    }

    @Before
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProfile.getLoginName()).isEqualTo(DEFAULT_LOGIN_NAME);
        assertThat(testProfile.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testProfile.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfile.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testProfile.isLocked()).isEqualTo(DEFAULT_LOCKED);
        assertThat(testProfile.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testProfile.getExtMap()).isEqualTo(DEFAULT_EXT_MAP);
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].loginName").value(hasItem(DEFAULT_LOGIN_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].extMap").value(hasItem(DEFAULT_EXT_MAP.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProfilesWithEagerRelationshipsIsEnabled() throws Exception {
        ProfileResource profileResource = new ProfileResource(profileServiceMock, profileQueryService);
        when(profileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProfileMockMvc.perform(get("/api/profiles?eagerload=true"))
        .andExpect(status().isOk());

        verify(profileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProfilesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProfileResource profileResource = new ProfileResource(profileServiceMock, profileQueryService);
            when(profileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProfileMockMvc.perform(get("/api/profiles?eagerload=true"))
        .andExpect(status().isOk());

            verify(profileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.loginName").value(DEFAULT_LOGIN_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.locked").value(DEFAULT_LOCKED.booleanValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.extMap").value(DEFAULT_EXT_MAP.toString()));
    }

    @Test
    @Transactional
    public void getAllProfilesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where name equals to DEFAULT_NAME
        defaultProfileShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the profileList where name equals to UPDATED_NAME
        defaultProfileShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProfileShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the profileList where name equals to UPDATED_NAME
        defaultProfileShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where name is not null
        defaultProfileShouldBeFound("name.specified=true");

        // Get all the profileList where name is null
        defaultProfileShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLoginNameIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where loginName equals to DEFAULT_LOGIN_NAME
        defaultProfileShouldBeFound("loginName.equals=" + DEFAULT_LOGIN_NAME);

        // Get all the profileList where loginName equals to UPDATED_LOGIN_NAME
        defaultProfileShouldNotBeFound("loginName.equals=" + UPDATED_LOGIN_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByLoginNameIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where loginName in DEFAULT_LOGIN_NAME or UPDATED_LOGIN_NAME
        defaultProfileShouldBeFound("loginName.in=" + DEFAULT_LOGIN_NAME + "," + UPDATED_LOGIN_NAME);

        // Get all the profileList where loginName equals to UPDATED_LOGIN_NAME
        defaultProfileShouldNotBeFound("loginName.in=" + UPDATED_LOGIN_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByLoginNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where loginName is not null
        defaultProfileShouldBeFound("loginName.specified=true");

        // Get all the profileList where loginName is null
        defaultProfileShouldNotBeFound("loginName.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where password equals to DEFAULT_PASSWORD
        defaultProfileShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the profileList where password equals to UPDATED_PASSWORD
        defaultProfileShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllProfilesByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultProfileShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the profileList where password equals to UPDATED_PASSWORD
        defaultProfileShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllProfilesByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where password is not null
        defaultProfileShouldBeFound("password.specified=true");

        // Get all the profileList where password is null
        defaultProfileShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where mobile equals to DEFAULT_MOBILE
        defaultProfileShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the profileList where mobile equals to UPDATED_MOBILE
        defaultProfileShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllProfilesByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultProfileShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the profileList where mobile equals to UPDATED_MOBILE
        defaultProfileShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllProfilesByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where mobile is not null
        defaultProfileShouldBeFound("mobile.specified=true");

        // Get all the profileList where mobile is null
        defaultProfileShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where email equals to DEFAULT_EMAIL
        defaultProfileShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the profileList where email equals to UPDATED_EMAIL
        defaultProfileShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllProfilesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultProfileShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the profileList where email equals to UPDATED_EMAIL
        defaultProfileShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllProfilesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where email is not null
        defaultProfileShouldBeFound("email.specified=true");

        // Get all the profileList where email is null
        defaultProfileShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesBySexIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where sex equals to DEFAULT_SEX
        defaultProfileShouldBeFound("sex.equals=" + DEFAULT_SEX);

        // Get all the profileList where sex equals to UPDATED_SEX
        defaultProfileShouldNotBeFound("sex.equals=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllProfilesBySexIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where sex in DEFAULT_SEX or UPDATED_SEX
        defaultProfileShouldBeFound("sex.in=" + DEFAULT_SEX + "," + UPDATED_SEX);

        // Get all the profileList where sex equals to UPDATED_SEX
        defaultProfileShouldNotBeFound("sex.in=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllProfilesBySexIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where sex is not null
        defaultProfileShouldBeFound("sex.specified=true");

        // Get all the profileList where sex is null
        defaultProfileShouldNotBeFound("sex.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLockedIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locked equals to DEFAULT_LOCKED
        defaultProfileShouldBeFound("locked.equals=" + DEFAULT_LOCKED);

        // Get all the profileList where locked equals to UPDATED_LOCKED
        defaultProfileShouldNotBeFound("locked.equals=" + UPDATED_LOCKED);
    }

    @Test
    @Transactional
    public void getAllProfilesByLockedIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locked in DEFAULT_LOCKED or UPDATED_LOCKED
        defaultProfileShouldBeFound("locked.in=" + DEFAULT_LOCKED + "," + UPDATED_LOCKED);

        // Get all the profileList where locked equals to UPDATED_LOCKED
        defaultProfileShouldNotBeFound("locked.in=" + UPDATED_LOCKED);
    }

    @Test
    @Transactional
    public void getAllProfilesByLockedIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where locked is not null
        defaultProfileShouldBeFound("locked.specified=true");

        // Get all the profileList where locked is null
        defaultProfileShouldNotBeFound("locked.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where remark equals to DEFAULT_REMARK
        defaultProfileShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the profileList where remark equals to UPDATED_REMARK
        defaultProfileShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllProfilesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultProfileShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the profileList where remark equals to UPDATED_REMARK
        defaultProfileShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllProfilesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where remark is not null
        defaultProfileShouldBeFound("remark.specified=true");

        // Get all the profileList where remark is null
        defaultProfileShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByExtMapIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where extMap equals to DEFAULT_EXT_MAP
        defaultProfileShouldBeFound("extMap.equals=" + DEFAULT_EXT_MAP);

        // Get all the profileList where extMap equals to UPDATED_EXT_MAP
        defaultProfileShouldNotBeFound("extMap.equals=" + UPDATED_EXT_MAP);
    }

    @Test
    @Transactional
    public void getAllProfilesByExtMapIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where extMap in DEFAULT_EXT_MAP or UPDATED_EXT_MAP
        defaultProfileShouldBeFound("extMap.in=" + DEFAULT_EXT_MAP + "," + UPDATED_EXT_MAP);

        // Get all the profileList where extMap equals to UPDATED_EXT_MAP
        defaultProfileShouldNotBeFound("extMap.in=" + UPDATED_EXT_MAP);
    }

    @Test
    @Transactional
    public void getAllProfilesByExtMapIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where extMap is not null
        defaultProfileShouldBeFound("extMap.specified=true");

        // Get all the profileList where extMap is null
        defaultProfileShouldNotBeFound("extMap.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByWorkspaceIsEqualToSomething() throws Exception {
        // Initialize the database
        Workspace workspace = WorkspaceResourceIntTest.createEntity(em);
        em.persist(workspace);
        em.flush();
        profile.setWorkspace(workspace);
        profileRepository.saveAndFlush(profile);
        Long workspaceId = workspace.getId();

        // Get all the profileList where workspace equals to workspaceId
        defaultProfileShouldBeFound("workspaceId.equals=" + workspaceId);

        // Get all the profileList where workspace equals to workspaceId + 1
        defaultProfileShouldNotBeFound("workspaceId.equals=" + (workspaceId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        Department department = DepartmentResourceIntTest.createEntity(em);
        em.persist(department);
        em.flush();
        profile.addDepartment(department);
        profileRepository.saveAndFlush(profile);
        Long departmentId = department.getId();

        // Get all the profileList where department equals to departmentId
        defaultProfileShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the profileList where department equals to departmentId + 1
        defaultProfileShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilesByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        Role role = RoleResourceIntTest.createEntity(em);
        em.persist(role);
        em.flush();
        profile.addRole(role);
        profileRepository.saveAndFlush(profile);
        Long roleId = role.getId();

        // Get all the profileList where role equals to roleId
        defaultProfileShouldBeFound("roleId.equals=" + roleId);

        // Get all the profileList where role equals to roleId + 1
        defaultProfileShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProfileShouldBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].loginName").value(hasItem(DEFAULT_LOGIN_NAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].extMap").value(hasItem(DEFAULT_EXT_MAP)));

        // Check, that the count call also returns 1
        restProfileMockMvc.perform(get("/api/profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProfileShouldNotBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProfileMockMvc.perform(get("/api/profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .name(UPDATED_NAME)
            .loginName(UPDATED_LOGIN_NAME)
            .password(UPDATED_PASSWORD)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .sex(UPDATED_SEX)
            .locked(UPDATED_LOCKED)
            .remark(UPDATED_REMARK)
            .extMap(UPDATED_EXT_MAP);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfile.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
        assertThat(testProfile.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testProfile.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfile.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testProfile.isLocked()).isEqualTo(UPDATED_LOCKED);
        assertThat(testProfile.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testProfile.getExtMap()).isEqualTo(UPDATED_EXT_MAP);
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profile.class);
        Profile profile1 = new Profile();
        profile1.setId(1L);
        Profile profile2 = new Profile();
        profile2.setId(profile1.getId());
        assertThat(profile1).isEqualTo(profile2);
        profile2.setId(2L);
        assertThat(profile1).isNotEqualTo(profile2);
        profile1.setId(null);
        assertThat(profile1).isNotEqualTo(profile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileDTO.class);
        ProfileDTO profileDTO1 = new ProfileDTO();
        profileDTO1.setId(1L);
        ProfileDTO profileDTO2 = new ProfileDTO();
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
        profileDTO2.setId(profileDTO1.getId());
        assertThat(profileDTO1).isEqualTo(profileDTO2);
        profileDTO2.setId(2L);
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
        profileDTO1.setId(null);
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profileMapper.fromId(null)).isNull();
    }
}
