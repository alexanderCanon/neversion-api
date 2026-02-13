package com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;

public record SserviceDetailResponse(
    String serviceName,
    String categoryName,
    BigDecimal priceIndividual,
    BigDecimal priceFamiliar
) {

}
