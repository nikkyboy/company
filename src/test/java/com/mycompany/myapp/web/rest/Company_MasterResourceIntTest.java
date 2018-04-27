package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.PractiseApp;

import com.mycompany.myapp.domain.Company_Master;
import com.mycompany.myapp.repository.Company_MasterRepository;
import com.mycompany.myapp.service.Company_MasterService;
import com.mycompany.myapp.service.dto.Company_MasterDTO;
import com.mycompany.myapp.service.mapper.Company_MasterMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Company_MasterResource REST controller.
 *
 * @see Company_MasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PractiseApp.class)
public class Company_MasterResourceIntTest extends AbstractCassandraTest {

    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    @Autowired
    private Company_MasterRepository company_MasterRepository;

    @Autowired
    private Company_MasterMapper company_MasterMapper;

    @Autowired
    private Company_MasterService company_MasterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCompany_MasterMockMvc;

    private Company_Master company_Master;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Company_MasterResource company_MasterResource = new Company_MasterResource(company_MasterService);
        this.restCompany_MasterMockMvc = MockMvcBuilders.standaloneSetup(company_MasterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company_Master createEntity() {
        Company_Master company_Master = new Company_Master()
            .company_id(DEFAULT_COMPANY_ID)
            .company_Name(DEFAULT_COMPANY_NAME);
        return company_Master;
    }

    @Before
    public void initTest() {
        company_MasterRepository.deleteAll();
        company_Master = createEntity();
    }

    @Test
    public void createCompany_Master() throws Exception {
        int databaseSizeBeforeCreate = company_MasterRepository.findAll().size();

        // Create the Company_Master
        Company_MasterDTO company_MasterDTO = company_MasterMapper.toDto(company_Master);
        restCompany_MasterMockMvc.perform(post("/api/company-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company_MasterDTO)))
            .andExpect(status().isCreated());

        // Validate the Company_Master in the database
        List<Company_Master> company_MasterList = company_MasterRepository.findAll();
        assertThat(company_MasterList).hasSize(databaseSizeBeforeCreate + 1);
        Company_Master testCompany_Master = company_MasterList.get(company_MasterList.size() - 1);
        assertThat(testCompany_Master.getCompany_id()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCompany_Master.getCompany_Name()).isEqualTo(DEFAULT_COMPANY_NAME);
    }

    @Test
    public void createCompany_MasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = company_MasterRepository.findAll().size();

        // Create the Company_Master with an existing ID
        company_Master.setId(UUID.randomUUID());
        Company_MasterDTO company_MasterDTO = company_MasterMapper.toDto(company_Master);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompany_MasterMockMvc.perform(post("/api/company-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company_MasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company_Master in the database
        List<Company_Master> company_MasterList = company_MasterRepository.findAll();
        assertThat(company_MasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCompany_Masters() throws Exception {
        // Initialize the database
        company_MasterRepository.save(company_Master);

        // Get all the company_MasterList
        restCompany_MasterMockMvc.perform(get("/api/company-masters"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company_Master.getId().toString())))
            .andExpect(jsonPath("$.[*].company_id").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].company_Name").value(hasItem(DEFAULT_COMPANY_NAME.toString())));
    }

    @Test
    public void getCompany_Master() throws Exception {
        // Initialize the database
        company_MasterRepository.save(company_Master);

        // Get the company_Master
        restCompany_MasterMockMvc.perform(get("/api/company-masters/{id}", company_Master.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(company_Master.getId().toString()))
            .andExpect(jsonPath("$.company_id").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.company_Name").value(DEFAULT_COMPANY_NAME.toString()));
    }

    @Test
    public void getNonExistingCompany_Master() throws Exception {
        // Get the company_Master
        restCompany_MasterMockMvc.perform(get("/api/company-masters/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCompany_Master() throws Exception {
        // Initialize the database
        company_MasterRepository.save(company_Master);
        int databaseSizeBeforeUpdate = company_MasterRepository.findAll().size();

        // Update the company_Master
        Company_Master updatedCompany_Master = company_MasterRepository.findOne(company_Master.getId());
        updatedCompany_Master
            .company_id(UPDATED_COMPANY_ID)
            .company_Name(UPDATED_COMPANY_NAME);
        Company_MasterDTO company_MasterDTO = company_MasterMapper.toDto(updatedCompany_Master);

        restCompany_MasterMockMvc.perform(put("/api/company-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company_MasterDTO)))
            .andExpect(status().isOk());

        // Validate the Company_Master in the database
        List<Company_Master> company_MasterList = company_MasterRepository.findAll();
        assertThat(company_MasterList).hasSize(databaseSizeBeforeUpdate);
        Company_Master testCompany_Master = company_MasterList.get(company_MasterList.size() - 1);
        assertThat(testCompany_Master.getCompany_id()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCompany_Master.getCompany_Name()).isEqualTo(UPDATED_COMPANY_NAME);
    }

    @Test
    public void updateNonExistingCompany_Master() throws Exception {
        int databaseSizeBeforeUpdate = company_MasterRepository.findAll().size();

        // Create the Company_Master
        Company_MasterDTO company_MasterDTO = company_MasterMapper.toDto(company_Master);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompany_MasterMockMvc.perform(put("/api/company-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company_MasterDTO)))
            .andExpect(status().isCreated());

        // Validate the Company_Master in the database
        List<Company_Master> company_MasterList = company_MasterRepository.findAll();
        assertThat(company_MasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCompany_Master() throws Exception {
        // Initialize the database
        company_MasterRepository.save(company_Master);
        int databaseSizeBeforeDelete = company_MasterRepository.findAll().size();

        // Get the company_Master
        restCompany_MasterMockMvc.perform(delete("/api/company-masters/{id}", company_Master.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Company_Master> company_MasterList = company_MasterRepository.findAll();
        assertThat(company_MasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Company_Master.class);
        Company_Master company_Master1 = new Company_Master();
        company_Master1.setId(UUID.randomUUID());
        Company_Master company_Master2 = new Company_Master();
        company_Master2.setId(company_Master1.getId());
        assertThat(company_Master1).isEqualTo(company_Master2);
        company_Master2.setId(UUID.randomUUID());
        assertThat(company_Master1).isNotEqualTo(company_Master2);
        company_Master1.setId(null);
        assertThat(company_Master1).isNotEqualTo(company_Master2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Company_MasterDTO.class);
        Company_MasterDTO company_MasterDTO1 = new Company_MasterDTO();
        company_MasterDTO1.setId(UUID.randomUUID());
        Company_MasterDTO company_MasterDTO2 = new Company_MasterDTO();
        assertThat(company_MasterDTO1).isNotEqualTo(company_MasterDTO2);
        company_MasterDTO2.setId(company_MasterDTO1.getId());
        assertThat(company_MasterDTO1).isEqualTo(company_MasterDTO2);
        company_MasterDTO2.setId(UUID.randomUUID());
        assertThat(company_MasterDTO1).isNotEqualTo(company_MasterDTO2);
        company_MasterDTO1.setId(null);
        assertThat(company_MasterDTO1).isNotEqualTo(company_MasterDTO2);
    }
}
