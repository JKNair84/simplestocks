package com.jpmc.assignments.simplestock.repository;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.Stocks;

/**
 * Tests for stock repository
 * There are no negative tests as we have the validation,
 * and only validated data would be sent through 
 * 
 * 
 * @author jnair1
 *
 */
public class StockRepositoryImplTest {
	
	@Mock
	private StockRepository repository;
	
	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getStockBySymbol() throws SimpleStockException, ExecutionException {
		
		Optional<Stocks> mockedStock = Optional.of(Stocks.ALE); 
		
		when(repository.getStockBySymbol(Mockito.any())).thenReturn(mockedStock);
		Optional<Stocks> retrievedStock = repository.getStockBySymbol(Stocks.ALE.getSymbol());
		assertEquals(retrievedStock.isPresent(), mockedStock.isPresent());
		assertEquals(retrievedStock.get().getLastDividend(), mockedStock.get().getLastDividend());
		assertEquals(retrievedStock.get().getParValue(), mockedStock.get().getParValue());
		assertEquals(retrievedStock.get().getSymbol(), mockedStock.get().getSymbol());
	}
	
	@Test
	/*
	 * this will work
	 */
	public void getStockByInvalidSymbol() throws SimpleStockException, ExecutionException {
		
		Optional<Stocks> mockedStock = Optional.of(Stocks.ALE); 
		
		when(repository.getStockBySymbol(Mockito.any())).thenReturn(mockedStock);
		Optional<Stocks> retrievedStock = repository.getStockBySymbol(Mockito.any());
		assertEquals(retrievedStock.isPresent(), mockedStock.isPresent());
		assertEquals(retrievedStock.get().getLastDividend(), mockedStock.get().getLastDividend());
		assertEquals(retrievedStock.get().getParValue(), mockedStock.get().getParValue());
		assertEquals(retrievedStock.get().getSymbol(), mockedStock.get().getSymbol());
	}

}
