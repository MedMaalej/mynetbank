package com.mynetbank.mynetbank;

import com.mynetbank.mynetbank.model.Transaction;
import com.mynetbank.mynetbank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MynetbankApplication{
	public static void main(String[] args) {
		SpringApplication.run(MynetbankApplication.class, args);
	}
}
