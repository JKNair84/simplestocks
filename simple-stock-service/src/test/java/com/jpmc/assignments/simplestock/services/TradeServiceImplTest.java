package com.jpmc.assignments.simplestock.services;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.Indicator;
import com.jpmc.assignments.simplestock.models.Trade;
import com.jpmc.assignments.simplestock.repository.TradeRepository;

/**
 * there are no negative test cases written as 
 * all the validations are in resources and request that comes to 
 * service is well validated.
 * 
 *  Assumption is this will only be invoked from the rest services
 * 
 * @author jnair1
 *
 */
public class TradeServiceImplTest {

	@Mock
	private TradeService tradeService;
	
	@Mock
	private TradeRepository repository;

	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void recordTradeTest() throws SimpleStockException {
		
		Trade toBePersisted = new Trade(Indicator.BUY, "GIN", 10, 100.0);
		Trade toBeMocked = new Trade(Indicator.BUY, "GIN", 10, 100.0);
		when(tradeService.recordTrade(toBePersisted)).thenReturn(toBeMocked);
			
		Trade persistedTrade = tradeService.recordTrade(toBePersisted);
		assertEquals(persistedTrade.getIndicator(), Indicator.BUY);
		assertEquals(persistedTrade.getPrice(), 100.0);
		assertEquals(persistedTrade.getQuantity(), 10);
		assertEquals(persistedTrade.getSymbol(), "GIN");
		assertNotNull(persistedTrade.getId());
	}
	

	@Test
	public void getRecentTransactions() throws SimpleStockException {
		
		Trade toBeMocked = new Trade(Indicator.BUY, "GIN", 10, 100.0);
		Map<String, List<Trade>> mockTransactions = new HashMap<>();
		List<Trade> trades = new ArrayList<Trade>();
		trades.add(toBeMocked);
		mockTransactions.put("POP", trades);
		
		when(tradeService.getRecentTransactions()).thenReturn(mockTransactions);
				
		Map<String, List<Trade>> fetchedTransactions = tradeService.getRecentTransactions();
		assertEquals(fetchedTransactions.size(), mockTransactions.size());
		assertNotNull(fetchedTransactions.get("POP"));
		assertEquals(fetchedTransactions.get("POP").size(), mockTransactions.get("POP").size());
		assertEquals(fetchedTransactions.get("POP").size(), 1);
		assertEquals(fetchedTransactions.get("POP").get(0).getSymbol(), mockTransactions.get("POP").get(0).getSymbol());
		assertEquals(fetchedTransactions.get("POP").get(0).getIndicator(), mockTransactions.get("POP").get(0).getIndicator());
		assertEquals(fetchedTransactions.get("POP").get(0).getPrice(), mockTransactions.get("POP").get(0).getPrice());
		assertEquals(fetchedTransactions.get("POP").get(0).getQuantity(), mockTransactions.get("POP").get(0).getQuantity());
	}
	
	@Test
	public void calculateVolumeWeightedPriceForStock() throws SimpleStockException {
		
		BigDecimal mockedDecimal = BigDecimal.valueOf(10);
		when(tradeService.calculateVolumeWeightedPriceForStock("GIN")).thenReturn(mockedDecimal);
		
		assertEquals(tradeService.calculateVolumeWeightedPriceForStock("GIN"), mockedDecimal);
				
	}
	
	@Test
	public void calculateGBCEIndex() throws SimpleStockException {
		
		BigDecimal mockedDecimal = BigDecimal.valueOf(19);
		when(tradeService.calculateGBCEIndex()).thenReturn(mockedDecimal);
		
		assertEquals(tradeService.calculateGBCEIndex(), mockedDecimal);
	}
	
}
