// package com.neversion.panel.profile.application.service;

// import java.util.List;
// import java.util.UUID;

// import org.springframework.stereotype.Service;

// import com.neversion.panel.exception.ResourceNotFoundException;
// import com.neversion.panel.profile.application.port.in.GetProfileUseCase;
// import com.neversion.panel.profile.domain.model.Profile;
// import com.neversion.panel.profile.domain.port.out.ProfileRepositoryPort;

// @Service
// public class GetProfileService implements GetProfileUseCase {
// private final ProfileRepositoryPort profileRepositoryPort;

// public GetProfileService(ProfileRepositoryPort profileRepositoryPort) {
// this.profileRepositoryPort = profileRepositoryPort;
// }

// @Override
// public Profile getById(UUID id) {
// return profileRepositoryPort.findById(id)
// .orElseThrow(() -> new ResourceNotFoundException("Profile with id " + id + "
// not found"));
// }

// @Override
// public Profile getByEmail(String email) {
// return profileRepositoryPort.findByEmail(email)
// .orElseThrow(() -> new ResourceNotFoundException("Profile with email " +
// email + " not found"));
// }

// @Override
// public List<Profile> getByName(String name) {
// return profileRepositoryPort.findByName(name);
// }

// @Override
// public List<Profile> getByIsActive(Boolean isActive) {
// return profileRepositoryPort.findByIsActive(isActive);
// }

// @Override
// public List<Profile> getAll() {
// return profileRepositoryPort.findAll();
// }
// }
