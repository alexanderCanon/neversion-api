package com.neversion.panel.reservation.infrastructure.adapters.out;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataGuestUserRepository extends JpaRepository<GuestUserEntity, UUID> {

    Optional<GuestUserEntity> findByEmail(String email);
}
