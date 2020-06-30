package com.demo.util;

import com.demo.atm.model.Account;
import com.demo.atm.model.Atm;
import com.demo.atm.service.AtmCashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class AtmUtil {
    private final MongoTemplate mongoTemplate;

    @Autowired
    AtmCashService acs;

    @Autowired
    public AtmUtil(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public void sampleAccount() {
        mongoTemplate.dropCollection(Account.class);
        Map<Integer, Integer> cash = new HashMap<>();
        cash.put(1,30);
        cash.put(10,20);
        cash.put(20,30);
        cash.put(50,10);
        Account account = new Account("Ravi", BigInteger.valueOf(100),cash );
        mongoTemplate.save(account);

        System.out.println(ObjectToJSonUtil.objectPrettyPrint(account));
    }

    public void sampleAtm() {
        mongoTemplate.dropCollection(Atm.class);
        Map<Integer, Integer> cash = new HashMap<>();
        cash.put(1,3330);
        cash.put(10,520);
        cash.put(20,830);
        cash.put(50,9910);
        cash.put(100,9910);
        cash.put(200,9910);
        cash.put(500,9910);
        cash.put(2000,9910);
        Atm atm = new Atm("Ravi", BigInteger.valueOf(1001),cash );
        mongoTemplate.save(atm);
        System.out.println(ObjectToJSonUtil.objectPrettyPrint(atm));
    }

}
