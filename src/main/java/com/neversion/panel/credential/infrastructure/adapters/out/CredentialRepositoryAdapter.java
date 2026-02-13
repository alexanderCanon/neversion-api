package com.neversion.panel.credential.infrastructure.adapters.out;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CredentialRepositoryAdapter extends JpaRepository<CredentialEntity, Long> {
    List<CredentialEntity> findByEmail(String email);
    List<CredentialEntity> findByIsActive(Boolean isActive);

    @Modifying
    @Transactional
    @Query("UPDATE CredentialEntity c SET c.isActive = false WHERE c.id = :id")
    void deactivate(@Param("id") Long id);
}
