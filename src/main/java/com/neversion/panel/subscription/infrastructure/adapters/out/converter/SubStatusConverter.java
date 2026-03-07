package com.neversion.panel.subscription.infrastructure.adapters.out.converter;

import com.neversion.panel.infrastructure.EnumConverter;
import com.neversion.panel.subscription.domain.model.enums.SubStatus;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SubStatusConverter extends EnumConverter<SubStatus> {

    public SubStatusConverter() {
        super(SubStatus.class);
    }
}
