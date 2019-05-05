package com.ayudenko.application.endpoint;

import com.ayudenko.application.exception.InvalidDatesForReservationException;
import com.ayudenko.application.exception.NoRoomForReservationException;
import com.ayudenko.application.exception.ServiceFault;
import com.ayudenko.application.repository.Reservation;
import com.ayudenko.application.service.HotelService;
import com.ayudenko.application.validation.Validator;
import com.ayudenko.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HotelEndpoint {
    private static final String NAMESPACE_URI = "http://www.ayudenko.com/soap";

    private static final String NOT_VALID = "The person or registration number is not valid";

    private final ObjectFactory objectFactory = new ObjectFactory();

    private final HotelService hotelService;
    private final Validator validator;

    @Autowired
    public HotelEndpoint(HotelService bookService, Validator validator) {
        this.hotelService = bookService;
        this.validator = validator;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "bookRoomRequest")
    @ResponsePayload
    public BookRoomResponse bookRoom(@RequestPayload BookRoomRequest request) {
       if (validator.validateBookRequest(request.getFrom(), request.getTo())) {
           throw new InvalidDatesForReservationException("Date from cannot be bigger then date to",
                   new ServiceFault(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST.getReasonPhrase()));

       }
       Reservation reservation = hotelService.bookRoom(request.getPerson(), request.getRoomType(), request.getFrom(), request.getTo());

       if (reservation == null) {
           throw new NoRoomForReservationException("There are no room available for the following dates",
                   new ServiceFault(String.valueOf(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND.getReasonPhrase()));
       }

       BookRoomResponse bookRoomResponse = objectFactory.createBookRoomResponse();
       bookRoomResponse.setReservationNumber(reservation.getReservationNumber());
       bookRoomResponse.setRoom(reservation.getWhich());

       return bookRoomResponse;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "checkInRequest")
    @ResponsePayload
    public CheckInResponse checkIn(@RequestPayload CheckInRequest request) {
        Status status = hotelService.checkIn(request.getReservationNumber(), request.getPerson());

        CheckInResponse checkInResponse = objectFactory.createCheckInResponse();
        checkInResponse.setStatus(status);

        if (status == Status.FAIL) {
            checkInResponse.setReason(NOT_VALID);
        } else {
            checkInResponse.setReason("Successfully checked in");
        }

        return checkInResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cancelReservationRequest")
    @ResponsePayload
    public CancelReservationResponse cancelReservation(@RequestPayload CancelReservationRequest request) {
        Status status = hotelService.cancelReservation(request.getReservationNumber(), request.getPerson());

        CancelReservationResponse cancelReservationResponse = objectFactory.createCancelReservationResponse();
        cancelReservationResponse.setStatus(status);

        if (status == Status.FAIL) {
            cancelReservationResponse.setReason(NOT_VALID);
        } else {
            cancelReservationResponse.setReason("Registration is successfully removed");
        }

        return cancelReservationResponse;
    }
}
