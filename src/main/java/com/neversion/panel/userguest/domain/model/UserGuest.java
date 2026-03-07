package com.neversion.panel.userguest.domain.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserGuest {
        private UUID id;
        private String name;
        private String email;
        private String phone;

        public UserGuest(UUID id, String name, String email, String phone) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.phone = phone;
        }
}
