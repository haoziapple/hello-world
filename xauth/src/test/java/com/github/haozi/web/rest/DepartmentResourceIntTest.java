package com.github.haozi.web.rest;

import com.github.haozi.XauthApp;

import com.github.haozi.domain.Department;
import com.github.haozi.domain.Workspace;
import com.github.haozi.domain.Department;
import com.github.haozi.domain.Profile;
import com.github.haozi.repository.DepartmentRepository;
import com.github.haozi.service.DepartmentService;
import com.github.haozi.service.dto.DepartmentDTO;
import com.github.haozi.service.mapper.DepartmentMapper;
import com.github.haozi.web.rest.errors.ExceptionTranslator;
import com.github.haozi.service.dto.DepartmentCriteria;
import com.github.haozi.service.DepartmentQueryService;

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
 * Test class for the DepartmentResource REST controller.
 *
 * @see DepartmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XauthApp.class)
public class DepartmentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final Boolean DEFAULT_LEAF = false;
    private static final Boolean UPDATED_LEAF = true;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_EXT_MAP = "AAAAAAAAAA";
    private static final String UPDATED_EXT_MAP = "BBBBBBBBBB";

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentQueryService departmentQueryService;

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

    private MockMvc restDepartmentMockMvc;

    private Department department;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartmentResource departmentResource = new DepartmentResource(departmentService, departmentQueryService);
        this.restDepartmentMockMvc = MockMvcBuilders.standaloneSetup(departmentResource)
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
    public static Department createEntity(EntityManager em) {
        Department department = new Department()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .seq(DEFAULT_SEQ)
            .leaf(DEFAULT_LEAF)
            .remark(DEFAULT_REMARK)
            .extMap(DEFAULT_EXT_MAP);
        return department;
    }

    @Before
    public void initTest() {
        department = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartment() throws Exception {
        int databaseSizeBeforeCreate = departmentRepository.findAll().size();

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);
        restDepartmentMockMvc.perform(post("/api/departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeCreate + 1);
        Department testDepartment = departmentList.get(departmentList.size() - 1);
        assertThat(testDepartment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDepartment.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDepartment.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testDepartment.isLeaf()).isEqualTo(DEFAULT_LEAF);
        assertThat(testDepartment.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testDepartment.getExtMap()).isEqualTo(DEFAULT_EXT_MAP);
    }

    @Test
    @Transactional
    public void createDepartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departmentRepository.findAll().size();

        // Create the Department with an existing ID
        department.setId(1L);
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentMockMvc.perform(post("/api/departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDepartments() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList
        restDepartmentMockMvc.perform(get("/api/departments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(department.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].extMap").value(hasItem(DEFAULT_EXT_MAP.toString())));
    }
    
    @Test
    @Transactional
    public void getDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get the department
        restDepartmentMockMvc.perform(get("/api/departments/{id}", department.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(department.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.leaf").value(DEFAULT_LEAF.booleanValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.extMap").value(DEFAULT_EXT_MAP.toString()));
    }

    @Test
    @Transactional
    public void getAllDepartmentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name equals to DEFAULT_NAME
        defaultDepartmentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the departmentList where name equals to UPDATED_NAME
        defaultDepartmentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDepartmentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the departmentList where name equals to UPDATED_NAME
        defaultDepartmentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name is not null
        defaultDepartmentShouldBeFound("name.specified=true");

        // Get all the departmentList where name is null
        defaultDepartmentShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepartmentsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where code equals to DEFAULT_CODE
        defaultDepartmentShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the departmentList where code equals to UPDATED_CODE
        defaultDepartmentShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where code in DEFAULT_CODE or UPDATED_CODE
        defaultDepartmentShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the departmentList where code equals to UPDATED_CODE
        defaultDepartmentShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where code is not null
        defaultDepartmentShouldBeFound("code.specified=true");

        // Get all the departmentList where code is null
        defaultDepartmentShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepartmentsBySeqIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where seq equals to DEFAULT_SEQ
        defaultDepartmentShouldBeFound("seq.equals=" + DEFAULT_SEQ);

        // Get all the departmentList where seq equals to UPDATED_SEQ
        defaultDepartmentShouldNotBeFound("seq.equals=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    public void getAllDepartmentsBySeqIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where seq in DEFAULT_SEQ or UPDATED_SEQ
        defaultDepartmentShouldBeFound("seq.in=" + DEFAULT_SEQ + "," + UPDATED_SEQ);

        // Get all the departmentList where seq equals to UPDATED_SEQ
        defaultDepartmentShouldNotBeFound("seq.in=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    public void getAllDepartmentsBySeqIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where seq is not null
        defaultDepartmentShouldBeFound("seq.specified=true");

        // Get all the departmentList where seq is null
        defaultDepartmentShouldNotBeFound("seq.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepartmentsBySeqIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where seq greater than or equals to DEFAULT_SEQ
        defaultDepartmentShouldBeFound("seq.greaterOrEqualThan=" + DEFAULT_SEQ);

        // Get all the departmentList where seq greater than or equals to UPDATED_SEQ
        defaultDepartmentShouldNotBeFound("seq.greaterOrEqualThan=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    public void getAllDepartmentsBySeqIsLessThanSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where seq less than or equals to DEFAULT_SEQ
        defaultDepartmentShouldNotBeFound("seq.lessThan=" + DEFAULT_SEQ);

        // Get all the departmentList where seq less than or equals to UPDATED_SEQ
        defaultDepartmentShouldBeFound("seq.lessThan=" + UPDATED_SEQ);
    }


    @Test
    @Transactional
    public void getAllDepartmentsByLeafIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where leaf equals to DEFAULT_LEAF
        defaultDepartmentShouldBeFound("leaf.equals=" + DEFAULT_LEAF);

        // Get all the departmentList where leaf equals to UPDATED_LEAF
        defaultDepartmentShouldNotBeFound("leaf.equals=" + UPDATED_LEAF);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByLeafIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where leaf in DEFAULT_LEAF or UPDATED_LEAF
        defaultDepartmentShouldBeFound("leaf.in=" + DEFAULT_LEAF + "," + UPDATED_LEAF);

        // Get all the departmentList where leaf equals to UPDATED_LEAF
        defaultDepartmentShouldNotBeFound("leaf.in=" + UPDATED_LEAF);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByLeafIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where leaf is not null
        defaultDepartmentShouldBeFound("leaf.specified=true");

        // Get all the departmentList where leaf is null
        defaultDepartmentShouldNotBeFound("leaf.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepartmentsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where remark equals to DEFAULT_REMARK
        defaultDepartmentShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the departmentList where remark equals to UPDATED_REMARK
        defaultDepartmentShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultDepartmentShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the departmentList where remark equals to UPDATED_REMARK
        defaultDepartmentShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where remark is not null
        defaultDepartmentShouldBeFound("remark.specified=true");

        // Get all the departmentList where remark is null
        defaultDepartmentShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepartmentsByExtMapIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where extMap equals to DEFAULT_EXT_MAP
        defaultDepartmentShouldBeFound("extMap.equals=" + DEFAULT_EXT_MAP);

        // Get all the departmentList where extMap equals to UPDATED_EXT_MAP
        defaultDepartmentShouldNotBeFound("extMap.equals=" + UPDATED_EXT_MAP);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByExtMapIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where extMap in DEFAULT_EXT_MAP or UPDATED_EXT_MAP
        defaultDepartmentShouldBeFound("extMap.in=" + DEFAULT_EXT_MAP + "," + UPDATED_EXT_MAP);

        // Get all the departmentList where extMap equals to UPDATED_EXT_MAP
        defaultDepartmentShouldNotBeFound("extMap.in=" + UPDATED_EXT_MAP);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByExtMapIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where extMap is not null
        defaultDepartmentShouldBeFound("extMap.specified=true");

        // Get all the departmentList where extMap is null
        defaultDepartmentShouldNotBeFound("extMap.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepartmentsByWorkspaceIsEqualToSomething() throws Exception {
        // Initialize the database
        Workspace workspace = WorkspaceResourceIntTest.createEntity(em);
        em.persist(workspace);
        em.flush();
        department.setWorkspace(workspace);
        departmentRepository.saveAndFlush(department);
        Long workspaceId = workspace.getId();

        // Get all the departmentList where workspace equals to workspaceId
        defaultDepartmentShouldBeFound("workspaceId.equals=" + workspaceId);

        // Get all the departmentList where workspace equals to workspaceId + 1
        defaultDepartmentShouldNotBeFound("workspaceId.equals=" + (workspaceId + 1));
    }


    @Test
    @Transactional
    public void getAllDepartmentsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        Department parent = DepartmentResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        department.setParent(parent);
        departmentRepository.saveAndFlush(department);
        Long parentId = parent.getId();

        // Get all the departmentList where parent equals to parentId
        defaultDepartmentShouldBeFound("parentId.equals=" + parentId);

        // Get all the departmentList where parent equals to parentId + 1
        defaultDepartmentShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllDepartmentsByProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        Profile profile = ProfileResourceIntTest.createEntity(em);
        em.persist(profile);
        em.flush();
        department.addProfile(profile);
        departmentRepository.saveAndFlush(department);
        Long profileId = profile.getId();

        // Get all the departmentList where profile equals to profileId
        defaultDepartmentShouldBeFound("profileId.equals=" + profileId);

        // Get all the departmentList where profile equals to profileId + 1
        defaultDepartmentShouldNotBeFound("profileId.equals=" + (profileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDepartmentShouldBeFound(String filter) throws Exception {
        restDepartmentMockMvc.perform(get("/api/departments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(department.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].extMap").value(hasItem(DEFAULT_EXT_MAP)));

        // Check, that the count call also returns 1
        restDepartmentMockMvc.perform(get("/api/departments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDepartmentShouldNotBeFound(String filter) throws Exception {
        restDepartmentMockMvc.perform(get("/api/departments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartmentMockMvc.perform(get("/api/departments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDepartment() throws Exception {
        // Get the department
        restDepartmentMockMvc.perform(get("/api/departments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();

        // Update the department
        Department updatedDepartment = departmentRepository.findById(department.getId()).get();
        // Disconnect from session so that the updates on updatedDepartment are not directly saved in db
        em.detach(updatedDepartment);
        updatedDepartment
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .seq(UPDATED_SEQ)
            .leaf(UPDATED_LEAF)
            .remark(UPDATED_REMARK)
            .extMap(UPDATED_EXT_MAP);
        DepartmentDTO departmentDTO = departmentMapper.toDto(updatedDepartment);

        restDepartmentMockMvc.perform(put("/api/departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isOk());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
        Department testDepartment = departmentList.get(departmentList.size() - 1);
        assertThat(testDepartment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDepartment.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDepartment.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testDepartment.isLeaf()).isEqualTo(UPDATED_LEAF);
        assertThat(testDepartment.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDepartment.getExtMap()).isEqualTo(UPDATED_EXT_MAP);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartment() throws Exception {
        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentMockMvc.perform(put("/api/departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        int databaseSizeBeforeDelete = departmentRepository.findAll().size();

        // Delete the department
        restDepartmentMockMvc.perform(delete("/api/departments/{id}", department.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Department.class);
        Department department1 = new Department();
        department1.setId(1L);
        Department department2 = new Department();
        department2.setId(department1.getId());
        assertThat(department1).isEqualTo(department2);
        department2.setId(2L);
        assertThat(department1).isNotEqualTo(department2);
        department1.setId(null);
        assertThat(department1).isNotEqualTo(department2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentDTO.class);
        DepartmentDTO departmentDTO1 = new DepartmentDTO();
        departmentDTO1.setId(1L);
        DepartmentDTO departmentDTO2 = new DepartmentDTO();
        assertThat(departmentDTO1).isNotEqualTo(departmentDTO2);
        departmentDTO2.setId(departmentDTO1.getId());
        assertThat(departmentDTO1).isEqualTo(departmentDTO2);
        departmentDTO2.setId(2L);
        assertThat(departmentDTO1).isNotEqualTo(departmentDTO2);
        departmentDTO1.setId(null);
        assertThat(departmentDTO1).isNotEqualTo(departmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(departmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(departmentMapper.fromId(null)).isNull();
    }
}
