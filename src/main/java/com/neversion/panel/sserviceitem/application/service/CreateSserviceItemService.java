package com.neversion.panel.sserviceitem.application.service;

import org.springframework.stereotype.Service;

import com.neversion.panel.sserviceitem.application.port.in.CreateSserviceItemUseCase;
import com.neversion.panel.sserviceitem.domain.model.SserviceItem;
import com.neversion.panel.sserviceitem.domain.port.out.SserviceItemRepositoryPort;

@Service
public class CreateSserviceItemService implements CreateSserviceItemUseCase {
    private final SserviceItemRepositoryPort sserviceDetailRepositoryPort;

    public CreateSserviceItemService(SserviceItemRepositoryPort sserviceDetailRepositoryPort) {
        this.sserviceDetailRepositoryPort = sserviceDetailRepositoryPort;
    }

    @Override
    public SserviceItem create(SserviceItem sserviceDetail) {
        return sserviceDetailRepositoryPort.save(sserviceDetail);
    }
}
