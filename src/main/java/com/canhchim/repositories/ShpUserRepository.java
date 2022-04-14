package com.canhchim.repositories;

import com.canhchim.models.ShpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShpUserRepository extends JpaRepository<ShpUser, Long>
{
    Optional<ShpUser> findByUserName(String userName);

    boolean existsByUserName (String userName);

    boolean existsByPhone (String phone);

    boolean existsByCitizenIdentity (String citizenIdentity);

    boolean existsByEmail (String email);



}
