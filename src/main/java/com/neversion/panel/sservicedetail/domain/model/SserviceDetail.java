package com.neversion.panel.sservicedetail.domain.model;

import java.math.BigDecimal;

public record SserviceDetail(
    Long id,
    Integer serviceId,
    Integer categoryId,
    BigDecimal priceIndividual,
    BigDecimal priceFamiliar
) {

}
