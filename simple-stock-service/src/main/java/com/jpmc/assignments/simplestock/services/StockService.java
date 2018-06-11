package com.jpmc.assignments.simplestock.services;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.StockTransaction;
import com.jpmc.assignments.simplestock.models.Stocks;

public interface StockService {

	/**
	 * 
	 * @param symbol
	 * @return
	 * @throws SimpleStockException 
	 */
	Optional<Stocks> getStockBySymbol(String symbol) throws SimpleStockException, ExecutionException;

	/**
	 * Calculates the dividend yield of a known stock 
	 * 
	 * @param transaction
	 *            StockTransaction identifies the stock and other calculation attributes.
	 * @return The dividend yield 
	 * 
	 * @throws SimpleStockException 
	 */
	StockTransaction calculateDividendYield( StockTransaction transaction ) throws SimpleStockException;
	
	/**
	 * Calculates the price earnings ratio of a known stock 
	 * 
	 * @param transaction
	 *            StockTransaction identifies the stock and other calculation attributes.
	 * @return The price earning ratio.
	 * 
	 * @throws SimpleStockException 
	 */
	StockTransaction calculatePriceEarningRatio( StockTransaction transaction ) throws SimpleStockException;
}
