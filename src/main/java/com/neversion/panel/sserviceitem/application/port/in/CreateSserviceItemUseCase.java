package com.neversion.panel.sserviceitem.application.port.in;

import com.neversion.panel.sserviceitem.domain.model.SserviceItem;

public interface CreateSserviceItemUseCase {
    SserviceItem create(Integer serviceId, SserviceItem sserviceDetail);
}
