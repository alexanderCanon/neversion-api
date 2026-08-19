package com.neversion.api.user.application.service;

import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.user.application.port.in.GetCurrentUserContextUseCase;
import com.neversion.api.user.domain.model.CurrentUserContextResult;
import com.neversion.api.user.domain.model.User;
import com.neversion.api.user.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetCurrentUserContextService implements GetCurrentUserContextUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public GetCurrentUserContextService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public CurrentUserContextResult get(String callerExternalId) {
        User user = userRepositoryPort.findByExternalId(callerExternalId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found for externalId: " + callerExternalId));

        return new CurrentUserContextResult(
                user.getExternalId(),
                user.getRole());
    }
}
