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
        Sservice sservice = sserviceRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sservice with id " + id + " not found"));
        return sservice;
    }

    @Override
    public Sservice getByName(String name) {
        Sservice sservice = sserviceRepositoryPort.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Sservice with name " + name + " not found"));
        return sservice;
    }

    @Override
    public List<Sservice> getAll() {
        List<Sservice> sservices = sserviceRepositoryPort.findAll();
        return sservices;
    }

    // @Override
    // public List<Sservice> getByCategory() {
    // return sserviceRepositoryPort.findByCategory();
    // }

}
