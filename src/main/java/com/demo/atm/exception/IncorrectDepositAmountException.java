package com.demo.atm.exception;

public class IncorrectDepositAmountException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    public IncorrectDepositAmountException() {
        super("Please check requested amount and your account balance.");
    }
}