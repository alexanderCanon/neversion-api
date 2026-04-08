// package com.neversion.api.users.infrastructure.adapters.out.mapper;

// import org.springframework.stereotype.Component;

// import com.neversion.api.users.domain.model.User;
// import com.neversion.api.users.infrastructure.adapters.out.UserEntity;

// @Component
// public class UserPersistenceMapper {

// public User toDomain(UserEntity entity) {
// return new User(
// entity.getId(),
// entity.getName(),
// entity.getLastname(),
// entity.getEmail(),
// entity.getPhone(),
// entity.getIsActive(),
// entity.getCreatedAt() != null ? entity.getCreatedAt().toInstant() : null);
// }
// }
