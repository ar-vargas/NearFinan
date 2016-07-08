package com.nearsoft.neardocs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nearsoft.neardocs.domain.Receipt;
import com.nearsoft.neardocs.domain.User;
import com.nearsoft.neardocs.security.SecurityUtils;
import com.nearsoft.neardocs.service.ReceiptService;
import com.nearsoft.neardocs.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Receipt.
 */
@RestController
@RequestMapping("/api")
public class ReceiptResource {

    private final Logger log = LoggerFactory.getLogger(ReceiptResource.class);

    @Inject
    private ReceiptService receiptService;


    /**
     * POST  /receipts : Create a new receipt.
     *
     * @param receipt the receipt to create
     * @return the ResponseEntity with status 201 (Created) and with body the new receipt, or with status 400 (Bad Request) if the receipt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/receipts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Receipt> createReceipt(@Valid @RequestBody Receipt receipt) throws URISyntaxException {
        log.debug("REST request to save Receipt : {}", receipt);
        if (receipt.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("receipt", "idexists", "A new receipt cannot already have an ID")).body(null);
        }
        Receipt result = receiptService.save(receipt);
        return ResponseEntity.created(new URI("/api/receipts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("receipt", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /receipts : Updates an existing receipt.
     *
     * @param receipt the receipt to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated receipt,
     * or with status 400 (Bad Request) if the receipt is not valid,
     * or with status 500 (Internal Server Error) if the receipt couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/receipts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Receipt> updateReceipt(@Valid @RequestBody Receipt receipt) throws URISyntaxException {
        log.debug("REST request to update Receipt : {}", receipt);
        if (receipt.getId() == null) {
            return createReceipt(receipt);
        }
        Receipt result = receiptService.save(receipt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("receipt", receipt.getId().toString()))
            .body(result);
    }

    /**
     * GET  /receipts : get all the receipts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     */
    @RequestMapping(value = "/receipts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Receipt> getAllReceipts() {
        log.debug("REST request to get all Receipts for Id");
        //return receiptService.findAll();
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = SecurityUtils.getCurrentUserLogin();
        return receiptService.findReceiptsByNearsoftnianId(email);
    }

    /**
     * GET  /receipts/:id : get the "id" receipt.
     *
     * @param id the id of the receipt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the receipt, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/receipts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Receipt> getReceipt(@PathVariable Long id) {
        log.debug("REST request to get Receipt : {}", id);
        Receipt receipt = receiptService.findOne(id);
        return Optional.ofNullable(receipt)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /receipts/:id : delete the "id" receipt.
     *
     * @param id the id of the receipt to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/receipts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteReceipt(@PathVariable Long id) {
        log.debug("REST request to delete Receipt : {}", id);
        receiptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("receipt", id.toString())).build();
    }

}
