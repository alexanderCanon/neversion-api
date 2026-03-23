// package com.neversion.api.profile.application.service;

// import java.util.UUID;

// import org.springframework.stereotype.Service;

// import com.neversion.api.exception.ResourceNotFoundException;
// import
// com.neversion.api.profile.application.port.in.DeactivateProfileUseCase;
// import com.neversion.api.profile.domain.port.out.ProfileRepositoryPort;

// @Service
// public class DeactivateProfileService implements DeactivateProfileUseCase {
// private final ProfileRepositoryPort profileRepositoryPort;

// public DeactivateProfileService(ProfileRepositoryPort profileRepositoryPort)
// {
// this.profileRepositoryPort = profileRepositoryPort;
// }

// @Override
// public void deactivate(UUID id) {
// profileRepositoryPort.findById(id)
// .orElseThrow(() -> new ResourceNotFoundException("Profile with id " + id + "
// not found"));
// profileRepositoryPort.deactivate(id);
// }
// }