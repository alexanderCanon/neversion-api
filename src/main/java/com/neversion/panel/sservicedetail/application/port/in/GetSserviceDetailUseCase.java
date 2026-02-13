package com.neversion.panel.sservicedetail.application.port.in;

import java.util.List;

import com.neversion.panel.sservicedetail.domain.model.SserviceDetail;

public interface GetSserviceDetailUseCase {
    SserviceDetail getById(Long id);
    List<SserviceDetail> getAll();
    List<SserviceDetail> getByServiceName(String serviceName);
    List<SserviceDetail> getByCategoryName(String categoryName);
}
