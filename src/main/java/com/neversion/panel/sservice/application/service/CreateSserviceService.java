package com.neversion.panel.sservice.application.service;

import org.springframework.stereotype.Service;

import com.neversion.panel.sservice.application.port.in.CreateSserviceUseCase;
import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.domain.port.out.SserviceRepositoryPort;

@Service
public class CreateSserviceService implements CreateSserviceUseCase {

    private final SserviceRepositoryPort sserviceRepositoryPort;

    public CreateSserviceService(SserviceRepositoryPort sserviceRepositoryPort) {
        this.sserviceRepositoryPort = sserviceRepositoryPort;
    }

    @Override
    public Sservice create(Sservice sservice) {
        return sserviceRepositoryPort.save(sservice);
    }
}
