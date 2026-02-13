package com.neversion.panel.sservice.domain.port.out;

import java.util.Optional;

import java.util.List;
import com.neversion.panel.sservice.domain.model.Sservice;

public interface SserviceRepositoryPort {
    Sservice save(Sservice sservice);
    Optional<Sservice> findById(Integer id);
    Optional<Sservice> findByName(String name);
    List<Sservice> findAll();
}
