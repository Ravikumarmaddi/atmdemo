package com.demo.atm.service;

import com.demo.atm.model.Atm;

import java.math.BigInteger;
import java.util.Map;

public interface AtmCashService {
    public Atm availableCash(BigInteger atmno);

    public Atm deposit(BigInteger atmno, Map<Integer, Integer> cash);

    Atm withdraw(BigInteger atmno, Map<Integer, Integer> cash);

//    public Atm bankFillUnfill(BigInteger atmno, Map cash, boolean isFill);
}
