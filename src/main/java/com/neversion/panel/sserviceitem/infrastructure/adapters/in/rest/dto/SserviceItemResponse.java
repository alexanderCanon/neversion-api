package com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest.dto;

import com.neversion.panel.sserviceitem.domain.model.SservicePrice;
import com.neversion.panel.sserviceitem.domain.model.enums.AccountType;

public record SserviceItemResponse(
        Long id,
        SservicePrice price,
        String duration,
        AccountType accountType) {
}
