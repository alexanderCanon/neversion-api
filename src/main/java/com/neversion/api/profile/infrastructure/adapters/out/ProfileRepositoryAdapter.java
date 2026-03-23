// package com.neversion.api.profile.infrastructure.adapters.out;

// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.transaction.annotation.Transactional;

// public interface ProfileRepositoryAdapter extends
// JpaRepository<ProfileEntity, UUID> {
// Optional<ProfileEntity> findByEmail(String email);

// List<ProfileEntity> findByName(String name);

// List<ProfileEntity> findByIsActive(Boolean isActive);

// @Modifying
// @Transactional
// @Query("UPDATE ProfileEntity p SET p.isActive = false WHERE p.id = :id")
// void deactivate(@Param("id") UUID id);
// }
