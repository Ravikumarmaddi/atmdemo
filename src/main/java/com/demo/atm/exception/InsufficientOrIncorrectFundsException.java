package com.demo.atm.exception;

public class InsufficientOrIncorrectFundsException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    public InsufficientOrIncorrectFundsException() {
        super("Please check requested amount and your account balance.");
    }
}