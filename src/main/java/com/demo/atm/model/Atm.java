package com.demo.atm.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Map;

@Document
@Data
@NoArgsConstructor
public class Atm {
    @Id
    private BigInteger id;
    private String name;
    private Map<Integer, Integer> cash;
    private Integer total;

    public Atm(String name, BigInteger id, Map<Integer, Integer> cash) {
        this.name = name;
        this.id = id;
        this.cash = cash;
        if (this.cash != null)
            this.total = this.cash.entrySet().stream().mapToInt((e) -> e.getKey() * e.getValue()).sum();
    }


    public void setCash(Map<Integer, Integer> cash) {
        this.cash = cash;
        if (this.cash != null)
            this.total = this.cash.entrySet().stream().mapToInt((e) -> e.getKey() * e.getValue()).sum();
    }

    private boolean availabilityCheck(Map<Integer, Integer> first, Map<Integer, Integer>  second) {
        return first.entrySet().stream()
                .allMatch(e -> e.getValue() > second.get(e.getKey()));

    }
}
