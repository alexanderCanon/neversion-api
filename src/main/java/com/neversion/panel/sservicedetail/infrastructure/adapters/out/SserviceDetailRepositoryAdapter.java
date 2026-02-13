package com.neversion.panel.sservicedetail.infrastructure.adapters.out;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SserviceDetailRepositoryAdapter extends JpaRepository<SserviceDetailEntity, Long> {
    List<SserviceDetailEntity> findByServiceName(String serviceName);
    List<SserviceDetailEntity> findByCategoryName(String categoryName);
}
