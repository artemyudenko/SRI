package com.ayudenko.application.exception;

public class NoRoomForReservationException extends ServiceFaultException {
    public NoRoomForReservationException(String message, ServiceFault serviceFault) {
        super(message, serviceFault);
    }

    public NoRoomForReservationException(String message, Throwable e, ServiceFault serviceFault) {
        super(message, e, serviceFault);
    }
}
