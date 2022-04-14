package com.canhchim.repositories;

import com.canhchim.models.ShpShopEmployee;
import com.canhchim.models.ShpShopEmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShpShopEmployeeRepository extends JpaRepository<ShpShopEmployee, ShpShopEmployeeId>
{

}
