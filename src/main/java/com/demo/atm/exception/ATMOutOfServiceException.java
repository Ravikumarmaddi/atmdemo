package com.demo.atm.exception;

import java.math.BigInteger;

public class ATMOutOfServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ATMOutOfServiceException(BigInteger id) {
        super("Account not found : " + id);
    }
}