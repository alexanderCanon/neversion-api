// package com.neversion.api.users.infrastructure.adapters.out;

// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

// import org.springframework.stereotype.Repository;

// import com.neversion.api.users.domain.model.User;
// import com.neversion.api.users.domain.port.out.UserRepositoryPort;
// import
// com.neversion.api.users.infrastructure.adapters.out.mapper.UserPersistenceMapper;

// @Repository
// public class JpaUserAdapter implements UserRepositoryPort {
// private final UserRepositoryAdapter userRepositoryAdapter;
// private final UserPersistenceMapper userPersistenceMapper;

// public JpaUserAdapter(UserRepositoryAdapter userRepositoryAdapter,
// UserPersistenceMapper userPersistenceMapper) {
// this.userRepositoryAdapter = userRepositoryAdapter;
// this.userPersistenceMapper = userPersistenceMapper;
// }

// @Override
// public Optional<User> findById(UUID id) {
// return userRepositoryAdapter.findById(id)
// .map(userPersistenceMapper::toDomain);
// }

// @Override
// public Optional<User> findByEmail(String email) {
// return userRepositoryAdapter.findByEmail(email)
// .map(userPersistenceMapper::toDomain);
// }

// @Override
// public List<User> findByName(String name) {
// return userRepositoryAdapter.findByName(name)
// .stream()
// .map(userPersistenceMapper::toDomain)
// .toList();
// }

// @Override
// public List<User> findByIsActive(Boolean isActive) {
// return userRepositoryAdapter.findByIsActive(isActive)
// .stream()
// .map(userPersistenceMapper::toDomain)
// .toList();
// }

// @Override
// public List<User> findAll() {
// return userRepositoryAdapter.findAll()
// .stream()
// .map(userPersistenceMapper::toDomain)
// .toList();
// }

// @Override
// public void deactivate(UUID id) {
// userRepositoryAdapter.deactivate(id);
// }
// }
