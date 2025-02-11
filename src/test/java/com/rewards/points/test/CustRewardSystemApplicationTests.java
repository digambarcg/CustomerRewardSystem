package com.rewards.points.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rewards.points.controller.RewardController;
import com.rewards.points.exception.CustomerNotFoundException;
import com.rewards.points.exception.InvalidTransactionException;
import com.rewards.points.model.Transaction;
import com.rewards.points.repository.TransactionRepository;
import com.rewards.points.service.RewardService;

@ExtendWith(MockitoExtension.class)
public class CustRewardSystemApplicationTests {
	
	@Autowired
    private RewardService rewardService;
	
	@InjectMocks
	RewardController rewardController;
	
	@Mock
	TransactionRepository transactionRepository;
	
	@Test
	public void testCustomerName() {
		assertThrows(CustomerNotFoundException.class, () -> {
			rewardService.validateTransactions(null);
		});
	}
	
	@Test
	void testInvalidTransaction() {
		LocalDate localDate1 = LocalDate.parse("2025-01-01");
		Transaction invalidTransactions = new Transaction("Cust1",-120.0, localDate1);
		
		assertThrows(InvalidTransactionException.class, () -> {
			rewardService.validateTransactions(invalidTransactions);
		});
	}
	
	@Test
	public void testAddEmployee() 
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		Transaction transaction = new Transaction();
		transaction.setCust_id("Cust1");
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
		LocalDate localDate1 = LocalDate.parse("2026-01-01");
		Transaction transact = new Transaction("Cust2",12231.0,localDate1);
		ResponseEntity<String> responseEntity = rewardController.addTransactions(transact);
		System.out.println("4444444444 "+responseEntity);
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
		//assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
	}
}
