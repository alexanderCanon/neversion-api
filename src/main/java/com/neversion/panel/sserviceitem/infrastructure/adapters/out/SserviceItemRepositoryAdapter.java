package com.neversion.panel.sserviceitem.infrastructure.adapters.out;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SserviceItemRepositoryAdapter extends JpaRepository<SserviceItemEntity, Long> {
    List<SserviceItemEntity> findByServiceName(String serviceName);

    List<SserviceItemEntity> findByAccountType(String accountType);
}
