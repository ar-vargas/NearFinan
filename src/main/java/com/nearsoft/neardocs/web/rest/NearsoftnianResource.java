package com.nearsoft.neardocs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nearsoft.neardocs.domain.Nearsoftnian;
import com.nearsoft.neardocs.service.NearsoftnianService;
import com.nearsoft.neardocs.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Nearsoftnian.
 */
@RestController
@RequestMapping("/api")
public class NearsoftnianResource {

    private final Logger log = LoggerFactory.getLogger(NearsoftnianResource.class);
        
    @Inject
    private NearsoftnianService nearsoftnianService;
    
    /**
     * POST  /nearsoftnians : Create a new nearsoftnian.
     *
     * @param nearsoftnian the nearsoftnian to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nearsoftnian, or with status 400 (Bad Request) if the nearsoftnian has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/nearsoftnians",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Nearsoftnian> createNearsoftnian(@Valid @RequestBody Nearsoftnian nearsoftnian) throws URISyntaxException {
        log.debug("REST request to save Nearsoftnian : {}", nearsoftnian);
        if (nearsoftnian.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("nearsoftnian", "idexists", "A new nearsoftnian cannot already have an ID")).body(null);
        }
        Nearsoftnian result = nearsoftnianService.save(nearsoftnian);
        return ResponseEntity.created(new URI("/api/nearsoftnians/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("nearsoftnian", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nearsoftnians : Updates an existing nearsoftnian.
     *
     * @param nearsoftnian the nearsoftnian to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nearsoftnian,
     * or with status 400 (Bad Request) if the nearsoftnian is not valid,
     * or with status 500 (Internal Server Error) if the nearsoftnian couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/nearsoftnians",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Nearsoftnian> updateNearsoftnian(@Valid @RequestBody Nearsoftnian nearsoftnian) throws URISyntaxException {
        log.debug("REST request to update Nearsoftnian : {}", nearsoftnian);
        if (nearsoftnian.getId() == null) {
            return createNearsoftnian(nearsoftnian);
        }
        Nearsoftnian result = nearsoftnianService.save(nearsoftnian);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("nearsoftnian", nearsoftnian.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nearsoftnians : get all the nearsoftnians.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nearsoftnians in body
     */
    @RequestMapping(value = "/nearsoftnians",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Nearsoftnian> getAllNearsoftnians() {
        log.debug("REST request to get all Nearsoftnians");
        return nearsoftnianService.findAll();
    }

    /**
     * GET  /nearsoftnians/:id : get the "id" nearsoftnian.
     *
     * @param id the id of the nearsoftnian to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nearsoftnian, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/nearsoftnians/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Nearsoftnian> getNearsoftnian(@PathVariable Long id) {
        log.debug("REST request to get Nearsoftnian : {}", id);
        Nearsoftnian nearsoftnian = nearsoftnianService.findOne(id);
        return Optional.ofNullable(nearsoftnian)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /nearsoftnians/:id : delete the "id" nearsoftnian.
     *
     * @param id the id of the nearsoftnian to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/nearsoftnians/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNearsoftnian(@PathVariable Long id) {
        log.debug("REST request to delete Nearsoftnian : {}", id);
        nearsoftnianService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("nearsoftnian", id.toString())).build();
    }

}
