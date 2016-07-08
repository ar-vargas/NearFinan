package com.nearsoft.neardocs.service;

import com.nearsoft.neardocs.domain.Receipt;
import com.nearsoft.neardocs.repository.ReceiptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Receipt.
 */
@Service
@Transactional
public class ReceiptService {

    private final Logger log = LoggerFactory.getLogger(ReceiptService.class);

    @Inject
    private ReceiptRepository receiptRepository;

    /**
     * Save a receipt.
     *
     * @param receipt the entity to save
     * @return the persisted entity
     */
    public Receipt save(Receipt receipt) {
        log.debug("Request to save Receipt : {}", receipt);
        Receipt result = receiptRepository.save(receipt);
        return result;
    }

    /**
     *  Get all the receipts.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Receipt> findAll() {
        log.debug("Request to get all Receipts");
        List<Receipt> result = receiptRepository.findAll();
        return result;
    }

    /**
     *  Get one receipt by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Receipt findOne(Long id) {
        log.debug("Request to get Receipt : {}", id);
        Receipt receipt = receiptRepository.findOne(id);
        return receipt;
    }

    /**
     *  Delete the  receipt by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Receipt : {}", id);
        receiptRepository.delete(id);
    }

    /**
     *  Return all receipts by Id
     *n
     */
    public List<Receipt> findReceiptsByNearsoftnianId(String email) {
        log.debug("Request find Receipts for Nearsoftnian");
        List<Receipt> result = receiptRepository.findReceiptsByNearsoftnianId(email);
        return result;
    }
}
