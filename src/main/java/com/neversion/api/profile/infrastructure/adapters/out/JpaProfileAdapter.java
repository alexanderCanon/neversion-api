// package com.neversion.api.profile.infrastructure.adapters.out;

// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

// import org.springframework.stereotype.Repository;

// import com.neversion.api.profile.domain.model.Profile;
// import com.neversion.api.profile.domain.port.out.ProfileRepositoryPort;
// import
// com.neversion.api.profile.infrastructure.adapters.out.mapper.ProfilePersistenceMapper;

// @Repository
// public class JpaProfileAdapter implements ProfileRepositoryPort {
// private final ProfileRepositoryAdapter profileRepositoryAdapter;
// private final ProfilePersistenceMapper profilePersistenceMapper;

// public JpaProfileAdapter(ProfileRepositoryAdapter profileRepositoryAdapter,
// ProfilePersistenceMapper profilePersistenceMapper) {
// this.profileRepositoryAdapter = profileRepositoryAdapter;
// this.profilePersistenceMapper = profilePersistenceMapper;
// }

// @Override
// public Optional<Profile> findById(UUID id) {
// return profileRepositoryAdapter.findById(id)
// .map(profilePersistenceMapper::toDomain);
// }

// @Override
// public Optional<Profile> findByEmail(String email) {
// return profileRepositoryAdapter.findByEmail(email)
// .map(profilePersistenceMapper::toDomain);
// }

// @Override
// public List<Profile> findByName(String name) {
// return profileRepositoryAdapter.findByName(name)
// .stream()
// .map(profilePersistenceMapper::toDomain)
// .toList();
// }

// @Override
// public List<Profile> findByIsActive(Boolean isActive) {
// return profileRepositoryAdapter.findByIsActive(isActive)
// .stream()
// .map(profilePersistenceMapper::toDomain)
// .toList();
// }

// @Override
// public List<Profile> findAll() {
// return profileRepositoryAdapter.findAll()
// .stream()
// .map(profilePersistenceMapper::toDomain)
// .toList();
// }

// @Override
// public void deactivate(UUID id) {
// profileRepositoryAdapter.deactivate(id);
// }
// }
