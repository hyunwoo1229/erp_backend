package com.erp.partner.repository;

import com.erp.partner.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    boolean existsByCode(String code);
}