package com.ayudenko.application;

public enum Constants {

    NAMESPACE_URI("http://www.ayudenko.com/soap");

    private String value;

    Constants(String v) {
        value = v;
    }

    public String getValue() {
        return this.value;
    }
}
