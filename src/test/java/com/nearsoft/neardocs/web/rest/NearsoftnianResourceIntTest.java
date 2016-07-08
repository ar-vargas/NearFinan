package com.nearsoft.neardocs.web.rest;

import com.nearsoft.neardocs.NearDocsApp;
import com.nearsoft.neardocs.domain.Nearsoftnian;
import com.nearsoft.neardocs.repository.NearsoftnianRepository;
import com.nearsoft.neardocs.service.NearsoftnianService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the NearsoftnianResource REST controller.
 *
 * @see NearsoftnianResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NearDocsApp.class)
@WebAppConfiguration
@IntegrationTest
public class NearsoftnianResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_NAME_2 = "AAAAA";
    private static final String UPDATED_NAME_2 = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME_2 = "AAAAA";
    private static final String UPDATED_LAST_NAME_2 = "BBBBB";

    @Inject
    private NearsoftnianRepository nearsoftnianRepository;

    @Inject
    private NearsoftnianService nearsoftnianService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNearsoftnianMockMvc;

    private Nearsoftnian nearsoftnian;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NearsoftnianResource nearsoftnianResource = new NearsoftnianResource();
        ReflectionTestUtils.setField(nearsoftnianResource, "nearsoftnianService", nearsoftnianService);
        this.restNearsoftnianMockMvc = MockMvcBuilders.standaloneSetup(nearsoftnianResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        nearsoftnian = new Nearsoftnian();
        nearsoftnian.setEmail(DEFAULT_EMAIL);
        nearsoftnian.setName(DEFAULT_NAME);
        nearsoftnian.setName2(DEFAULT_NAME_2);
        nearsoftnian.setLastName(DEFAULT_LAST_NAME);
        nearsoftnian.setLastName2(DEFAULT_LAST_NAME_2);
    }

    @Test
    @Transactional
    public void createNearsoftnian() throws Exception {
        int databaseSizeBeforeCreate = nearsoftnianRepository.findAll().size();

        // Create the Nearsoftnian

        restNearsoftnianMockMvc.perform(post("/api/nearsoftnians")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nearsoftnian)))
                .andExpect(status().isCreated());

        // Validate the Nearsoftnian in the database
        List<Nearsoftnian> nearsoftnians = nearsoftnianRepository.findAll();
        assertThat(nearsoftnians).hasSize(databaseSizeBeforeCreate + 1);
        Nearsoftnian testNearsoftnian = nearsoftnians.get(nearsoftnians.size() - 1);
        assertThat(testNearsoftnian.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testNearsoftnian.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNearsoftnian.getName2()).isEqualTo(DEFAULT_NAME_2);
        assertThat(testNearsoftnian.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testNearsoftnian.getLastName2()).isEqualTo(DEFAULT_LAST_NAME_2);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = nearsoftnianRepository.findAll().size();
        // set the field null
        nearsoftnian.setEmail(null);

        // Create the Nearsoftnian, which fails.

        restNearsoftnianMockMvc.perform(post("/api/nearsoftnians")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nearsoftnian)))
                .andExpect(status().isBadRequest());

        List<Nearsoftnian> nearsoftnians = nearsoftnianRepository.findAll();
        assertThat(nearsoftnians).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nearsoftnianRepository.findAll().size();
        // set the field null
        nearsoftnian.setName(null);

        // Create the Nearsoftnian, which fails.

        restNearsoftnianMockMvc.perform(post("/api/nearsoftnians")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nearsoftnian)))
                .andExpect(status().isBadRequest());

        List<Nearsoftnian> nearsoftnians = nearsoftnianRepository.findAll();
        assertThat(nearsoftnians).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nearsoftnianRepository.findAll().size();
        // set the field null
        nearsoftnian.setLastName(null);

        // Create the Nearsoftnian, which fails.

        restNearsoftnianMockMvc.perform(post("/api/nearsoftnians")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nearsoftnian)))
                .andExpect(status().isBadRequest());

        List<Nearsoftnian> nearsoftnians = nearsoftnianRepository.findAll();
        assertThat(nearsoftnians).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNearsoftnians() throws Exception {
        // Initialize the database
        nearsoftnianRepository.saveAndFlush(nearsoftnian);

        // Get all the nearsoftnians
        restNearsoftnianMockMvc.perform(get("/api/nearsoftnians?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(nearsoftnian.getId().intValue())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].name2").value(hasItem(DEFAULT_NAME_2.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName2").value(hasItem(DEFAULT_LAST_NAME_2.toString())));
    }

    @Test
    @Transactional
    public void getNearsoftnian() throws Exception {
        // Initialize the database
        nearsoftnianRepository.saveAndFlush(nearsoftnian);

        // Get the nearsoftnian
        restNearsoftnianMockMvc.perform(get("/api/nearsoftnians/{id}", nearsoftnian.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(nearsoftnian.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.name2").value(DEFAULT_NAME_2.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.lastName2").value(DEFAULT_LAST_NAME_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNearsoftnian() throws Exception {
        // Get the nearsoftnian
        restNearsoftnianMockMvc.perform(get("/api/nearsoftnians/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNearsoftnian() throws Exception {
        // Initialize the database
        nearsoftnianService.save(nearsoftnian);

        int databaseSizeBeforeUpdate = nearsoftnianRepository.findAll().size();

        // Update the nearsoftnian
        Nearsoftnian updatedNearsoftnian = new Nearsoftnian();
        updatedNearsoftnian.setId(nearsoftnian.getId());
        updatedNearsoftnian.setEmail(UPDATED_EMAIL);
        updatedNearsoftnian.setName(UPDATED_NAME);
        updatedNearsoftnian.setName2(UPDATED_NAME_2);
        updatedNearsoftnian.setLastName(UPDATED_LAST_NAME);
        updatedNearsoftnian.setLastName2(UPDATED_LAST_NAME_2);

        restNearsoftnianMockMvc.perform(put("/api/nearsoftnians")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedNearsoftnian)))
                .andExpect(status().isOk());

        // Validate the Nearsoftnian in the database
        List<Nearsoftnian> nearsoftnians = nearsoftnianRepository.findAll();
        assertThat(nearsoftnians).hasSize(databaseSizeBeforeUpdate);
        Nearsoftnian testNearsoftnian = nearsoftnians.get(nearsoftnians.size() - 1);
        assertThat(testNearsoftnian.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNearsoftnian.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNearsoftnian.getName2()).isEqualTo(UPDATED_NAME_2);
        assertThat(testNearsoftnian.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testNearsoftnian.getLastName2()).isEqualTo(UPDATED_LAST_NAME_2);
    }

    @Test
    @Transactional
    public void deleteNearsoftnian() throws Exception {
        // Initialize the database
        nearsoftnianService.save(nearsoftnian);

        int databaseSizeBeforeDelete = nearsoftnianRepository.findAll().size();

        // Get the nearsoftnian
        restNearsoftnianMockMvc.perform(delete("/api/nearsoftnians/{id}", nearsoftnian.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Nearsoftnian> nearsoftnians = nearsoftnianRepository.findAll();
        assertThat(nearsoftnians).hasSize(databaseSizeBeforeDelete - 1);
    }
}
