package com.neversion.panel.credential.application.port.in;

import java.util.List;

import com.neversion.panel.credential.domain.model.Credential;

public interface GetCredentialUseCase {
    Credential getById(Long id);
    List<Credential> getByEmail(String email);
    List<Credential> getByIsActive(Boolean isActive);
    List<Credential> getAll();
}
