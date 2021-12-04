package com.company.cafe.exceptions;


public class IllegalOrderAddress extends RuntimeException {

    public IllegalOrderAddress(String address) {
        super("Illegal order address: " + address);
    }
}