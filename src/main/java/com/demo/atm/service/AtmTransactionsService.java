package com.demo.atm.service;

import com.demo.atm.model.Account;

import java.math.BigInteger;
import java.util.Map;

public interface AtmTransactionsService {
    Account withdrawAmount(BigInteger accno, Integer amount, BigInteger atmno);

    Account deposit(BigInteger accno, Map<Integer, Integer> cash, BigInteger atmno);

    Account withdrawDenominations(BigInteger accno, Map<Integer, Integer> cash, BigInteger atmno);

    // public Account withdraw(BigInteger accno, Map<Integer, Integer> cash);
}
