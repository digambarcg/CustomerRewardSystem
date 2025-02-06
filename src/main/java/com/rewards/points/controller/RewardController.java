package com.rewards.points.controller;

import java.time.Month;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.points.exception.RewardsException;
import com.rewards.points.model.Transaction;
import com.rewards.points.repository.TransactionRepository;
import com.rewards.points.service.RewardService;

@RestController
@RequestMapping("/rewards")
public class RewardController {

	@Autowired
	private RewardService rewardService;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@PostMapping("/addTransaction")
	public String addTransactions(@RequestBody Transaction transaction) throws RewardsException {
		rewardService.addTransactions(transaction);
		return "Transaction added successfully.";
	}
	
	@GetMapping("/totalRewards")
	public Map<String, Integer> getTotalRewards() {
		return rewardService.calculateTotalRewardPoints();
	}
	
	@GetMapping("/MonthlyRewards")
	public Map<String, Map<Month, Integer>> getMonthlyRewards() {
		return rewardService.calculateMonthlyRewardPoints();
	}
}
