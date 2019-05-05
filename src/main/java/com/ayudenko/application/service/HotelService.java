package com.ayudenko.application.service;

import com.ayudenko.application.repository.Reservation;
import com.ayudenko.application.repository.ReservationRepository;
import com.ayudenko.application.repository.RoomRepository;
import com.ayudenko.soap.Person;
import com.ayudenko.soap.Room;
import com.ayudenko.soap.Status;
import com.ayudenko.soap.Type;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Set;

@Service
public class HotelService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    private static int reservationNumberTemplate = 0;

    public HotelService(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public Reservation bookRoom(Person person, Type roomType, XMLGregorianCalendar from, XMLGregorianCalendar to) {
        Set<Room> roomsByType = roomRepository.getRoomsByType(roomType);
        Set<Reservation> reservations = reservationRepository.getReservations();

        if (reservations.isEmpty()) {
            Reservation reservation = new Reservation(reservationNumberTemplate++, person,
                    roomsByType.iterator().next(), from, to);
            reservationRepository.addReservation(reservation);
            return reservation;
        }

        Room roomForResrvation = null;
        for (Room roomByType : roomsByType) {

            if (roomForResrvation != null) {
                break;
            }

            int counter = 0;

            for (Reservation reservation : reservations) {
                if (roomForResrvation != null) {
                    break;
                }

                if (roomByType.equals(reservation.getWhich())) {
                    int compareStartAAndEndB = reservation.getFrom().compare(to);
                    int compareEndAAndStartB = reservation.getTo().compare(from);
                    if ((compareStartAAndEndB == DatatypeConstants.LESSER || compareStartAAndEndB == DatatypeConstants.EQUAL)
                        && compareEndAAndStartB == DatatypeConstants.GREATER || compareEndAAndStartB == DatatypeConstants.EQUAL) {
                        System.out.println("Room " + roomByType.getNumber() + " is taken for this period.");
                        break;
                    } else {
                        roomForResrvation = roomByType;
                    }
                }
                counter++;
                if (counter == reservations.size() && roomForResrvation == null) {
                    roomForResrvation = roomByType;
                }

            }

        }

        if (roomForResrvation == null) {
            return null;
        }

        Reservation reservation = new Reservation(reservationNumberTemplate++, person,
                roomForResrvation, from, to);
        reservationRepository.addReservation(reservation);

        return reservation;
    }

    public Status checkIn(int reservationNumber, Person person) {
        Reservation reservation = getReservation(reservationNumber, person);
        if (reservation == null) {
            return Status.FAIL;
        }

        reservationRepository.checkIn(reservation);
        return Status.SUCCESS;
    }

    public Status cancelReservation(int reservationNumber, Person person) {
        Reservation reservation = getReservation(reservationNumber, person);
        if (reservation == null) {
            return Status.FAIL;
        }

        reservationRepository.removeReservation(reservation);
        return Status.SUCCESS;
    }

    private Reservation getReservation(int reservationNumber, Person person) {
        Set<Reservation> reservations = reservationRepository.getReservations();
        for (Reservation reservation : reservations) {
            if (reservation.getReservationNumber() == reservationNumber && reservation.getWho().equals(person)) {
                return reservation;
            }
        }

        return null;
    }
}
