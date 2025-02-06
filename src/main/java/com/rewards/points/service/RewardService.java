package com.rewards.points.service;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewards.points.exception.CustomerNotFoundException;
import com.rewards.points.exception.InvalidTransactionDateException;
import com.rewards.points.exception.InvalidTransactionException;
import com.rewards.points.exception.RewardsException;
import com.rewards.points.model.Transaction;
import com.rewards.points.repository.TransactionRepository;

@Service
public class RewardService {
	private List<Transaction> transactions = new ArrayList<>();
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public void addTransactions(Transaction transaction) throws RewardsException {
		validateTransactions(transaction);
		transactionRepository.save(transaction);
//		transactions.add(transaction);
		
	}
	
	public Map<String, Map<Month, Integer>> calculateMonthlyRewardPoints() {
		Map<String, Map<Month, Integer>> cust_rewards = new HashMap<>();
		List<Transaction> montlytransactions = transactionRepository.findAll();
		System.out.println("Transactions:::"+montlytransactions);
		for (Transaction tran : montlytransactions) {
			String cust_id = tran.getCust_id();
			Month month = tran.getTrans_date().getMonth();
			int points = calculateRewardPoints(tran.getTrans_amt());
			
			cust_rewards.computeIfAbsent(cust_id, k -> new HashMap<>()).merge(month, points, Integer::sum);
		}
		return cust_rewards;
	}
	
	public  Map<String, Integer> calculateTotalRewardPoints(){
		Map<String, Integer> totalRewards = new HashMap<>();
		List<Transaction> allTransactions = transactionRepository.findAll();
		System.out.println("Transactions:::"+allTransactions);
		for (Transaction tran : allTransactions) {
			String cust_id = tran.getCust_id();
			int points = calculateRewardPoints(tran.getTrans_amt());
			
			totalRewards.merge(cust_id, points, Integer::sum);
		}
		return totalRewards;
	}
	
	private int calculateRewardPoints(double trans_amt) {
		int points = 0;
		if (trans_amt > 100) {
			points += (trans_amt - 100) * 2;
		}
		if (trans_amt > 50) {
			points += (Math.min(trans_amt, 100) - 50);
		}
		return points;
		
	}
	
	public void validateTransactions(Transaction trans){
		if (trans.getCust_id() == null || trans.getCust_id().isEmpty()) {
			throw new CustomerNotFoundException("Customer ID can not be null or empty.");
		}
		if (trans.getTrans_amt() < 0 ) {
			throw new InvalidTransactionException("Transaction amount can not be less than 0/negative.");
		}
		if (trans.getTrans_date() == null || trans.getTrans_date().equals(" ")) {
			throw new InvalidTransactionDateException("Transaction date can not be null or empty.");
		}
	}
}
