// package com.neversion.api.users.infrastructure.adapters.out;

// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.transaction.annotation.Transactional;

// public interface UserRepositoryAdapter extends
// JpaRepository<UserEntity, UUID> {
// Optional<UserEntity> findByEmail(String email);

// List<UserEntity> findByName(String name);

// List<UserEntity> findByIsActive(Boolean isActive);

// @Modifying
// @Transactional
// @Query("UPDATE UserEntity p SET p.isActive = false WHERE p.id = :id")
// void deactivate(@Param("id") UUID id);
// }
