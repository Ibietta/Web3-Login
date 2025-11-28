package com.example.multipagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.multipagos.model.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.id = :companyId")
    Optional<Company> findByIdAndTenant(Long companyId);
}

