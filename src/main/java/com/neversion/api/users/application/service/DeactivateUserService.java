// package com.neversion.api.users.application.service;

// import java.util.UUID;

// import org.springframework.stereotype.Service;

// import com.neversion.api.exception.ResourceNotFoundException;
// import
// com.neversion.api.users.application.port.in.DeactivateUserUseCase;
// import com.neversion.api.users.domain.port.out.UserRepositoryPort;

// @Service
// public class DeactivateUserService implements DeactivateUserUseCase {
// private final UserRepositoryPort userRepositoryPort;

// public DeactivateUserService(UserRepositoryPort userRepositoryPort)
// {
// this.userRepositoryPort = userRepositoryPort;
// }

// @Override
// public void deactivate(UUID id) {
// userRepositoryPort.findById(id)
// .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + "
// not found"));
// userRepositoryPort.deactivate(id);
// }
// }