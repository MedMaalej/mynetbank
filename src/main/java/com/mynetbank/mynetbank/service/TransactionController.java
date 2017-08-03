package com.mynetbank.mynetbank.service;

import com.mynetbank.mynetbank.model.Transaction;
import com.mynetbank.mynetbank.repository.TransactionRepository;
import com.mynetbank.mynetbank.util.ErrorTypes;
import com.mynetbank.mynetbank.wrapper.StatisticsResultsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mohamed Maalej on 02/08/17.
 */

@RestController
public class TransactionController {


    @Autowired
    TransactionRepository transactionRepository;

    /**
     * This method checks whether a given timestamp is in a given range of timestamps
     *
     * @param value
     * @param start
     * @param end
     * @return
     */
    private boolean isTimestampInRange(long value, long start, long end) {
        return (value <= end && value >= start);
    }


    /**
     * This method retrieves all the transactions occurring in the last 60 seconds
     *
     * @param transactionList
     * @return
     */
    private List<Transaction> getRecentTransactions(List<Transaction> transactionList) {
        List<Transaction> recentTransactionsList = new ArrayList<>();
        long now = Instant.now().toEpochMilli();
        for (Transaction transaction : transactionList) {
            if (isTimestampInRange(transaction.getTimestamp(), now - 60000, now)) {
                recentTransactionsList.add(transaction);
            }
        }
        return recentTransactionsList;
    }

    /**
     * This method returns the sum of all transactions occurring in the last 60 seconds
     *
     * @param transactionList
     * @return
     */
    private double getTransactionsSum(List<Transaction> transactionList) {
        double transactionsSum = 0;
        for (Transaction transaction : transactionList) {
            transactionsSum += transaction.getAmount();
        }
        return transactionsSum;
    }

    /**
     * This method returns the average of all transactions occurring in the last 60 seconds
     *
     * @param transactionList
     * @return
     */
    private double getTransactionsAverage(List<Transaction> transactionList) {
        return getTransactionsSum(transactionList) / transactionList.size();
    }

    /**
     * This method returns the max amount of all transactions occurring in the last 60 seconds
     *
     * @param transactionList
     * @return
     */
    private double getTransactionsMaxAmount(List<Transaction> transactionList) {
        double maxAmount = 0;

        for (Transaction transaction : transactionList) {
            if (transaction.getAmount() > maxAmount) {
                maxAmount = transaction.getAmount();
            }
        }
        return maxAmount;
    }

    /**
     * This method returns the min amount of all transactions occurring in the last 60 seconds
     *
     * @param transactionList
     * @return
     */
    private double getTransactionsMinAmount(List<Transaction> transactionList) {
        if (transactionList.size() > 0) {
            double minAmount = transactionList.get(0).getAmount();
            for (Transaction transaction : transactionList) {
                if (transaction.getAmount() < minAmount) {
                    minAmount = transaction.getAmount();
                }
            }
            return minAmount;
        }
        return 0;
    }


    /**
     * The /transactions endpoint checks whether a given transaction occurred within the last
     * 60 seconds
     *
     * @param amount
     * @param timestamp
     * @return
     */
    @RequestMapping(value = "/transactions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> testTransaction(@RequestParam(value = "amount") double amount, @RequestParam(value = "timestamp") long timestamp) {
        long now = Instant.now().toEpochMilli();
        if (Math.abs(timestamp - now) / 1000 > 60) {
            return new ResponseEntity<Object>(ErrorTypes.OLD_TRANSACTION.getErrorCode(), HttpStatus.EXPECTATION_FAILED);

        } else {
            return new ResponseEntity<Object>(ErrorTypes.SUCCESS.getErrorCode(), HttpStatus.OK);

        }
    }


    /**
     * The /statistics endpoint return the statistics (sum, average, min, max,count) of all
     * the transactions occurring within the last 60 seconds
     *
     * @return
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatisticsResultsWrapper getStatistics() {
        List<Transaction> recentTransactionsList = getRecentTransactions(transactionRepository.findAll());
        StatisticsResultsWrapper statisticsResultsWrapper = new StatisticsResultsWrapper();
        statisticsResultsWrapper.setMin(getTransactionsMinAmount(recentTransactionsList));
        statisticsResultsWrapper.setMax(getTransactionsMaxAmount(recentTransactionsList));
        statisticsResultsWrapper.setAverage(getTransactionsAverage(recentTransactionsList));
        statisticsResultsWrapper.setSum(getTransactionsSum(recentTransactionsList));
        statisticsResultsWrapper.setCount(recentTransactionsList.size());
        return statisticsResultsWrapper;
    }

}
