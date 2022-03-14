package com.canhchim.repositories;

import com.canhchim.models.ShpUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShpUserRepository extends JpaRepository<ShpUser, Long>
{
    Optional<ShpUser> findByUserName(String userName);

}
