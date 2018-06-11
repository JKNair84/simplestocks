package com.jpmc.assignments.simplestock.services;

import static org.testng.Assert.assertEquals;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.StockTransaction;
import com.jpmc.assignments.simplestock.models.StockTypes;
import com.jpmc.assignments.simplestock.models.Stocks;
import com.jpmc.assignments.simplestock.repository.StockRepository;

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
public class StockServiceImplTest {

	@Mock
	private StockService stockService;
	@Mock
	private StockRepository repository;

	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(dataProvider = "DIVIDEND_PROVIDER", dataProviderClass = DataSetForStocksTests.class)
	public void calculateDividendYieldTest(String symbol, double price, double expectedDividendYield)
			throws SimpleStockException {

		StockTransaction transaction = new StockTransaction(price, Stocks.valueOf(symbol));
		Mockito.when(stockService.calculateDividendYield(transaction)).thenReturn(new StockTransaction(
				transaction.getTransactionId(), expectedDividendYield, 0, price, Stocks.valueOf(symbol)));

		assertEquals(stockService.calculateDividendYield(transaction).getDividendYield(), expectedDividendYield);
	}

	@Test(dataProvider = "PE_RATIO_PROVIDER", dataProviderClass = DataSetForStocksTests.class)
	public void calculatePriceEarningRatioTest(String symbol, double price, double expectedPeRatio)
			throws SimpleStockException {

		StockTransaction transaction = new StockTransaction(price, Stocks.valueOf(symbol));
		Mockito.when(stockService.calculatePriceEarningRatio(transaction)).thenReturn(new StockTransaction(
				transaction.getTransactionId(), 0, expectedPeRatio, price, Stocks.valueOf(symbol)));

		assertEquals(stockService.calculatePriceEarningRatio(transaction).getPeRatio(), expectedPeRatio);
	}

	@Test(dataProvider = "STOCKS_PROVIDER", dataProviderClass = DataSetForStocksTests.class)
	public void getStockBySymbolTest(String symbol, StockTypes type, double lastDividend, double fixedDividend,
			double parValue) throws SimpleStockException, ExecutionException {

		Mockito.when(stockService.getStockBySymbol(symbol)).thenReturn(Optional.of(Stocks.valueOf(symbol)));

		Stocks fetchedStock = stockService.getStockBySymbol(symbol).get();
		assertEquals(fetchedStock.getFixedDividend(), fixedDividend);
		assertEquals(fetchedStock.getLastDividend(), lastDividend);
		assertEquals(fetchedStock.getParValue(), parValue);
		assertEquals(fetchedStock.getSymbol(), symbol);
		assertEquals(fetchedStock.getType(), type);
	}

	@Test
	public void getInvalidStockBySymbolTest() throws SimpleStockException, ExecutionException {
		assertEquals(false, stockService.getStockBySymbol("INVALID_SYMBOL").isPresent());
	}

}
