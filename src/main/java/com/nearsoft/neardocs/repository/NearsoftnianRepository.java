package com.nearsoft.neardocs.repository;

import com.nearsoft.neardocs.domain.Nearsoftnian;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Nearsoftnian entity.
 */
@SuppressWarnings("unused")
public interface NearsoftnianRepository extends JpaRepository<Nearsoftnian,Long> {

}
