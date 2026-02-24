package com.neversion.panel.sservice.infrastructure.adapters.out;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.neversion.panel.sservice.domain.model.enums.CategoryType;
import com.neversion.panel.sserviceitem.infrastructure.adapters.out.SserviceItemEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "services")
@Getter
@Setter
@Builder
@SQLDelete(sql = "UPDATE services SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
public class SserviceEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CategoryType category;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SserviceItemEntity> items = new ArrayList<>();

    public SserviceEntity() {
    }

    public SserviceEntity(Integer id, String name, String description, String imageUrl, Boolean isActive,
            Instant createdAt, CategoryType category, List<SserviceItemEntity> items) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.category = category;
        this.items = items;
    }

    public void addItem(SserviceItemEntity item) {
        items.add(item);
        item.setService(this);
    }

    public void removeItem(SserviceItemEntity item) {
        items.remove(item);
        item.setService(null);
    }
}
