package com.canhchim.repositories;

import com.canhchim.models.PrdOrderDetail;
import com.canhchim.models.PrdOrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrdOrderDetailRepository extends JpaRepository<PrdOrderDetail, PrdOrderDetailId>
{
}
