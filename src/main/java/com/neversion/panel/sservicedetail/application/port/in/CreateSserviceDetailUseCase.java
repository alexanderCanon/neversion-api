package com.neversion.panel.sservicedetail.application.port.in;

import com.neversion.panel.sservicedetail.domain.model.SserviceDetail;

public interface CreateSserviceDetailUseCase {
    SserviceDetail create(SserviceDetail sserviceDetail);
}
