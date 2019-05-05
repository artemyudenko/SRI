package com.ayudenko.application.exception;

public class InvalidDatesForReservationException extends ServiceFaultException {

    public InvalidDatesForReservationException(String message, ServiceFault serviceFault) {
        super(message, serviceFault);
    }

    public InvalidDatesForReservationException(String message, Throwable e, ServiceFault serviceFault) {
        super(message, e, serviceFault);
    }
}
