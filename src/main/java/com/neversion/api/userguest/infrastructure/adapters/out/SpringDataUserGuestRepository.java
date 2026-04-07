package com.neversion.api.userguest.infrastructure.adapters.out;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SpringDataUserGuestRepository extends JpaRepository<UserGuestEntity, UUID> {
    List<UserGuestEntity> findByName(String name);

    List<UserGuestEntity> findByPhone(String phone);

    @Modifying
    @Transactional
    @Query("UPDATE UserGuestEntity u SET u.isActive = false WHERE u.id = :id")
    void deactivate(@Param("id") UUID id);
}
