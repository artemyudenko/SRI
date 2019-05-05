package com.ayudenko.application.repository;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ReservationRepository {

    private final Set<Reservation> reservations = new HashSet<>();

    public Set<Reservation> getReservations() {
        return new HashSet<>(reservations);
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void checkIn(Reservation reservation) {
        for (Reservation r:reservations) {
            if (r.equals(reservation)) {
                r.setCheckedIn(true);
            }
        }
    }

    public void removeReservation(Reservation reservation) {
        reservations.removeIf(next -> next.equals(reservation));
    }
}
