package com.demo.atm.controller;

import com.demo.atm.model.Account;
import com.demo.atm.service.AtmCashService;
import com.demo.atm.service.AtmTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;

@RestController
@RequestMapping(value= "/api/atm")
public class AtmTransOptController {

    @Autowired
    AtmCashService acs;

    @Autowired
    AtmTransactionsService ats;

    @PutMapping("/deposit/{accno}/{atmno}")
    public Account diposit(@PathVariable BigInteger accno,@PathVariable BigInteger atmno, @RequestBody Map<Integer, Integer> cash) {
            return ats.deposit(accno,cash,atmno);
    }

    // With Amount
    @PutMapping("/withdrawAmt/{accno}/{atmno}/{amount}")
    public Account withdrawAmount(@PathVariable BigInteger accno,@PathVariable BigInteger atmno, @PathVariable Integer amount) {
            return ats.withdrawAmount(accno,amount,atmno);
    }

    // With Denominations
    @PutMapping("/withdrawDen/{accno}/{atmno}")
    public Account withdrawDenominations(@PathVariable BigInteger accno,@PathVariable BigInteger atmno, @RequestBody Map<Integer, Integer> cash) {
            return ats.withdrawDenominations(accno,cash, atmno);
    }

}
