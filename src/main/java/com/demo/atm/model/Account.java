package com.demo.atm.model;

import com.demo.util.ObjectToJSonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Document
@Data
@NoArgsConstructor
public class Account {
    @Id
    private BigInteger id;
    private String name;
    private Map<Integer, Integer> cash;
    private Integer total;

    public Account(String name, BigInteger id, Map<Integer, Integer> cash) {
        this.name = name;
        this.id = id;
        this.cash = cash;
        if(this.cash!=null)
            this.total =  this.cash.entrySet().stream().mapToInt((e) -> e.getKey() * e.getValue()).sum();
        else
            this.total = 0;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> cash = new HashMap<>();
        cash.put(1,30);
        cash.put(10,20);
        cash.put(20,30);
        cash.put(50,10);
        Account account = new Account("Ravi", BigInteger.valueOf(100),cash );
        System.out.println(ObjectToJSonUtil.objectPrettyPrint(account));
    }

    public void setCash(Map<Integer, Integer> cash) {
        this.cash = cash;
        if(this.cash!=null)
            this.total = this.cash.entrySet().stream().mapToInt((e) -> e.getKey() * e.getValue()).sum();
    }

}
