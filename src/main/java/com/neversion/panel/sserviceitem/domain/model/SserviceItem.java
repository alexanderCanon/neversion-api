package com.neversion.panel.sserviceitem.domain.model;

import java.math.BigDecimal;

import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sserviceitem.domain.model.enums.AccountType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class SserviceItem {

        private Long id;
        @Setter
        private Sservice sservice;
        private SservicePrice price; // record
        private String duration;
        private AccountType accountType;

        public void updatePrice(BigDecimal newAmount) {
                this.price = new SservicePrice(newAmount);
        }

        public void applyDiscount(BigDecimal percentage) {
                BigDecimal newAmount = this.price.amount().multiply(BigDecimal.ONE.subtract(percentage));
                this.price = new SservicePrice(newAmount);
        }

        public SserviceItem() {
        }

        public SserviceItem(Long id, Sservice sservice, SservicePrice price, String duration,
                        AccountType accountType) {
                this.id = id;
                this.sservice = sservice;
                this.price = price;
                this.duration = duration;
                this.accountType = accountType;
        }
}