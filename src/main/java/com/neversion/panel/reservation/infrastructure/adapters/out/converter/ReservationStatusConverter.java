package com.neversion.panel.reservation.infrastructure.adapters.out.converter;

import com.neversion.panel.infrastructure.EnumConverter;
import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReservationStatusConverter extends EnumConverter<ReservationStatus> {

    public ReservationStatusConverter() {
        super(ReservationStatus.class);
    }
}
