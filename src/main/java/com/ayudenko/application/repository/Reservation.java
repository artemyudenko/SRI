package com.ayudenko.application.repository;

import com.ayudenko.soap.Person;
import com.ayudenko.soap.Room;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Objects;

public class Reservation {
    private int reservationNumber;
    private Person who;
    private Room which;
    private XMLGregorianCalendar from;
    private XMLGregorianCalendar to;
    private boolean checkedIn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return reservationNumber == that.reservationNumber &&
                Objects.equals(who, that.who) &&
                Objects.equals(which, that.which) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationNumber, who, which, from, to);
    }

    public Person getWho() {
        return who;
    }

    public void setWho(Person who) {
        this.who = who;
    }

    public Room getWhich() {
        return which;
    }

    public void setWhich(Room which) {
        this.which = which;
    }

    public XMLGregorianCalendar getFrom() {
        return from;
    }

    public void setFrom(XMLGregorianCalendar from) {
        this.from = from;
    }

    public XMLGregorianCalendar getTo() {
        return to;
    }

    public void setTo(XMLGregorianCalendar to) {
        this.to = to;
    }

    public Reservation(int reservationNumber, Person who, Room which, XMLGregorianCalendar from, XMLGregorianCalendar to) {
        this.reservationNumber = reservationNumber;
        this.who = who;
        this.which = which;
        this.from = from;
        this.to = to;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }
}
