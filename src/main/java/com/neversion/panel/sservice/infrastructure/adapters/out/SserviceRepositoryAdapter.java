package com.neversion.panel.sservice.infrastructure.adapters.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SserviceRepositoryAdapter extends JpaRepository<SserviceEntity, Integer>{
    Optional<SserviceEntity> findByName(String name);

}
