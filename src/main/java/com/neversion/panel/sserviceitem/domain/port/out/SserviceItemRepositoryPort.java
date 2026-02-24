package com.neversion.panel.sserviceitem.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.neversion.panel.sserviceitem.domain.model.SserviceItem;

public interface SserviceItemRepositoryPort {
    SserviceItem save(SserviceItem sserviceDetail);
    Optional<SserviceItem> findById(Long id);
    List<SserviceItem> findAll();
    List<SserviceItem> findByServiceName(String serviceName);
    List<SserviceItem> findByAccountType(String accountType);
}
