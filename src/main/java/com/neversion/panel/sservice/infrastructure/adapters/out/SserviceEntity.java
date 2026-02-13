package com.neversion.panel.sservice.infrastructure.adapters.out;

import java.time.Instant;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "services")
@Getter
@SQLDelete(sql = "UPDATE services SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
public class SserviceEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "name")
    String name;
    
    @Column(name = "description")
    String description;
    
    @Column(name = "image_url")
    String imageUrl;
    
    @Column(name = "is_active")
    Boolean isActive;
    
    @Column(name = "created_at")
    Instant createdAt;

    public SserviceEntity(){}
    public SserviceEntity(Integer id, String name, String description, String imageUrl, boolean isActive){
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isActive = isActive; 
    }
}
