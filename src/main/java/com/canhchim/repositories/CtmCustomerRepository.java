package com.canhchim.repositories;

import com.canhchim.models.CtmCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CtmCustomerRepository extends JpaRepository<CtmCustomer, Long> {
    Optional<CtmCustomer> findByCustomerName(String customerName);

}
