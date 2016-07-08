package com.nearsoft.neardocs.repository;


import com.nearsoft.neardocs.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
@SuppressWarnings("unused")
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {
    @Query(value="Select rs.* from receipt rs where rs.owner_id= (select ns.id from Nearsoftnian ns where email=:email)", nativeQuery = true)
    List<Receipt> findReceiptsByNearsoftnianId(@Param("email") String email);

    @Query(value="Select rs.* from receipt rs where rs.owner_id= (select ns.id from Nearsoftnian ns where email=:email) and rs.id=:id", nativeQuery = true)
    Receipt findOne(@Param("email") String email, @Param("id") Long id);
}
