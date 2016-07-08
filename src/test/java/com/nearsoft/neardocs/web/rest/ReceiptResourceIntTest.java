package com.nearsoft.neardocs.web.rest;

import com.nearsoft.neardocs.NearDocsApp;
import com.nearsoft.neardocs.domain.Receipt;
import com.nearsoft.neardocs.repository.ReceiptRepository;
import com.nearsoft.neardocs.service.ReceiptService;

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
 * Test class for the ReceiptResource REST controller.
 *
 * @see ReceiptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NearDocsApp.class)
@WebAppConfiguration
@IntegrationTest
public class ReceiptResourceIntTest {

    private static final String DEFAULT_PATH = "AAAAA";
    private static final String UPDATED_PATH = "BBBBB";

    private static final Integer DEFAULT_YEAR = 1990;
    private static final Integer UPDATED_YEAR = 1991;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private ReceiptService receiptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restReceiptMockMvc;

    private Receipt receipt;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReceiptResource receiptResource = new ReceiptResource();
        ReflectionTestUtils.setField(receiptResource, "receiptService", receiptService);
        this.restReceiptMockMvc = MockMvcBuilders.standaloneSetup(receiptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        receipt = new Receipt();
        receipt.setPath(DEFAULT_PATH);
        receipt.setYear(DEFAULT_YEAR);
        receipt.setMonth(DEFAULT_MONTH);
    }

    @Test
    @Transactional
    public void createReceipt() throws Exception {
        int databaseSizeBeforeCreate = receiptRepository.findAll().size();

        // Create the Receipt

        restReceiptMockMvc.perform(post("/api/receipts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(receipt)))
                .andExpect(status().isCreated());

        // Validate the Receipt in the database
        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeCreate + 1);
        Receipt testReceipt = receipts.get(receipts.size() - 1);
        assertThat(testReceipt.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testReceipt.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testReceipt.getMonth()).isEqualTo(DEFAULT_MONTH);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setPath(null);

        // Create the Receipt, which fails.

        restReceiptMockMvc.perform(post("/api/receipts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(receipt)))
                .andExpect(status().isBadRequest());

        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setYear(null);

        // Create the Receipt, which fails.

        restReceiptMockMvc.perform(post("/api/receipts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(receipt)))
                .andExpect(status().isBadRequest());

        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setMonth(null);

        // Create the Receipt, which fails.

        restReceiptMockMvc.perform(post("/api/receipts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(receipt)))
                .andExpect(status().isBadRequest());

        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReceipts() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);

        // Get all the receipts
        restReceiptMockMvc.perform(get("/api/receipts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(receipt.getId().intValue())))
                .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
                .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)));
    }

    @Test
    @Transactional
    public void getReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);

        // Get the receipt
        restReceiptMockMvc.perform(get("/api/receipts/{id}", receipt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(receipt.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH));
    }

    @Test
    @Transactional
    public void getNonExistingReceipt() throws Exception {
        // Get the receipt
        restReceiptMockMvc.perform(get("/api/receipts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceipt() throws Exception {
        // Initialize the database
        receiptService.save(receipt);

        int databaseSizeBeforeUpdate = receiptRepository.findAll().size();

        // Update the receipt
        Receipt updatedReceipt = new Receipt();
        updatedReceipt.setId(receipt.getId());
        updatedReceipt.setPath(UPDATED_PATH);
        updatedReceipt.setYear(UPDATED_YEAR);
        updatedReceipt.setMonth(UPDATED_MONTH);

        restReceiptMockMvc.perform(put("/api/receipts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedReceipt)))
                .andExpect(status().isOk());

        // Validate the Receipt in the database
        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeUpdate);
        Receipt testReceipt = receipts.get(receipts.size() - 1);
        assertThat(testReceipt.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testReceipt.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testReceipt.getMonth()).isEqualTo(UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void deleteReceipt() throws Exception {
        // Initialize the database
        receiptService.save(receipt);

        int databaseSizeBeforeDelete = receiptRepository.findAll().size();

        // Get the receipt
        restReceiptMockMvc.perform(delete("/api/receipts/{id}", receipt.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Receipt> receipts = receiptRepository.findAll();
        assertThat(receipts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
