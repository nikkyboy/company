package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.PractiseApp;

import com.mycompany.myapp.domain.Product_Master;
import com.mycompany.myapp.repository.Product_MasterRepository;
import com.mycompany.myapp.service.Product_MasterService;
import com.mycompany.myapp.service.dto.Product_MasterDTO;
import com.mycompany.myapp.service.mapper.Product_MasterMapper;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Product_MasterResource REST controller.
 *
 * @see Product_MasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PractiseApp.class)
public class Product_MasterResourceIntTest extends AbstractCassandraTest {

    private static final Integer DEFAULT_PRODUCT_ID = 1;
    private static final Integer UPDATED_PRODUCT_ID = 2;

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);

    @Autowired
    private Product_MasterRepository product_MasterRepository;

    @Autowired
    private Product_MasterMapper product_MasterMapper;

    @Autowired
    private Product_MasterService product_MasterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restProduct_MasterMockMvc;

    private Product_Master product_Master;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Product_MasterResource product_MasterResource = new Product_MasterResource(product_MasterService);
        this.restProduct_MasterMockMvc = MockMvcBuilders.standaloneSetup(product_MasterResource)
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
    public static Product_Master createEntity() {
        Product_Master product_Master = new Product_Master()
            .product_id(DEFAULT_PRODUCT_ID)
            .product_Name(DEFAULT_PRODUCT_NAME)
            .company_id(DEFAULT_COMPANY_ID)
            .unit_Price(DEFAULT_UNIT_PRICE);
        return product_Master;
    }

    @Before
    public void initTest() {
        product_MasterRepository.deleteAll();
        product_Master = createEntity();
    }

    @Test
    public void createProduct_Master() throws Exception {
        int databaseSizeBeforeCreate = product_MasterRepository.findAll().size();

        // Create the Product_Master
        Product_MasterDTO product_MasterDTO = product_MasterMapper.toDto(product_Master);
        restProduct_MasterMockMvc.perform(post("/api/product-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product_MasterDTO)))
            .andExpect(status().isCreated());

        // Validate the Product_Master in the database
        List<Product_Master> product_MasterList = product_MasterRepository.findAll();
        assertThat(product_MasterList).hasSize(databaseSizeBeforeCreate + 1);
        Product_Master testProduct_Master = product_MasterList.get(product_MasterList.size() - 1);
        assertThat(testProduct_Master.getProduct_id()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testProduct_Master.getProduct_Name()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProduct_Master.getCompany_id()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testProduct_Master.getUnit_Price()).isEqualTo(DEFAULT_UNIT_PRICE);
    }

    @Test
    public void createProduct_MasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = product_MasterRepository.findAll().size();

        // Create the Product_Master with an existing ID
        product_Master.setId(UUID.randomUUID());
        Product_MasterDTO product_MasterDTO = product_MasterMapper.toDto(product_Master);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduct_MasterMockMvc.perform(post("/api/product-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product_MasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product_Master in the database
        List<Product_Master> product_MasterList = product_MasterRepository.findAll();
        assertThat(product_MasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllProduct_Masters() throws Exception {
        // Initialize the database
        product_MasterRepository.save(product_Master);

        // Get all the product_MasterList
        restProduct_MasterMockMvc.perform(get("/api/product-masters"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product_Master.getId().toString())))
            .andExpect(jsonPath("$.[*].product_id").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].product_Name").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
            .andExpect(jsonPath("$.[*].company_id").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].unit_Price").value(hasItem(DEFAULT_UNIT_PRICE.intValue())));
    }

    @Test
    public void getProduct_Master() throws Exception {
        // Initialize the database
        product_MasterRepository.save(product_Master);

        // Get the product_Master
        restProduct_MasterMockMvc.perform(get("/api/product-masters/{id}", product_Master.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product_Master.getId().toString()))
            .andExpect(jsonPath("$.product_id").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.product_Name").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.company_id").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.unit_Price").value(DEFAULT_UNIT_PRICE.intValue()));
    }

    @Test
    public void getNonExistingProduct_Master() throws Exception {
        // Get the product_Master
        restProduct_MasterMockMvc.perform(get("/api/product-masters/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProduct_Master() throws Exception {
        // Initialize the database
        product_MasterRepository.save(product_Master);
        int databaseSizeBeforeUpdate = product_MasterRepository.findAll().size();

        // Update the product_Master
        Product_Master updatedProduct_Master = product_MasterRepository.findOne(product_Master.getId());
        updatedProduct_Master
            .product_id(UPDATED_PRODUCT_ID)
            .product_Name(UPDATED_PRODUCT_NAME)
            .company_id(UPDATED_COMPANY_ID)
            .unit_Price(UPDATED_UNIT_PRICE);
        Product_MasterDTO product_MasterDTO = product_MasterMapper.toDto(updatedProduct_Master);

        restProduct_MasterMockMvc.perform(put("/api/product-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product_MasterDTO)))
            .andExpect(status().isOk());

        // Validate the Product_Master in the database
        List<Product_Master> product_MasterList = product_MasterRepository.findAll();
        assertThat(product_MasterList).hasSize(databaseSizeBeforeUpdate);
        Product_Master testProduct_Master = product_MasterList.get(product_MasterList.size() - 1);
        assertThat(testProduct_Master.getProduct_id()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testProduct_Master.getProduct_Name()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct_Master.getCompany_id()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testProduct_Master.getUnit_Price()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    public void updateNonExistingProduct_Master() throws Exception {
        int databaseSizeBeforeUpdate = product_MasterRepository.findAll().size();

        // Create the Product_Master
        Product_MasterDTO product_MasterDTO = product_MasterMapper.toDto(product_Master);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProduct_MasterMockMvc.perform(put("/api/product-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product_MasterDTO)))
            .andExpect(status().isCreated());

        // Validate the Product_Master in the database
        List<Product_Master> product_MasterList = product_MasterRepository.findAll();
        assertThat(product_MasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteProduct_Master() throws Exception {
        // Initialize the database
        product_MasterRepository.save(product_Master);
        int databaseSizeBeforeDelete = product_MasterRepository.findAll().size();

        // Get the product_Master
        restProduct_MasterMockMvc.perform(delete("/api/product-masters/{id}", product_Master.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Product_Master> product_MasterList = product_MasterRepository.findAll();
        assertThat(product_MasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product_Master.class);
        Product_Master product_Master1 = new Product_Master();
        product_Master1.setId(UUID.randomUUID());
        Product_Master product_Master2 = new Product_Master();
        product_Master2.setId(product_Master1.getId());
        assertThat(product_Master1).isEqualTo(product_Master2);
        product_Master2.setId(UUID.randomUUID());
        assertThat(product_Master1).isNotEqualTo(product_Master2);
        product_Master1.setId(null);
        assertThat(product_Master1).isNotEqualTo(product_Master2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product_MasterDTO.class);
        Product_MasterDTO product_MasterDTO1 = new Product_MasterDTO();
        product_MasterDTO1.setId(UUID.randomUUID());
        Product_MasterDTO product_MasterDTO2 = new Product_MasterDTO();
        assertThat(product_MasterDTO1).isNotEqualTo(product_MasterDTO2);
        product_MasterDTO2.setId(product_MasterDTO1.getId());
        assertThat(product_MasterDTO1).isEqualTo(product_MasterDTO2);
        product_MasterDTO2.setId(UUID.randomUUID());
        assertThat(product_MasterDTO1).isNotEqualTo(product_MasterDTO2);
        product_MasterDTO1.setId(null);
        assertThat(product_MasterDTO1).isNotEqualTo(product_MasterDTO2);
    }
}
