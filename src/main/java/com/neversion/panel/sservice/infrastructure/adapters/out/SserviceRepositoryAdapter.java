package com.neversion.panel.sservice.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversion.panel.sservice.domain.model.enums.CategoryType;

public interface SserviceRepositoryAdapter extends JpaRepository<SserviceEntity, Integer> {
    Optional<SserviceEntity> findByName(String name);

    List<SserviceEntity> findByCategory(CategoryType category);

}
