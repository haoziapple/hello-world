package com.github.haozi.web.rest;

import com.github.haozi.XauthApp;

import com.github.haozi.domain.Menu;
import com.github.haozi.domain.Menu;
import com.github.haozi.domain.Role;
import com.github.haozi.domain.Template;
import com.github.haozi.domain.Auth;
import com.github.haozi.repository.MenuRepository;
import com.github.haozi.service.MenuService;
import com.github.haozi.service.dto.MenuDTO;
import com.github.haozi.service.mapper.MenuMapper;
import com.github.haozi.web.rest.errors.ExceptionTranslator;
import com.github.haozi.service.dto.MenuCriteria;
import com.github.haozi.service.MenuQueryService;

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
 * Test class for the MenuResource REST controller.
 *
 * @see MenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XauthApp.class)
public class MenuResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LEAF = false;
    private static final Boolean UPDATED_LEAF = true;

    private static final Boolean DEFAULT_SHOW_FLAG = false;
    private static final Boolean UPDATED_SHOW_FLAG = true;

    private static final String DEFAULT_EXTMAP = "AAAAAAAAAA";
    private static final String UPDATED_EXTMAP = "BBBBBBBBBB";

    @Autowired
    private MenuRepository menuRepository;

    @Mock
    private MenuRepository menuRepositoryMock;

    @Autowired
    private MenuMapper menuMapper;

    @Mock
    private MenuService menuServiceMock;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuQueryService menuQueryService;

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

    private MockMvc restMenuMockMvc;

    private Menu menu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MenuResource menuResource = new MenuResource(menuService, menuQueryService);
        this.restMenuMockMvc = MockMvcBuilders.standaloneSetup(menuResource)
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
    public static Menu createEntity(EntityManager em) {
        Menu menu = new Menu()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .seq(DEFAULT_SEQ)
            .state(DEFAULT_STATE)
            .url(DEFAULT_URL)
            .leaf(DEFAULT_LEAF)
            .showFlag(DEFAULT_SHOW_FLAG)
            .extmap(DEFAULT_EXTMAP);
        return menu;
    }

    @Before
    public void initTest() {
        menu = createEntity(em);
    }

    @Test
    @Transactional
    public void createMenu() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // Create the Menu
        MenuDTO menuDTO = menuMapper.toDto(menu);
        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isCreated());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate + 1);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMenu.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMenu.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testMenu.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testMenu.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testMenu.isLeaf()).isEqualTo(DEFAULT_LEAF);
        assertThat(testMenu.isShowFlag()).isEqualTo(DEFAULT_SHOW_FLAG);
        assertThat(testMenu.getExtmap()).isEqualTo(DEFAULT_EXTMAP);
    }

    @Test
    @Transactional
    public void createMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // Create the Menu with an existing ID
        menu.setId(1L);
        MenuDTO menuDTO = menuMapper.toDto(menu);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMenus() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList
        restMenuMockMvc.perform(get("/api/menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menu.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())))
            .andExpect(jsonPath("$.[*].showFlag").value(hasItem(DEFAULT_SHOW_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMenusWithEagerRelationshipsIsEnabled() throws Exception {
        MenuResource menuResource = new MenuResource(menuServiceMock, menuQueryService);
        when(menuServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMenuMockMvc = MockMvcBuilders.standaloneSetup(menuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMenuMockMvc.perform(get("/api/menus?eagerload=true"))
        .andExpect(status().isOk());

        verify(menuServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMenusWithEagerRelationshipsIsNotEnabled() throws Exception {
        MenuResource menuResource = new MenuResource(menuServiceMock, menuQueryService);
            when(menuServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMenuMockMvc = MockMvcBuilders.standaloneSetup(menuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMenuMockMvc.perform(get("/api/menus?eagerload=true"))
        .andExpect(status().isOk());

            verify(menuServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", menu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(menu.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.leaf").value(DEFAULT_LEAF.booleanValue()))
            .andExpect(jsonPath("$.showFlag").value(DEFAULT_SHOW_FLAG.booleanValue()))
            .andExpect(jsonPath("$.extmap").value(DEFAULT_EXTMAP.toString()));
    }

    @Test
    @Transactional
    public void getAllMenusByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where name equals to DEFAULT_NAME
        defaultMenuShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the menuList where name equals to UPDATED_NAME
        defaultMenuShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMenusByNameIsInShouldWork() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMenuShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the menuList where name equals to UPDATED_NAME
        defaultMenuShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMenusByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where name is not null
        defaultMenuShouldBeFound("name.specified=true");

        // Get all the menuList where name is null
        defaultMenuShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllMenusByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where code equals to DEFAULT_CODE
        defaultMenuShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the menuList where code equals to UPDATED_CODE
        defaultMenuShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllMenusByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where code in DEFAULT_CODE or UPDATED_CODE
        defaultMenuShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the menuList where code equals to UPDATED_CODE
        defaultMenuShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllMenusByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where code is not null
        defaultMenuShouldBeFound("code.specified=true");

        // Get all the menuList where code is null
        defaultMenuShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllMenusBySeqIsEqualToSomething() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where seq equals to DEFAULT_SEQ
        defaultMenuShouldBeFound("seq.equals=" + DEFAULT_SEQ);

        // Get all the menuList where seq equals to UPDATED_SEQ
        defaultMenuShouldNotBeFound("seq.equals=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    public void getAllMenusBySeqIsInShouldWork() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where seq in DEFAULT_SEQ or UPDATED_SEQ
        defaultMenuShouldBeFound("seq.in=" + DEFAULT_SEQ + "," + UPDATED_SEQ);

        // Get all the menuList where seq equals to UPDATED_SEQ
        defaultMenuShouldNotBeFound("seq.in=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    public void getAllMenusBySeqIsNullOrNotNull() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where seq is not null
        defaultMenuShouldBeFound("seq.specified=true");

        // Get all the menuList where seq is null
        defaultMenuShouldNotBeFound("seq.specified=false");
    }

    @Test
    @Transactional
    public void getAllMenusBySeqIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where seq greater than or equals to DEFAULT_SEQ
        defaultMenuShouldBeFound("seq.greaterOrEqualThan=" + DEFAULT_SEQ);

        // Get all the menuList where seq greater than or equals to UPDATED_SEQ
        defaultMenuShouldNotBeFound("seq.greaterOrEqualThan=" + UPDATED_SEQ);
    }

    @Test
    @Transactional
    public void getAllMenusBySeqIsLessThanSomething() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where seq less than or equals to DEFAULT_SEQ
        defaultMenuShouldNotBeFound("seq.lessThan=" + DEFAULT_SEQ);

        // Get all the menuList where seq less than or equals to UPDATED_SEQ
        defaultMenuShouldBeFound("seq.lessThan=" + UPDATED_SEQ);
    }


    @Test
    @Transactional
    public void getAllMenusByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where state equals to DEFAULT_STATE
        defaultMenuShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the menuList where state equals to UPDATED_STATE
        defaultMenuShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllMenusByStateIsInShouldWork() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where state in DEFAULT_STATE or UPDATED_STATE
        defaultMenuShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the menuList where state equals to UPDATED_STATE
        defaultMenuShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllMenusByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where state is not null
        defaultMenuShouldBeFound("state.specified=true");

        // Get all the menuList where state is null
        defaultMenuShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    public void getAllMenusByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where url equals to DEFAULT_URL
        defaultMenuShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the menuList where url equals to UPDATED_URL
        defaultMenuShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllMenusByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where url in DEFAULT_URL or UPDATED_URL
        defaultMenuShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the menuList where url equals to UPDATED_URL
        defaultMenuShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllMenusByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where url is not null
        defaultMenuShouldBeFound("url.specified=true");

        // Get all the menuList where url is null
        defaultMenuShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllMenusByLeafIsEqualToSomething() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where leaf equals to DEFAULT_LEAF
        defaultMenuShouldBeFound("leaf.equals=" + DEFAULT_LEAF);

        // Get all the menuList where leaf equals to UPDATED_LEAF
        defaultMenuShouldNotBeFound("leaf.equals=" + UPDATED_LEAF);
    }

    @Test
    @Transactional
    public void getAllMenusByLeafIsInShouldWork() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where leaf in DEFAULT_LEAF or UPDATED_LEAF
        defaultMenuShouldBeFound("leaf.in=" + DEFAULT_LEAF + "," + UPDATED_LEAF);

        // Get all the menuList where leaf equals to UPDATED_LEAF
        defaultMenuShouldNotBeFound("leaf.in=" + UPDATED_LEAF);
    }

    @Test
    @Transactional
    public void getAllMenusByLeafIsNullOrNotNull() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where leaf is not null
        defaultMenuShouldBeFound("leaf.specified=true");

        // Get all the menuList where leaf is null
        defaultMenuShouldNotBeFound("leaf.specified=false");
    }

    @Test
    @Transactional
    public void getAllMenusByShowFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where showFlag equals to DEFAULT_SHOW_FLAG
        defaultMenuShouldBeFound("showFlag.equals=" + DEFAULT_SHOW_FLAG);

        // Get all the menuList where showFlag equals to UPDATED_SHOW_FLAG
        defaultMenuShouldNotBeFound("showFlag.equals=" + UPDATED_SHOW_FLAG);
    }

    @Test
    @Transactional
    public void getAllMenusByShowFlagIsInShouldWork() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where showFlag in DEFAULT_SHOW_FLAG or UPDATED_SHOW_FLAG
        defaultMenuShouldBeFound("showFlag.in=" + DEFAULT_SHOW_FLAG + "," + UPDATED_SHOW_FLAG);

        // Get all the menuList where showFlag equals to UPDATED_SHOW_FLAG
        defaultMenuShouldNotBeFound("showFlag.in=" + UPDATED_SHOW_FLAG);
    }

    @Test
    @Transactional
    public void getAllMenusByShowFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where showFlag is not null
        defaultMenuShouldBeFound("showFlag.specified=true");

        // Get all the menuList where showFlag is null
        defaultMenuShouldNotBeFound("showFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllMenusByExtmapIsEqualToSomething() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where extmap equals to DEFAULT_EXTMAP
        defaultMenuShouldBeFound("extmap.equals=" + DEFAULT_EXTMAP);

        // Get all the menuList where extmap equals to UPDATED_EXTMAP
        defaultMenuShouldNotBeFound("extmap.equals=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllMenusByExtmapIsInShouldWork() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where extmap in DEFAULT_EXTMAP or UPDATED_EXTMAP
        defaultMenuShouldBeFound("extmap.in=" + DEFAULT_EXTMAP + "," + UPDATED_EXTMAP);

        // Get all the menuList where extmap equals to UPDATED_EXTMAP
        defaultMenuShouldNotBeFound("extmap.in=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllMenusByExtmapIsNullOrNotNull() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList where extmap is not null
        defaultMenuShouldBeFound("extmap.specified=true");

        // Get all the menuList where extmap is null
        defaultMenuShouldNotBeFound("extmap.specified=false");
    }

    @Test
    @Transactional
    public void getAllMenusByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        Menu parent = MenuResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        menu.setParent(parent);
        menuRepository.saveAndFlush(menu);
        Long parentId = parent.getId();

        // Get all the menuList where parent equals to parentId
        defaultMenuShouldBeFound("parentId.equals=" + parentId);

        // Get all the menuList where parent equals to parentId + 1
        defaultMenuShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllMenusByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        Role role = RoleResourceIntTest.createEntity(em);
        em.persist(role);
        em.flush();
        menu.addRole(role);
        menuRepository.saveAndFlush(menu);
        Long roleId = role.getId();

        // Get all the menuList where role equals to roleId
        defaultMenuShouldBeFound("roleId.equals=" + roleId);

        // Get all the menuList where role equals to roleId + 1
        defaultMenuShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }


    @Test
    @Transactional
    public void getAllMenusByTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        Template template = TemplateResourceIntTest.createEntity(em);
        em.persist(template);
        em.flush();
        menu.addTemplate(template);
        menuRepository.saveAndFlush(menu);
        Long templateId = template.getId();

        // Get all the menuList where template equals to templateId
        defaultMenuShouldBeFound("templateId.equals=" + templateId);

        // Get all the menuList where template equals to templateId + 1
        defaultMenuShouldNotBeFound("templateId.equals=" + (templateId + 1));
    }


    @Test
    @Transactional
    public void getAllMenusByAuthIsEqualToSomething() throws Exception {
        // Initialize the database
        Auth auth = AuthResourceIntTest.createEntity(em);
        em.persist(auth);
        em.flush();
        menu.addAuth(auth);
        menuRepository.saveAndFlush(menu);
        Long authId = auth.getId();

        // Get all the menuList where auth equals to authId
        defaultMenuShouldBeFound("authId.equals=" + authId);

        // Get all the menuList where auth equals to authId + 1
        defaultMenuShouldNotBeFound("authId.equals=" + (authId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMenuShouldBeFound(String filter) throws Exception {
        restMenuMockMvc.perform(get("/api/menus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menu.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())))
            .andExpect(jsonPath("$.[*].showFlag").value(hasItem(DEFAULT_SHOW_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP)));

        // Check, that the count call also returns 1
        restMenuMockMvc.perform(get("/api/menus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMenuShouldNotBeFound(String filter) throws Exception {
        restMenuMockMvc.perform(get("/api/menus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMenuMockMvc.perform(get("/api/menus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMenu() throws Exception {
        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Update the menu
        Menu updatedMenu = menuRepository.findById(menu.getId()).get();
        // Disconnect from session so that the updates on updatedMenu are not directly saved in db
        em.detach(updatedMenu);
        updatedMenu
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .seq(UPDATED_SEQ)
            .state(UPDATED_STATE)
            .url(UPDATED_URL)
            .leaf(UPDATED_LEAF)
            .showFlag(UPDATED_SHOW_FLAG)
            .extmap(UPDATED_EXTMAP);
        MenuDTO menuDTO = menuMapper.toDto(updatedMenu);

        restMenuMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isOk());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenu.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMenu.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testMenu.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testMenu.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testMenu.isLeaf()).isEqualTo(UPDATED_LEAF);
        assertThat(testMenu.isShowFlag()).isEqualTo(UPDATED_SHOW_FLAG);
        assertThat(testMenu.getExtmap()).isEqualTo(UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void updateNonExistingMenu() throws Exception {
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Create the Menu
        MenuDTO menuDTO = menuMapper.toDto(menu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        int databaseSizeBeforeDelete = menuRepository.findAll().size();

        // Delete the menu
        restMenuMockMvc.perform(delete("/api/menus/{id}", menu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Menu.class);
        Menu menu1 = new Menu();
        menu1.setId(1L);
        Menu menu2 = new Menu();
        menu2.setId(menu1.getId());
        assertThat(menu1).isEqualTo(menu2);
        menu2.setId(2L);
        assertThat(menu1).isNotEqualTo(menu2);
        menu1.setId(null);
        assertThat(menu1).isNotEqualTo(menu2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuDTO.class);
        MenuDTO menuDTO1 = new MenuDTO();
        menuDTO1.setId(1L);
        MenuDTO menuDTO2 = new MenuDTO();
        assertThat(menuDTO1).isNotEqualTo(menuDTO2);
        menuDTO2.setId(menuDTO1.getId());
        assertThat(menuDTO1).isEqualTo(menuDTO2);
        menuDTO2.setId(2L);
        assertThat(menuDTO1).isNotEqualTo(menuDTO2);
        menuDTO1.setId(null);
        assertThat(menuDTO1).isNotEqualTo(menuDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(menuMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(menuMapper.fromId(null)).isNull();
    }
}
