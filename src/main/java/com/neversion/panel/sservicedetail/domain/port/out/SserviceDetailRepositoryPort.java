package com.neversion.panel.sservicedetail.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.neversion.panel.sservicedetail.domain.model.SserviceDetail;

public interface SserviceDetailRepositoryPort {
    SserviceDetail save(SserviceDetail sserviceDetail);
    Optional<SserviceDetail> findById(Long id);
    List<SserviceDetail> findAll();
    List<SserviceDetail> findByServiceName(String serviceName);
    List<SserviceDetail> findByCategoryName(String categoryName);
}
