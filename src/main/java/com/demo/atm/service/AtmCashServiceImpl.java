package com.demo.atm.service;

import com.demo.atm.exception.ATMDenominationException;
import com.demo.atm.exception.ATMOutOfServiceException;
import com.demo.atm.model.Atm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Service
public class AtmCashServiceImpl implements AtmCashService {

    private final MongoTemplate mongoTemplate;
    @Autowired
    public AtmCashServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Atm availableCash(BigInteger atmno) {
        Atm atm = mongoTemplate.findById(atmno, Atm.class);
        if(atm != null && atm.getTotal()==0){
            throw new ATMOutOfServiceException(atmno);
        }
        return atm!=null?atm : null;
    }

    @Override
    public Atm deposit(BigInteger atmno, Map<Integer, Integer> cash) {
        //sampleAccount();
        //sampleAtm();
        Atm atm = mongoTemplate.findById(atmno, Atm.class);
        if(atm==null){
            throw new ATMOutOfServiceException(atmno);
        }else {
            Map oCash = atm.getCash();
            HashMap<Integer, Integer> nCash = new HashMap<>(oCash);

            cash.forEach((key, value) -> nCash.merge(key, value, (v1, v2) -> v1 + v2));
            atm.setCash(nCash);
            mongoTemplate.save(atm);
        }
        return atm!=null?atm : null;
    }

    @Override
    public Atm withdraw(BigInteger atmno, Map<Integer, Integer> cash) {
        Atm atm = mongoTemplate.findById(atmno, Atm.class);
        if(atm!=null) {
            Map oCash = atm.getCash();

            HashMap<Integer, Integer> nCash = new HashMap<>(oCash);

            cash.forEach((key, value) -> nCash.merge(key, value, (v1, v2) -> v1 - v2));
            nCash.forEach((key, value) -> {
                if (value < 1) throw new ATMDenominationException();
            });
            atm.setCash(nCash);
            mongoTemplate.save(atm);
        } else{
            throw new ATMOutOfServiceException(atmno);
        }
        return atm!=null?atm : null;
    }


}
