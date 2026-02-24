package com.neversion.panel.sserviceitem.application.port.in;

import java.util.List;

import com.neversion.panel.sserviceitem.domain.model.SserviceItem;

public interface GetSserviceItemUseCase {
    SserviceItem getById(Long id);
    List<SserviceItem> getAll();
    List<SserviceItem> getByServiceName(String serviceName);
    List<SserviceItem> getByAccountType(String accountType);     
}
