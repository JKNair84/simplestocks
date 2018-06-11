package com.jpmc.assignments.simplestock.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.OperationNotSupportedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.Trade;
import com.jpmc.assignments.simplestock.repository.TradeRepository;
import com.jpmc.assignments.simplestock.services.TradeService;

@Service
/**
 * Trading service impl
 * @author jnair1
 *
 */
public class TradeServiceImpl implements TradeService {
	
	private Logger LOGGER = LoggerFactory.getLogger(TradeService.class);
	private TradeRepository tradeRepository;
	
	@Autowired
	public TradeServiceImpl(TradeRepository tradeRepository) {
		this.tradeRepository = tradeRepository;
	}
	
	@Override
	public Map<String, List<Trade>> getRecentTransactions() throws SimpleStockException {

		// this needs to be paginated if the size increases in future. Only pages would be retrieved, instead of entire dataset
		List<Trade> trades = tradeRepository.getRecentTrades();
		
		//all the trades are grouped by SYMBOL
		Map<String, List<Trade>> tradesStockWise = trades.stream().collect(Collectors.groupingBy(p -> p.getSymbol()));
		LOGGER.info("Fetched the trades from the CACHE");		
		return tradesStockWise;
	}

	
	@Override
	public Trade recordTrade(Trade trade) throws SimpleStockException {
		LOGGER.info("Recording TRADE={}", trade);
		return tradeRepository.recordTrade(trade);
	}

	
	@Override
	public BigDecimal calculateVolumeWeightedPriceForStock(String symbol) throws SimpleStockException {
		LOGGER.info("Calculate the volume weight for selected SYMBOL={}", symbol);
		
		// only the specific transactions associated with the selected SYMBOL needs to be selected
		// needs to be paginated if we are planning to persist large amount of data
		List<Trade> trades = tradeRepository.getRecentTradesBySymbol(symbol);
		
		BigDecimal result = BigDecimal.ZERO;
		if( trades.size() == 0) {
			return BigDecimal.ZERO;
		}
		
		BigDecimal priceSum = BigDecimal.ZERO;
		BigInteger quantitySum = BigInteger.ZERO;
		
		for (Trade record : trades) {

			priceSum = priceSum.add(BigDecimal.valueOf(record.getPrice()).multiply(BigDecimal.valueOf(record.getQuantity())));
			quantitySum = quantitySum.add(BigInteger.valueOf(record.getQuantity()));
		}
		result = priceSum.divide(new BigDecimal(quantitySum),7,3).setScale(0, BigDecimal.ROUND_HALF_EVEN);
		LOGGER.info("Calculated VOLUME_WEIGHTED_PRICE={}", result);
		return result;
	}

	
	@Override
	public BigDecimal calculateGBCEIndex() throws SimpleStockException {
		
		List<Trade> trades = tradeRepository.getRecentTrades();
		if(trades.size() == 0) {
			return BigDecimal.ZERO;
		}
		
		BigDecimal tradePrice = BigDecimal.ONE;
		for(Trade trade: trades) {
			tradePrice = tradePrice.multiply(BigDecimal.valueOf(trade.getPrice()));
		}
		BigDecimal index = new BigDecimal(Math.pow(tradePrice.doubleValue(), 1.0 / trades.size())).setScale(2, BigDecimal.ROUND_HALF_UP); 
		LOGGER.info("Calculated the GBCE_INDEX={}", index);
		return index;
	}
	
	@Override
	public Map<String, List<Trade>> getTransactionHistories() throws OperationNotSupportedException {
		// not implemented - this would be when there is a need to get complete list of transactions from DB
		throw new OperationNotSupportedException();
	}

}
