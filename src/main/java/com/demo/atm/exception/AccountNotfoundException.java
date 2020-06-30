package com.demo.atm.exception;

import java.math.BigInteger;

public class AccountNotfoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AccountNotfoundException(BigInteger id) {
        super("Account not found : " + id);
    }
}