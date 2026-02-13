package com.neversion.panel.sservicedetail.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.sservicedetail.application.port.in.GetSserviceDetailUseCase;
import com.neversion.panel.sservicedetail.domain.model.SserviceDetail;
import com.neversion.panel.sservicedetail.domain.port.out.SserviceDetailRepositoryPort;

@Service
public class GetSserviceDetailService implements GetSserviceDetailUseCase {
    private final SserviceDetailRepositoryPort sserviceDetailRepositoryPort;

    public GetSserviceDetailService(SserviceDetailRepositoryPort sserviceDetailRepositoryPort) {
        this.sserviceDetailRepositoryPort = sserviceDetailRepositoryPort;
    }

    @Override
    public SserviceDetail getById(Long id) {
        return sserviceDetailRepositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("SserviceDetail with id " + id + " not found"));
    }

    @Override
    public List<SserviceDetail> getAll() {
        return sserviceDetailRepositoryPort.findAll();
    }

    @Override
    public List<SserviceDetail> getByServiceName(String serviceName) {
        return sserviceDetailRepositoryPort.findByServiceName(serviceName);
    }

    @Override
    public List<SserviceDetail> getByCategoryName(String categoryName) {
        return sserviceDetailRepositoryPort.findByCategoryName(categoryName);
    }
}
