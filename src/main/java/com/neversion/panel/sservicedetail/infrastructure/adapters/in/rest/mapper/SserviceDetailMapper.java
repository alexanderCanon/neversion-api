package com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.sservicedetail.domain.model.SserviceDetail;
import com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest.dto.SserviceDetailRequest;
import com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest.dto.SserviceDetailResponse;

@Component
public class SserviceDetailMapper {

    public SserviceDetail toDomain(SserviceDetailRequest request) {
        return new SserviceDetail(
            null,
            request.getServiceId(),
            request.getCategoryId(),
            null,
            null,
            request.getPriceIndividual(),
            request.getPriceFamiliar()
        );
    }

    public SserviceDetailResponse toResponse(SserviceDetail sserviceDetail) {
        return new SserviceDetailResponse(
            sserviceDetail.serviceName(),
            sserviceDetail.categoryName(),
            sserviceDetail.priceIndividual(),
            sserviceDetail.priceFamiliar()
        );
    }
}
