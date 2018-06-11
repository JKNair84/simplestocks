package com.jpmc.assignments.simplestock.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.Trade;
import com.jpmc.assignments.simplestock.repository.TradeRepository;

@Repository
/**
*
* This is the main repository for all the trading activities.
* create a separate service as STORAGEService. 
* Ideal logic would have be to have a separate STORAGEService 
* This STORAGEService would be responsible for making the calls to DB or CACHE instead of calling repository directly
* 
* @author jnair1
*
*/
public class TradeRepositoryImpl implements TradeRepository {

	private Logger LOGGER = LoggerFactory.getLogger(TradeRepository.class);

	private Cache<String, List<Trade>> tradeDB;
	private Cache<String, Trade> inMemoryTradeCache;
	
	private final long tradeDBExpirationTime = 10l;
	private final long tradeCacheExpirationTime = 60l;
	
	@PostConstruct
	public void init() {
		
		tradeDB = CacheBuilder.newBuilder().expireAfterWrite(tradeDBExpirationTime, TimeUnit.MINUTES).build();
		inMemoryTradeCache = CacheBuilder.newBuilder()
				.expireAfterWrite(tradeCacheExpirationTime, TimeUnit.SECONDS)
				.removalListener(new RemovalListener<String, Trade>() {

					{
	                    LOGGER.debug("Removal Listener created");
	                }
					@Override
					public void onRemoval(RemovalNotification<String, Trade> notification) {

						LOGGER.info("Going to remove data from InputDataPool {}", notification.getKey());
						persistInDB(notification.getValue());
					}
				})
				.build();
	}



	@Override
	public Trade recordTrade(Trade trade) throws SimpleStockException {
		// form a unique key and store
		inMemoryTradeCache.put("~" +trade.getSymbol() + "~" + trade.getId(), trade);
		return trade;
	}


	@Override
	public List<Trade> getRecentTrades() throws SimpleStockException {

		List<Trade> recentTrades = new ArrayList<Trade>(inMemoryTradeCache.asMap().values());
		return recentTrades;
	}

	private String persistInDB(Trade trade) {
		LOGGER.debug("TRADE={} is now getting persisted in main database", trade);
		List<Trade> trades = tradeDB.asMap().getOrDefault(trade.getSymbol(), new ArrayList<Trade>());
		trades.add(trade);
		LOGGER.info("Persisted TRADE_ID={}", trade.getId());
		return trade.getId();	
	}

	@Override
	public List<Trade> getRecentTradesBySymbol(String symbol) throws SimpleStockException {
		LOGGER.info("Fetch transactions based on SYMBOL={}", symbol);
		 List<Trade> selectedTrades = inMemoryTradeCache.asMap().entrySet().stream()
		            .filter(entry ->entry.getKey().contains("~" + symbol + "~"))
		            .map(entry -> entry.getValue())
		            .collect(Collectors.toList());
		return selectedTrades;
	}
	
	@Override
	public Map<String, List<Trade>> getAllTrades() throws SimpleStockException {
		// TODO Implementation for actual database. Intentionally left blank
		return null;
	}

}
