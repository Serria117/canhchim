package com.canhchim.repositories;

import com.canhchim.models.PrdProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<PrdProductType, Long>
{
}
