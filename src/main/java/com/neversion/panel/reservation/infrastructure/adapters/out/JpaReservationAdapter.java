package com.neversion.panel.reservation.infrastructure.adapters.out;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Repository;

import com.neversion.panel.reservation.domain.model.GuestUser;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.ReservationDetail;
import com.neversion.panel.reservation.domain.port.out.ReservationRepositoryPort;
import com.neversion.panel.reservation.infrastructure.adapters.out.mapper.ReservationPersistenceMapper;

@Repository
public class JpaReservationAdapter implements ReservationRepositoryPort {

    private final SpringDataReservationRepository reservationRepository;
    private final SpringDataReservationDetailRepository detailRepository;
    private final SpringDataGuestUserRepository guestUserRepository;
    private final ReservationPersistenceMapper mapper;

    public JpaReservationAdapter(
            SpringDataReservationRepository reservationRepository,
            SpringDataReservationDetailRepository detailRepository,
            SpringDataGuestUserRepository guestUserRepository,
            ReservationPersistenceMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.detailRepository = detailRepository;
        this.guestUserRepository = guestUserRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByProofUrl(String proofUrl) {
        return reservationRepository.existsByProofUrl(proofUrl);
    }

    @Override
    public GuestUser findOrCreateGuest(GuestUser guest) {
        return guestUserRepository.findByEmail(guest.email())
                .map(mapper::toDomain)
                .orElseGet(() -> {
                    GuestUserEntity entity = mapper.toEntity(guest);
                    GuestUserEntity saved = guestUserRepository.save(entity);
                    return mapper.toDomain(saved);
                });
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = mapper.toEntity(reservation);
        entity.setCreatedAt(OffsetDateTime.now());
        ReservationEntity saved = reservationRepository.saveAndFlush(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public ReservationDetail saveDetail(ReservationDetail detail) {
        ReservationDetailEntity entity = mapper.toEntity(detail);
        ReservationDetailEntity saved = detailRepository.save(entity);
        return mapper.toDomain(saved);
    }
}
