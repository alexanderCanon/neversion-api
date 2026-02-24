package com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.sserviceitem.domain.model.SserviceItem;
import com.neversion.panel.sserviceitem.domain.model.SservicePrice;
import com.neversion.panel.sserviceitem.domain.model.enums.AccountType;
import com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest.dto.SserviceItemRequest;
import com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest.dto.SserviceItemResponse;

@Component
public class SserviceItemMapper {

    public static SserviceItem toDomain(SserviceItemRequest request) {
        if (request == null)
            return null;

        return SserviceItem.builder()
                .price(new SservicePrice(request.priceAmount()))
                .duration(request.duration())
                .accountType(AccountType.valueOf(request.accountType().toUpperCase()))
                .build();
    }

    public static SserviceItemResponse toResponse(SserviceItem sserviceItem) {
        if (sserviceItem == null)
            return null;

        return new SserviceItemResponse(
                sserviceItem.getId(),
                sserviceItem.getPrice(),
                sserviceItem.getDuration(),
                sserviceItem.getAccountType());
    }
}
