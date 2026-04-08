package com.neversion.api.accountslot.infrastructure.adapters.out.converter;

import com.neversion.api.accountslot.domain.model.enums.SlotStatus;
import com.neversion.api.infrastructure.EnumConverter;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SlotStatusConverter extends EnumConverter<SlotStatus> {

    public SlotStatusConverter() {
        super(SlotStatus.class);
    }
}
