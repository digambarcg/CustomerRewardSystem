package com.rewards.points.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import com.rewards.points.model.Transaction;


public class RestTemplatePostApiExamples {
	@LocalServerPort
	int randomServerPort;
	 
	@Test
	public void testAddEmployee_withBody_thenSuccess() throws URISyntaxException {
	RestTemplate restTemplate = new RestTemplate();
	URI uri = new URI("http://localhost:" + randomServerPort + "/rewards/addTransaction");
	 
	LocalDate localDate1 = LocalDate.parse("2026-01-01");
	Transaction transact = new Transaction("Cust1",12231.0,localDate1);
	 
	Transaction createdTransaction = restTemplate.postForObject(uri, transact, Transaction.class);
	 
	Assertions.assertNotNull(createdTransaction.getCust_id());
	}
}
