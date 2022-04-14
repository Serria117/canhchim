package com.canhchim.repositories;

import com.canhchim.models.CtmCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CtmCustomerRepository extends JpaRepository<CtmCustomer, Long>
{

    Optional<CtmCustomer> findByCustomerName(String customerName);

    @Query("select c from CtmCustomer c where c.phone = ?1")
    Optional<CtmCustomer> findByPhone(String phone);

}
