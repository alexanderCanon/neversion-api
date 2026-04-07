package com.neversion.api.inventory.application.port.in;

import java.util.List;
import java.util.UUID;

import com.neversion.api.inventory.domain.model.Service;

public interface ServiceUseCase {
    Service create(Service service);
    Service update(UUID uuid, Service service);
    Service getById(UUID uuid);
    List<Service> getAll();
    void delete(UUID uuid);
}
