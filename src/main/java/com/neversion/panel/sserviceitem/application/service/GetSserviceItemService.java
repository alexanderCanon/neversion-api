package com.neversion.panel.sserviceitem.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.sserviceitem.application.port.in.GetSserviceItemUseCase;
import com.neversion.panel.sserviceitem.domain.model.SserviceItem;
import com.neversion.panel.sserviceitem.domain.port.out.SserviceItemRepositoryPort;

@Service
public class GetSserviceItemService implements GetSserviceItemUseCase {
    private final SserviceItemRepositoryPort sserviceDetailRepositoryPort;

    public GetSserviceItemService(SserviceItemRepositoryPort sserviceDetailRepositoryPort) {
        this.sserviceDetailRepositoryPort = sserviceDetailRepositoryPort;
    }

    @Override
    public SserviceItem getById(Long id) {
        return sserviceDetailRepositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("SserviceDetail with id " + id + " not found"));
    }

    @Override
    public List<SserviceItem> getAll() {
        return sserviceDetailRepositoryPort.findAll();
    }

    @Override
    public List<SserviceItem> getByServiceName(String serviceName) {
        return sserviceDetailRepositoryPort.findByServiceName(serviceName);
    }

    @Override
    public List<SserviceItem> getByAccountType(String accountType) {
        return sserviceDetailRepositoryPort.findByAccountType(accountType);
    }
}
