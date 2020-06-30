package com.demo.atm.service;

import com.demo.atm.exception.*;
import com.demo.atm.model.Account;
import com.demo.atm.model.Atm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class AtmTransactionsServiceImpl implements AtmTransactionsService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    AtmCashService acs;

    @Autowired
    public AtmTransactionsServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Account deposit(BigInteger accno, Map<Integer, Integer> cash,BigInteger atmno) {
        depositValidCash(cash);
        acs.deposit(atmno, cash);
        Account acc = mongoTemplate.findById(accno, Account.class);
        if (acc == null) {
            throw new AccountNotfoundException(accno);
        }
        Map oCash = acc.getCash();
        HashMap<Integer, Integer> nCash = new HashMap<>(oCash);

        cash.forEach((key, value) -> nCash.merge(key, value, (v1, v2) -> v1 + v2));
        acc.setCash(nCash);
        mongoTemplate.save(acc);
        return acc != null ? acc : null;
    }

    // Requested Denominations
    @Override
    public Account withdrawDenominations(BigInteger accno, Map<Integer, Integer> cash, BigInteger atmno) {
        acs.withdraw(atmno, cash);
        withdrawValidCash(cash);
        Account acc = mongoTemplate.findById(accno, Account.class);
        if (acc == null) {
            throw new AccountNotfoundException(accno);
        }
        Map oCash = acc.getCash();
        int total = cash.entrySet().stream().mapToInt((e) -> e.getKey() * e.getValue()).sum();
        if (total > acc.getTotal()) {
            throw new InsufficientOrIncorrectFundsException();
        }
        HashMap<Integer, Integer> nCash = new HashMap<>(oCash);

        cash.forEach((key, value) -> nCash.merge(key, value, (v1, v2) -> v1 - v2));
        acc.setCash(nCash);
        mongoTemplate.save(acc);
        return acc != null ? acc : null;
    }

    // Available Demoninations
    @Override
    public Account withdrawAmount(BigInteger accno, Integer amount, BigInteger atmno) {
        TreeMap<Integer, Integer> cash = convetToDemominations(amount,atmno);
        acs.withdraw(atmno, cash);
        //withdrawValidCash(cash);
        Account acc = mongoTemplate.findById(accno, Account.class);
        if (acc == null) {
            throw new AccountNotfoundException(accno);
        }
        Map oCash = acc.getCash();
        int total = cash.entrySet().stream().mapToInt((e) -> e.getKey() * e.getValue()).sum();
        if (total > acc.getTotal()) {
            throw new InsufficientOrIncorrectFundsException();
        }
        HashMap<Integer, Integer> nCash = new HashMap<>(oCash);

        cash.forEach((key, value) -> nCash.merge(key, value, (v1, v2) -> v1 - v2));
        acc.setCash(nCash);
        mongoTemplate.save(acc);
        return acc != null ? acc : null;
    }

    public  Map<Integer, Integer> cash2 = null;
    public  List<Integer> cval = null;

    private TreeMap<Integer, Integer> convetToDemominations(Integer amount, BigInteger atmno) {
        Atm atm = acs.availableCash(atmno);
        cval = new ArrayList<>(new TreeSet<Integer>(atm.getCash().keySet()).descendingSet());
        return recCash(amount, null,-1);
    }

   private TreeMap<Integer, Integer> recCash(Integer amt, TreeMap<Integer, Integer> t, int i) {
        i++;
        if (t == null) {
            t = new TreeMap<>();
        }
        if (amt > 0) {
            int den =  cval.get(i);
            int div = amt / den;
            if (div > 0 && cash2.get(den)>= div) {
                t.put(den, div);
            } else {
                throw new ATMDenominationException();
            }
            recCash(amt % den,t,i);
        }
        return t;
    }

    private void depositValidCash(Map<Integer, Integer> cash) {
        final boolean temp = false;
        TreeSet<Integer> values = new TreeSet<Integer>(cash.values());
        if(values!= null && values.first()==0 && values.size()==1)
            throw new DepositAmountZeroException();
        cash.forEach((k, v) -> {
            if (v < 1) throw new IncorrectDepositAmountException();
        });
    }

    private void withdrawValidCash(Map<Integer, Integer> cash) {
        final boolean temp = false;
        TreeSet<Integer> values = new TreeSet<Integer>(cash.values());
        if(values!= null && values.size()==1 && values.first()==0 )
            throw new DepositAmountZeroException();
        cash.forEach((k, v) -> {
            if (v < 1) throw new IncorrectDepositAmountException();
        });
    }
}
