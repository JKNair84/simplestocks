package com.jpmc.assignments.simplestock.services.impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.StockTransaction;
import com.jpmc.assignments.simplestock.models.Stocks;
import com.jpmc.assignments.simplestock.repository.StockRepository;
import com.jpmc.assignments.simplestock.services.StockService;

@Service
/**
 * Service for all the stocking activities
 * 
 * @author jnair1
 *
 */
public class StockServiceImpl implements StockService {
	
	private Logger LOGGER = LoggerFactory.getLogger(StockService.class);
	private StockRepository stockRepository;
	
	@Autowired
	public StockServiceImpl(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}
	

	/**
	 * @throws ExecutionException 
	 * 
	 */
	@Override
	public Optional<Stocks> getStockBySymbol(String symbol) throws SimpleStockException, ExecutionException {
		LOGGER.debug("Fetching the stocks by provided SYMBOL={}", symbol);
		return stockRepository.getStockBySymbol(symbol);
	}

	/**
	 * 
	 */
	@Override
	public StockTransaction calculateDividendYield(StockTransaction transaction) throws SimpleStockException {
		
		LOGGER.debug("calculating the dividend for STOCK_TRANSACTION={} ", transaction);
		
		double dividendYield = 0.0;
		Stocks stock = transaction.getStock();
		
		switch (stock.getType()) {
		case COMMON:
			dividendYield = stock.getLastDividend() / transaction.getPrice();
			break;
		case PREFERRED:
			dividendYield = (stock.getFixedDividend() * stock.getParValue()) / transaction.getPrice();
			break;
		default:
			throw new SimpleStockException("Invalid Stock Type");
	}

		StockTransaction result = new StockTransaction(transaction.getTransactionId(),dividendYield, 0, transaction.getPrice(), stock);
		LOGGER.info("dividend calculated for STOCK={}, of TYPE={} ", stock.getSymbol(), stock.getType());
		return result;
	}

	/**
	 * 
	 */
	@Override
	public StockTransaction calculatePriceEarningRatio(StockTransaction transaction) throws SimpleStockException {
		
		LOGGER.debug("calculating  price earning ratio STOCK_TRANSACTION={}",transaction);
		Stocks stock = transaction.getStock();
		double dividendYield = calculateDividendYield(transaction).getDividendYield();
		double priceEarningRatio = transaction.getPrice() / dividendYield;

		StockTransaction result = new StockTransaction(
				transaction.getTransactionId(),dividendYield, priceEarningRatio, transaction.getPrice(), stock);
		LOGGER.info("price earning ratio calculated for STOCK={}, of TYPE={} ", stock.getSymbol(), stock.getType());
		return result;
	}

	public void setProductRepository(StockRepository repository) {
		this.stockRepository = repository;
	}

}
