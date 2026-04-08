// package com.neversion.api.users.infrastructure.adapters.out;

// import java.util.UUID;

// import com.neversion.api.infrastructure.AuditableEntity;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import lombok.Getter;

// @Entity
// @Table(name = "users")
// @Getter
// public class UserEntity extends AuditableEntity {

// @Id
// @Column(name = "id")
// UUID id;

// @Column(name = "name")
// String name;

// @Column(name = "lastname")
// String lastname;

// @Column(name = "email")
// String email;

// @Column(name = "phone")
// String phone;

// public UserEntity() {
// }

// public UserEntity(UUID id, String name, String lastname, String email,
// String phone) {
// this.id = id;
// this.name = name;
// this.lastname = lastname;
// this.email = email;
// this.phone = phone;
// }
// }
