package com.jpmc.assignments.simplestock.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.Trade;

/**
 * 
 * @author jnair1
 *
 */
public interface TradeService {
	/**
	 * Retrieves all trades 
	 * 
	 * @return A collection of all trades stock wise.
	 */
	public Map<String, List<Trade>> getRecentTransactions()  throws SimpleStockException;

	/**
	 * Sells or Buy a number of shares of the stock represented by the stock symbol.
	 * Records a transaction of a number of shares of the stock represented by
	 * the stock symbol.
	 * 
	 * @param trade
	 *            The trade that needs to be bought against the specified stock, price and quantity.
	 *            
	 * @return transaction id 
	 * throws SimpleStockException
	 */
	public Trade recordTrade(Trade trade)  throws SimpleStockException;

	/**
	 * Retrieves all trades
	 * 
	 * @return A map, arranged by stockNames and list of trades happened against that particular stock.
	 * throws SimpleStockException
	 */
	public Map<String, List<Trade>> getTransactionHistories()  throws OperationNotSupportedException;
	
	
	/**
	 * Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
	 * @param symbol 

	 * @return
	 * @throws mean value
	 * @throws SimpleStockException
	 */
	public BigDecimal calculateVolumeWeightedPriceForStock(String symbol)  throws SimpleStockException;
	
	/**
	 * 
	 * @return
	 * @throws SimpleStockException
	 */
	public BigDecimal calculateGBCEIndex()  throws SimpleStockException;

}
