package com.mynetbank.mynetbank.model;

import org.springframework.data.annotation.Id;

/**
 * Created by Mohamed Maalej on 02/08/17.
 */

/**
 * This class represents a single transaction
 */
public class Transaction {
  @Id
    private String id;
    private double amount;
    private long timestamp;



    public Transaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }


}
