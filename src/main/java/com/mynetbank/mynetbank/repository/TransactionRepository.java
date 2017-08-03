package com.mynetbank.mynetbank.repository;

import com.mynetbank.mynetbank.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Mohamed Maalej on 02/08/17.
 */

/**
 * This interface inherits all the standard crud operations from the MongoRepository
 */
public interface TransactionRepository extends MongoRepository<Transaction,String> {

}
