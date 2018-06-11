package com.jpmc.assignments.simplestock.repository;

import java.util.List;
import java.util.Map;

import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.Trade;

/**
 * 
 * @author jnair1
 *
 */
public interface TradeRepository {

	/**
	 * This method would extract all the trades from the main database.
	 * this is not implemented yet as I am storing all the data in cache 
	 * and not in database 
	 * 
	 * @return
	 * @throws SimpleStockException
	 */
	public Map<String, List<Trade>> getAllTrades()  throws SimpleStockException;
	
	/**
	 * persist the transactions in cache
	 * 
	 * @param trade
	 * @return
	 * @throws SimpleStockException
	 */
	public Trade recordTrade(Trade trade) throws SimpleStockException;
	
	/**
	 * This method will extract all the recent transactions 
	 * 
	 * @return
	 * @throws SimpleStockException
	 */
	public List<Trade> getRecentTrades() throws SimpleStockException;
	
	/**
	 * This method is similar to recentTransactios one,
	 * only difference is, this would get all the trades associated to a 
	 * particular symbol selected by user 
	 * 
	 * @param symbol
	 * @return
	 * @throws SimpleStockException
	 */
	public List<Trade> getRecentTradesBySymbol(String symbol) throws SimpleStockException;

}
