package com.nearsoft.neardocs.service;

import com.nearsoft.neardocs.domain.Nearsoftnian;
import com.nearsoft.neardocs.repository.NearsoftnianRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Nearsoftnian.
 */
@Service
@Transactional
public class NearsoftnianService {

    private final Logger log = LoggerFactory.getLogger(NearsoftnianService.class);
    
    @Inject
    private NearsoftnianRepository nearsoftnianRepository;
    
    /**
     * Save a nearsoftnian.
     * 
     * @param nearsoftnian the entity to save
     * @return the persisted entity
     */
    public Nearsoftnian save(Nearsoftnian nearsoftnian) {
        log.debug("Request to save Nearsoftnian : {}", nearsoftnian);
        Nearsoftnian result = nearsoftnianRepository.save(nearsoftnian);
        return result;
    }

    /**
     *  Get all the nearsoftnians.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Nearsoftnian> findAll() {
        log.debug("Request to get all Nearsoftnians");
        List<Nearsoftnian> result = nearsoftnianRepository.findAll();
        return result;
    }

    /**
     *  Get one nearsoftnian by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Nearsoftnian findOne(Long id) {
        log.debug("Request to get Nearsoftnian : {}", id);
        Nearsoftnian nearsoftnian = nearsoftnianRepository.findOne(id);
        return nearsoftnian;
    }

    /**
     *  Delete the  nearsoftnian by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Nearsoftnian : {}", id);
        nearsoftnianRepository.delete(id);
    }
}
