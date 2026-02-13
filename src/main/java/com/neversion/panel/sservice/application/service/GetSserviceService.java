package com.neversion.panel.sservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.sservice.application.port.in.GetSserviceUseCase;
import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.domain.port.out.SserviceRepositoryPort;

@Service
public class GetSserviceService implements GetSserviceUseCase {
    private final SserviceRepositoryPort sserviceRepositoryPort;
    public GetSserviceService(SserviceRepositoryPort sserviceRepositoryPort) {
        this.sserviceRepositoryPort = sserviceRepositoryPort;
    }

    @Override
    public Sservice getById(Integer id) {
        return sserviceRepositoryPort.findById(id).
        orElseThrow(() -> new ResourceNotFoundException("Sservice with id " + id + " not found"));
    }

    @Override
    public Sservice getByName(String name) {
        return sserviceRepositoryPort.findByName(name).
        orElseThrow(() -> new ResourceNotFoundException("Sservice with name " + name + " not found"));
    }

    @Override
    public List<Sservice> getAll() {
        return sserviceRepositoryPort.findAll();
    }

}   
