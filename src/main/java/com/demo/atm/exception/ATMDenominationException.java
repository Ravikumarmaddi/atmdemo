package com.demo.atm.exception;

public class ATMDenominationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ATMDenominationException() {
        super("Requested denominations not available at present, please select diffrent denominations.");
    }
}