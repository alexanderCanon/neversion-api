package com.neversion.panel.credential.infrastructure.adapters.out;

import com.neversion.panel.sservicedetail.infrastructure.adapters.out.SserviceDetailEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "credentials")
@Getter
public class CredentialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "email")
    String email;

    @Column(name = "pass")
    String pass;

    @Column(name = "is_active")
    Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_details_id", insertable = false, updatable = false)
    SserviceDetailEntity serviceDetail;

    @Column(name = "service_details_id")
    Long serviceDetailsId;

    public CredentialEntity() {}

    public CredentialEntity(Long id, String email, String pass, Boolean isActive, Long serviceDetailsId) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.isActive = isActive;
        this.serviceDetailsId = serviceDetailsId;
    }
}
