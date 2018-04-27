package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AbstractCassandraTest;
import com.mycompany.myapp.PractiseApp;

import com.mycompany.myapp.domain.Order_Details;
import com.mycompany.myapp.repository.Order_DetailsRepository;
import com.mycompany.myapp.service.Order_DetailsService;
import com.mycompany.myapp.service.dto.Order_DetailsDTO;
import com.mycompany.myapp.service.mapper.Order_DetailsMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Order_DetailsResource REST controller.
 *
 * @see Order_DetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PractiseApp.class)
public class Order_DetailsResourceIntTest extends AbstractCassandraTest {

    private static final Integer DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_ORDER_ID = 2;

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final Integer DEFAULT_PRODUCT_ID = 1;
    private static final Integer UPDATED_PRODUCT_ID = 2;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_VAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ORDER_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORDER_VALUE = new BigDecimal(2);

    @Autowired
    private Order_DetailsRepository order_DetailsRepository;

    @Autowired
    private Order_DetailsMapper order_DetailsMapper;

    @Autowired
    private Order_DetailsService order_DetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restOrder_DetailsMockMvc;

    private Order_Details order_Details;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Order_DetailsResource order_DetailsResource = new Order_DetailsResource(order_DetailsService);
        this.restOrder_DetailsMockMvc = MockMvcBuilders.standaloneSetup(order_DetailsResource)
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
    public static Order_Details createEntity() {
        Order_Details order_Details = new Order_Details()
            .order_id(DEFAULT_ORDER_ID)
            .order_Date(DEFAULT_ORDER_DATE)
            .company_id(DEFAULT_COMPANY_ID)
            .product_id(DEFAULT_PRODUCT_ID)
            .quantity(DEFAULT_QUANTITY)
            .total(DEFAULT_TOTAL)
            .vat(DEFAULT_VAT)
            .order_value(DEFAULT_ORDER_VALUE);
        return order_Details;
    }

    @Before
    public void initTest() {
        order_DetailsRepository.deleteAll();
        order_Details = createEntity();
    }

    @Test
    public void createOrder_Details() throws Exception {
        int databaseSizeBeforeCreate = order_DetailsRepository.findAll().size();

        // Create the Order_Details
        Order_DetailsDTO order_DetailsDTO = order_DetailsMapper.toDto(order_Details);
        restOrder_DetailsMockMvc.perform(post("/api/order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order_DetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the Order_Details in the database
        List<Order_Details> order_DetailsList = order_DetailsRepository.findAll();
        assertThat(order_DetailsList).hasSize(databaseSizeBeforeCreate + 1);
        Order_Details testOrder_Details = order_DetailsList.get(order_DetailsList.size() - 1);
        assertThat(testOrder_Details.getOrder_id()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testOrder_Details.getOrder_Date()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testOrder_Details.getCompany_id()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testOrder_Details.getProduct_id()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testOrder_Details.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrder_Details.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testOrder_Details.getVat()).isEqualTo(DEFAULT_VAT);
        assertThat(testOrder_Details.getOrder_value()).isEqualTo(DEFAULT_ORDER_VALUE);
    }

    @Test
    public void createOrder_DetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = order_DetailsRepository.findAll().size();

        // Create the Order_Details with an existing ID
        order_Details.setId(UUID.randomUUID());
        Order_DetailsDTO order_DetailsDTO = order_DetailsMapper.toDto(order_Details);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrder_DetailsMockMvc.perform(post("/api/order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order_DetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Order_Details in the database
        List<Order_Details> order_DetailsList = order_DetailsRepository.findAll();
        assertThat(order_DetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllOrder_Details() throws Exception {
        // Initialize the database
        order_DetailsRepository.save(order_Details);

        // Get all the order_DetailsList
        restOrder_DetailsMockMvc.perform(get("/api/order-details"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order_Details.getId().toString())))
            .andExpect(jsonPath("$.[*].order_id").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].order_Date").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].company_id").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].product_id").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.intValue())))
            .andExpect(jsonPath("$.[*].order_value").value(hasItem(DEFAULT_ORDER_VALUE.intValue())));
    }

    @Test
    public void getOrder_Details() throws Exception {
        // Initialize the database
        order_DetailsRepository.save(order_Details);

        // Get the order_Details
        restOrder_DetailsMockMvc.perform(get("/api/order-details/{id}", order_Details.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(order_Details.getId().toString()))
            .andExpect(jsonPath("$.order_id").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.order_Date").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.company_id").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.product_id").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT.intValue()))
            .andExpect(jsonPath("$.order_value").value(DEFAULT_ORDER_VALUE.intValue()));
    }

    @Test
    public void getNonExistingOrder_Details() throws Exception {
        // Get the order_Details
        restOrder_DetailsMockMvc.perform(get("/api/order-details/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrder_Details() throws Exception {
        // Initialize the database
        order_DetailsRepository.save(order_Details);
        int databaseSizeBeforeUpdate = order_DetailsRepository.findAll().size();

        // Update the order_Details
        Order_Details updatedOrder_Details = order_DetailsRepository.findOne(order_Details.getId());
        updatedOrder_Details
            .order_id(UPDATED_ORDER_ID)
            .order_Date(UPDATED_ORDER_DATE)
            .company_id(UPDATED_COMPANY_ID)
            .product_id(UPDATED_PRODUCT_ID)
            .quantity(UPDATED_QUANTITY)
            .total(UPDATED_TOTAL)
            .vat(UPDATED_VAT)
            .order_value(UPDATED_ORDER_VALUE);
        Order_DetailsDTO order_DetailsDTO = order_DetailsMapper.toDto(updatedOrder_Details);

        restOrder_DetailsMockMvc.perform(put("/api/order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order_DetailsDTO)))
            .andExpect(status().isOk());

        // Validate the Order_Details in the database
        List<Order_Details> order_DetailsList = order_DetailsRepository.findAll();
        assertThat(order_DetailsList).hasSize(databaseSizeBeforeUpdate);
        Order_Details testOrder_Details = order_DetailsList.get(order_DetailsList.size() - 1);
        assertThat(testOrder_Details.getOrder_id()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testOrder_Details.getOrder_Date()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrder_Details.getCompany_id()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testOrder_Details.getProduct_id()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testOrder_Details.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrder_Details.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testOrder_Details.getVat()).isEqualTo(UPDATED_VAT);
        assertThat(testOrder_Details.getOrder_value()).isEqualTo(UPDATED_ORDER_VALUE);
    }

    @Test
    public void updateNonExistingOrder_Details() throws Exception {
        int databaseSizeBeforeUpdate = order_DetailsRepository.findAll().size();

        // Create the Order_Details
        Order_DetailsDTO order_DetailsDTO = order_DetailsMapper.toDto(order_Details);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrder_DetailsMockMvc.perform(put("/api/order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order_DetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the Order_Details in the database
        List<Order_Details> order_DetailsList = order_DetailsRepository.findAll();
        assertThat(order_DetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteOrder_Details() throws Exception {
        // Initialize the database
        order_DetailsRepository.save(order_Details);
        int databaseSizeBeforeDelete = order_DetailsRepository.findAll().size();

        // Get the order_Details
        restOrder_DetailsMockMvc.perform(delete("/api/order-details/{id}", order_Details.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Order_Details> order_DetailsList = order_DetailsRepository.findAll();
        assertThat(order_DetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order_Details.class);
        Order_Details order_Details1 = new Order_Details();
        order_Details1.setId(UUID.randomUUID());
        Order_Details order_Details2 = new Order_Details();
        order_Details2.setId(order_Details1.getId());
        assertThat(order_Details1).isEqualTo(order_Details2);
        order_Details2.setId(UUID.randomUUID());
        assertThat(order_Details1).isNotEqualTo(order_Details2);
        order_Details1.setId(null);
        assertThat(order_Details1).isNotEqualTo(order_Details2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order_DetailsDTO.class);
        Order_DetailsDTO order_DetailsDTO1 = new Order_DetailsDTO();
        order_DetailsDTO1.setId(UUID.randomUUID());
        Order_DetailsDTO order_DetailsDTO2 = new Order_DetailsDTO();
        assertThat(order_DetailsDTO1).isNotEqualTo(order_DetailsDTO2);
        order_DetailsDTO2.setId(order_DetailsDTO1.getId());
        assertThat(order_DetailsDTO1).isEqualTo(order_DetailsDTO2);
        order_DetailsDTO2.setId(UUID.randomUUID());
        assertThat(order_DetailsDTO1).isNotEqualTo(order_DetailsDTO2);
        order_DetailsDTO1.setId(null);
        assertThat(order_DetailsDTO1).isNotEqualTo(order_DetailsDTO2);
    }
}
