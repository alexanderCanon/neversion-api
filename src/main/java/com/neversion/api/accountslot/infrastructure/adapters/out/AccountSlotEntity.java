package com.neversion.api.accountslot.infrastructure.adapters.out;

import java.util.UUID;

import com.neversion.api.accountslot.domain.model.enums.SlotStatus;
import com.neversion.api.accountslot.infrastructure.adapters.out.converter.SlotStatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_slots")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "profile_name", length = 100)
    private String profileName;

    @Column(name = "pin", length = 20)
    private String pin;

    @Column(name = "status", columnDefinition = "slot_status")
    @Convert(converter = SlotStatusConverter.class)
    private SlotStatus status;
}
