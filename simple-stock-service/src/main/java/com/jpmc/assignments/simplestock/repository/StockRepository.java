package com.jpmc.assignments.simplestock.repository;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.Stocks;

/**
 * 
 * @author jnair1
 *
 */
public interface StockRepository {

	/**
	 * 
	 * @param symbol
	 * @return
	 * @throws SimpleStockException 
	 */
	Optional<Stocks> getStockBySymbol(String symbol) throws SimpleStockException, ExecutionException;

}
