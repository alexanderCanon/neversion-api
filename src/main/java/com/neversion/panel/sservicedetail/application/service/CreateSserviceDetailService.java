package com.neversion.panel.sservicedetail.application.service;

import org.springframework.stereotype.Service;

import com.neversion.panel.sservicedetail.application.port.in.CreateSserviceDetailUseCase;
import com.neversion.panel.sservicedetail.domain.model.SserviceDetail;
import com.neversion.panel.sservicedetail.domain.port.out.SserviceDetailRepositoryPort;

@Service
public class CreateSserviceDetailService implements CreateSserviceDetailUseCase {
    private final SserviceDetailRepositoryPort sserviceDetailRepositoryPort;

    public CreateSserviceDetailService(SserviceDetailRepositoryPort sserviceDetailRepositoryPort) {
        this.sserviceDetailRepositoryPort = sserviceDetailRepositoryPort;
    }

    @Override
    public SserviceDetail create(SserviceDetail sserviceDetail) {
        return sserviceDetailRepositoryPort.save(sserviceDetail);
    }
}
