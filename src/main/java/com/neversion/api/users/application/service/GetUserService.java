// package com.neversion.api.users.application.service;

// import java.util.List;
// import java.util.UUID;

// import org.springframework.stereotype.Service;

// import com.neversion.api.exception.ResourceNotFoundException;
// import com.neversion.api.users.application.port.in.GetUserUseCase;
// import com.neversion.api.users.domain.model.User;
// import com.neversion.api.users.domain.port.out.UserRepositoryPort;

// @Service
// public class GetUserService implements GetUserUseCase {
// private final UserRepositoryPort userRepositoryPort;

// public GetUserService(UserRepositoryPort userRepositoryPort) {
// this.userRepositoryPort = userRepositoryPort;
// }

// @Override
// public User getById(UUID id) {
// return userRepositoryPort.findById(id)
// .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + "
// not found"));
// }

// @Override
// public User getByEmail(String email) {
// return userRepositoryPort.findByEmail(email)
// .orElseThrow(() -> new ResourceNotFoundException("User with email " +
// email + " not found"));
// }

// @Override
// public List<User> getByName(String name) {
// return userRepositoryPort.findByName(name);
// }

// @Override
// public List<User> getByIsActive(Boolean isActive) {
// return userRepositoryPort.findByIsActive(isActive);
// }

// @Override
// public List<User> getAll() {
// return userRepositoryPort.findAll();
// }
// }
