package com.neversion.api.subscription.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.subscription.domain.model.Subscription;
import com.neversion.api.subscription.domain.model.enums.SubStatus;
import com.neversion.api.subscription.domain.port.out.SubscriptionRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateSubscriptionService Unit Tests")
class UpdateSubscriptionServiceUT {

        @Mock
        private SubscriptionRepositoryPort subscriptionRepositoryPort;

        private UpdateSubscriptionService updateSubscriptionService;

        @BeforeEach
        void setUp() {
                updateSubscriptionService = new UpdateSubscriptionService(subscriptionRepositoryPort);
        }

        private Subscription buildSubscription(SubStatus status) {
                return Subscription.builder()
                                .id(UUID.randomUUID())
                                .userGuestId(UUID.randomUUID())
                                .accountId(UUID.randomUUID())
                                .purchaseDate(LocalDate.now())
                                .renewalDate(LocalDate.now().plusMonths(1))
                                .status(status)
                                .build();
        }

        @Nested
        @DisplayName("suspend")
        class Suspend {

                @Test
                @DisplayName("should suspend an ACTIVE subscription")
                void shouldSuspendActive() {
                        Subscription active = buildSubscription(SubStatus.ACTIVE);
                        when(subscriptionRepositoryPort.findById(active.getId()))
                                        .thenReturn(Optional.of(active));
                        when(subscriptionRepositoryPort.save(any(Subscription.class)))
                                        .thenAnswer(inv -> inv.getArgument(0));

                        Subscription result = updateSubscriptionService.suspend(active.getId());

                        assertThat(result.getStatus()).isEqualTo(SubStatus.SUSPENDED);
                }

                @Test
                @DisplayName("should throw when subscription is not ACTIVE")
                void shouldThrowWhenNotActive() {
                        Subscription suspended = buildSubscription(SubStatus.SUSPENDED);
                        when(subscriptionRepositoryPort.findById(suspended.getId()))
                                        .thenReturn(Optional.of(suspended));

                        assertThatThrownBy(() -> updateSubscriptionService.suspend(suspended.getId()))
                                        .isInstanceOf(BusinessRuleException.class)
                                        .hasMessageContaining("ACTIVE");
                }

                @Test
                @DisplayName("should throw when subscription not found")
                void shouldThrowNotFound() {
                        UUID id = UUID.randomUUID();
                        when(subscriptionRepositoryPort.findById(id)).thenReturn(Optional.empty());

                        assertThatThrownBy(() -> updateSubscriptionService.suspend(id))
                                        .isInstanceOf(ResourceNotFoundException.class);
                }
        }

        @Nested
        @DisplayName("terminate")
        class Terminate {

                @Test
                @DisplayName("should terminate an ACTIVE subscription")
                void shouldTerminateActive() {
                        Subscription active = buildSubscription(SubStatus.ACTIVE);
                        when(subscriptionRepositoryPort.findById(active.getId()))
                                        .thenReturn(Optional.of(active));
                        when(subscriptionRepositoryPort.save(any(Subscription.class)))
                                        .thenAnswer(inv -> inv.getArgument(0));

                        Subscription result = updateSubscriptionService.terminate(active.getId());

                        assertThat(result.getStatus()).isEqualTo(SubStatus.CANCELLED);
                }

                @Test
                @DisplayName("should throw when already cancelled")
                void shouldThrowWhenAlreadyCancelled() {
                        Subscription cancelled = buildSubscription(SubStatus.CANCELLED);
                        when(subscriptionRepositoryPort.findById(cancelled.getId()))
                                        .thenReturn(Optional.of(cancelled));

                        assertThatThrownBy(() -> updateSubscriptionService.terminate(cancelled.getId()))
                                        .isInstanceOf(BusinessRuleException.class)
                                        .hasMessageContaining("already cancelled");
                }
        }
}
