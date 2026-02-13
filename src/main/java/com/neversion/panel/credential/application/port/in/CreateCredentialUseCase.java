package com.neversion.panel.credential.application.port.in;

import com.neversion.panel.credential.domain.model.Credential;

public interface CreateCredentialUseCase {
    Credential create(Credential credential);
}
