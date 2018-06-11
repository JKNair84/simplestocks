package com.jpmc.assignments.simplestock.repository;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for trade repository There are no negative tests as we have the
 * validation, and only validated data would be sent through
 * 
 * 
 * @author jnair1
 *
 */
//TODO: write missing test cases
//There are no negative tests as we have the validation,
//and only validated data would be sent through
public class TradeRepositoryImplTest {

	@Mock
	private TradeRepository repository;

	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllTrades() {

	}

	@Test
	public void recordTrade() {

	}

	@Test
	public void getRecentTrades() {

	}

	@Test
	public void getRecentTradesBySymbol() {

	}

	@Test
	public void getRecentTradesByInvalidSymbol() {

	}

}
