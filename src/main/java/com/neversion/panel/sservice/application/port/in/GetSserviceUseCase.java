package com.neversion.panel.sservice.application.port.in;

import java.util.List;

import com.neversion.panel.sservice.domain.model.Sservice;

public interface GetSserviceUseCase {
    Sservice getById(Integer id);
    Sservice getByName(String name);
    List<Sservice> getAll();
}
