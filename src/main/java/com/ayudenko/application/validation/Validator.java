package com.ayudenko.application.validation;

import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

@Component
public class Validator {
    public boolean validateBookRequest(XMLGregorianCalendar from ,XMLGregorianCalendar to) {
        int compare = from.compare(to);
        return compare == DatatypeConstants.GREATER || compare == DatatypeConstants.EQUAL;
    }
}
