package com.neversion.panel.sserviceitem.application.service;

import org.springframework.stereotype.Service;

import com.neversion.panel.sservice.application.port.in.GetSserviceUseCase;
import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sserviceitem.application.port.in.CreateSserviceItemUseCase;
import com.neversion.panel.sserviceitem.domain.model.SserviceItem;
import com.neversion.panel.sserviceitem.domain.port.out.SserviceItemRepositoryPort;

@Service
public class CreateSserviceItemService implements CreateSserviceItemUseCase {
    private final SserviceItemRepositoryPort sserviceDetailRepositoryPort;
    private final GetSserviceUseCase getSserviceUseCase;

    public CreateSserviceItemService(
            SserviceItemRepositoryPort sserviceDetailRepositoryPort,
            GetSserviceUseCase getSserviceUseCase) {
        this.sserviceDetailRepositoryPort = sserviceDetailRepositoryPort;
        this.getSserviceUseCase = getSserviceUseCase;
    }

    @Override
    public SserviceItem create(Integer serviceId, SserviceItem sserviceDetail) {
        Sservice sservice = getSserviceUseCase.getById(serviceId);
        sserviceDetail.setSservice(sservice);
        return sserviceDetailRepositoryPort.save(sserviceDetail);
    }
}
