package com.neversion.panel.profile.infrastructure.adapters.out;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "profiles")
@Getter
public class ProfileEntity {

    @Id
    @Column(name = "id")
    UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "lastname")
    String lastname;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "is_active")
    Boolean isActive;

    @Column(name = "created_at")
    Instant createdAt;

    public ProfileEntity() {}

    public ProfileEntity(UUID id, String name, String lastname, String email,
        String phone, Boolean isActive, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
}
