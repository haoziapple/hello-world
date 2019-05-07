package com.github.haozi.web.rest;

import com.github.haozi.XauthApp;

import com.github.haozi.domain.Workspace;
import com.github.haozi.domain.Workspace;
import com.github.haozi.domain.Site;
import com.github.haozi.repository.WorkspaceRepository;
import com.github.haozi.service.WorkspaceService;
import com.github.haozi.service.dto.WorkspaceDTO;
import com.github.haozi.service.mapper.WorkspaceMapper;
import com.github.haozi.web.rest.errors.ExceptionTranslator;
import com.github.haozi.service.dto.WorkspaceCriteria;
import com.github.haozi.service.WorkspaceQueryService;

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
 * Test class for the WorkspaceResource REST controller.
 *
 * @see WorkspaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XauthApp.class)
public class WorkspaceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_EXTMAP = "AAAAAAAAAA";
    private static final String UPDATED_EXTMAP = "BBBBBBBBBB";

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Mock
    private WorkspaceRepository workspaceRepositoryMock;

    @Autowired
    private WorkspaceMapper workspaceMapper;

    @Mock
    private WorkspaceService workspaceServiceMock;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private WorkspaceQueryService workspaceQueryService;

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

    private MockMvc restWorkspaceMockMvc;

    private Workspace workspace;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkspaceResource workspaceResource = new WorkspaceResource(workspaceService, workspaceQueryService);
        this.restWorkspaceMockMvc = MockMvcBuilders.standaloneSetup(workspaceResource)
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
    public static Workspace createEntity(EntityManager em) {
        Workspace workspace = new Workspace()
            .name(DEFAULT_NAME)
            .remark(DEFAULT_REMARK)
            .extmap(DEFAULT_EXTMAP);
        return workspace;
    }

    @Before
    public void initTest() {
        workspace = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkspace() throws Exception {
        int databaseSizeBeforeCreate = workspaceRepository.findAll().size();

        // Create the Workspace
        WorkspaceDTO workspaceDTO = workspaceMapper.toDto(workspace);
        restWorkspaceMockMvc.perform(post("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceDTO)))
            .andExpect(status().isCreated());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeCreate + 1);
        Workspace testWorkspace = workspaceList.get(workspaceList.size() - 1);
        assertThat(testWorkspace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkspace.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testWorkspace.getExtmap()).isEqualTo(DEFAULT_EXTMAP);
    }

    @Test
    @Transactional
    public void createWorkspaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workspaceRepository.findAll().size();

        // Create the Workspace with an existing ID
        workspace.setId(1L);
        WorkspaceDTO workspaceDTO = workspaceMapper.toDto(workspace);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkspaceMockMvc.perform(post("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorkspaces() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList
        restWorkspaceMockMvc.perform(get("/api/workspaces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workspace.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllWorkspacesWithEagerRelationshipsIsEnabled() throws Exception {
        WorkspaceResource workspaceResource = new WorkspaceResource(workspaceServiceMock, workspaceQueryService);
        when(workspaceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restWorkspaceMockMvc = MockMvcBuilders.standaloneSetup(workspaceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWorkspaceMockMvc.perform(get("/api/workspaces?eagerload=true"))
        .andExpect(status().isOk());

        verify(workspaceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllWorkspacesWithEagerRelationshipsIsNotEnabled() throws Exception {
        WorkspaceResource workspaceResource = new WorkspaceResource(workspaceServiceMock, workspaceQueryService);
            when(workspaceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restWorkspaceMockMvc = MockMvcBuilders.standaloneSetup(workspaceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWorkspaceMockMvc.perform(get("/api/workspaces?eagerload=true"))
        .andExpect(status().isOk());

            verify(workspaceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getWorkspace() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get the workspace
        restWorkspaceMockMvc.perform(get("/api/workspaces/{id}", workspace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workspace.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.extmap").value(DEFAULT_EXTMAP.toString()));
    }

    @Test
    @Transactional
    public void getAllWorkspacesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList where name equals to DEFAULT_NAME
        defaultWorkspaceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the workspaceList where name equals to UPDATED_NAME
        defaultWorkspaceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWorkspacesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultWorkspaceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the workspaceList where name equals to UPDATED_NAME
        defaultWorkspaceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWorkspacesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList where name is not null
        defaultWorkspaceShouldBeFound("name.specified=true");

        // Get all the workspaceList where name is null
        defaultWorkspaceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkspacesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList where remark equals to DEFAULT_REMARK
        defaultWorkspaceShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the workspaceList where remark equals to UPDATED_REMARK
        defaultWorkspaceShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllWorkspacesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultWorkspaceShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the workspaceList where remark equals to UPDATED_REMARK
        defaultWorkspaceShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllWorkspacesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList where remark is not null
        defaultWorkspaceShouldBeFound("remark.specified=true");

        // Get all the workspaceList where remark is null
        defaultWorkspaceShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkspacesByExtmapIsEqualToSomething() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList where extmap equals to DEFAULT_EXTMAP
        defaultWorkspaceShouldBeFound("extmap.equals=" + DEFAULT_EXTMAP);

        // Get all the workspaceList where extmap equals to UPDATED_EXTMAP
        defaultWorkspaceShouldNotBeFound("extmap.equals=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllWorkspacesByExtmapIsInShouldWork() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList where extmap in DEFAULT_EXTMAP or UPDATED_EXTMAP
        defaultWorkspaceShouldBeFound("extmap.in=" + DEFAULT_EXTMAP + "," + UPDATED_EXTMAP);

        // Get all the workspaceList where extmap equals to UPDATED_EXTMAP
        defaultWorkspaceShouldNotBeFound("extmap.in=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllWorkspacesByExtmapIsNullOrNotNull() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList where extmap is not null
        defaultWorkspaceShouldBeFound("extmap.specified=true");

        // Get all the workspaceList where extmap is null
        defaultWorkspaceShouldNotBeFound("extmap.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkspacesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        Workspace parent = WorkspaceResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        workspace.setParent(parent);
        workspaceRepository.saveAndFlush(workspace);
        Long parentId = parent.getId();

        // Get all the workspaceList where parent equals to parentId
        defaultWorkspaceShouldBeFound("parentId.equals=" + parentId);

        // Get all the workspaceList where parent equals to parentId + 1
        defaultWorkspaceShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkspacesBySiteIsEqualToSomething() throws Exception {
        // Initialize the database
        Site site = SiteResourceIntTest.createEntity(em);
        em.persist(site);
        em.flush();
        workspace.addSite(site);
        workspaceRepository.saveAndFlush(workspace);
        Long siteId = site.getId();

        // Get all the workspaceList where site equals to siteId
        defaultWorkspaceShouldBeFound("siteId.equals=" + siteId);

        // Get all the workspaceList where site equals to siteId + 1
        defaultWorkspaceShouldNotBeFound("siteId.equals=" + (siteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultWorkspaceShouldBeFound(String filter) throws Exception {
        restWorkspaceMockMvc.perform(get("/api/workspaces?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workspace.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP)));

        // Check, that the count call also returns 1
        restWorkspaceMockMvc.perform(get("/api/workspaces/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultWorkspaceShouldNotBeFound(String filter) throws Exception {
        restWorkspaceMockMvc.perform(get("/api/workspaces?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkspaceMockMvc.perform(get("/api/workspaces/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWorkspace() throws Exception {
        // Get the workspace
        restWorkspaceMockMvc.perform(get("/api/workspaces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkspace() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();

        // Update the workspace
        Workspace updatedWorkspace = workspaceRepository.findById(workspace.getId()).get();
        // Disconnect from session so that the updates on updatedWorkspace are not directly saved in db
        em.detach(updatedWorkspace);
        updatedWorkspace
            .name(UPDATED_NAME)
            .remark(UPDATED_REMARK)
            .extmap(UPDATED_EXTMAP);
        WorkspaceDTO workspaceDTO = workspaceMapper.toDto(updatedWorkspace);

        restWorkspaceMockMvc.perform(put("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceDTO)))
            .andExpect(status().isOk());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
        Workspace testWorkspace = workspaceList.get(workspaceList.size() - 1);
        assertThat(testWorkspace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkspace.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testWorkspace.getExtmap()).isEqualTo(UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkspace() throws Exception {
        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();

        // Create the Workspace
        WorkspaceDTO workspaceDTO = workspaceMapper.toDto(workspace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkspaceMockMvc.perform(put("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkspace() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        int databaseSizeBeforeDelete = workspaceRepository.findAll().size();

        // Delete the workspace
        restWorkspaceMockMvc.perform(delete("/api/workspaces/{id}", workspace.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Workspace.class);
        Workspace workspace1 = new Workspace();
        workspace1.setId(1L);
        Workspace workspace2 = new Workspace();
        workspace2.setId(workspace1.getId());
        assertThat(workspace1).isEqualTo(workspace2);
        workspace2.setId(2L);
        assertThat(workspace1).isNotEqualTo(workspace2);
        workspace1.setId(null);
        assertThat(workspace1).isNotEqualTo(workspace2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkspaceDTO.class);
        WorkspaceDTO workspaceDTO1 = new WorkspaceDTO();
        workspaceDTO1.setId(1L);
        WorkspaceDTO workspaceDTO2 = new WorkspaceDTO();
        assertThat(workspaceDTO1).isNotEqualTo(workspaceDTO2);
        workspaceDTO2.setId(workspaceDTO1.getId());
        assertThat(workspaceDTO1).isEqualTo(workspaceDTO2);
        workspaceDTO2.setId(2L);
        assertThat(workspaceDTO1).isNotEqualTo(workspaceDTO2);
        workspaceDTO1.setId(null);
        assertThat(workspaceDTO1).isNotEqualTo(workspaceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workspaceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workspaceMapper.fromId(null)).isNull();
    }
}
