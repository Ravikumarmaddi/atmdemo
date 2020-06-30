package com.demo.atm.exception;

public class DepositAmountZeroException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DepositAmountZeroException() {
        super("Deposit Amount can not be zero.");
    }
}
