package com.jpmc.assignments.simplestock.repository.impl;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.Stocks;
import com.jpmc.assignments.simplestock.repository.StockRepository;

@Repository
/**
*
* This is the main repository for all the stock activities.
* create a separate service as STORAGEService. 
* Ideal logic would have be to have a separate STORAGEService 
* This STORAGEService would be responsible for making the calls to DB or CACHE instead of calling repository directly
* 
* @author jnair1
*
*/
public class StockRepositoryImpl implements StockRepository {

	private Logger LOGGER = LoggerFactory.getLogger(StockRepository.class);

	private LoadingCache<String, Optional<Stocks>> stocksCache;
	
	@PostConstruct
	public void init() {

		stocksCache =  CacheBuilder.newBuilder()
     		.maximumSize(5)	// maximum 5 records can only be cached
     		.expireAfterAccess(30, TimeUnit.MINUTES) // stocks would be expired after configured timespan
     		.build(new CacheLoader<String, Optional<Stocks>>() 
     			{
					@Override
					public Optional<Stocks> load(String symbol) throws IOException, SimpleStockException {
						return getStockBySymbolFromDB(symbol);
					}
     			});
	}

	private Optional<Stocks> getStockBySymbolFromDB(String symbol) throws SimpleStockException {

		LOGGER.debug("Its an initial fetch, hence request came to DB. Dont worry next would be cache for SYMBOL={}", symbol);
		
		Stocks stock = EnumSet.allOf(Stocks.class).stream().filter(e -> e.getSymbol().equals(symbol.toUpperCase()))
					.findFirst().
					orElseThrow(() -> new SimpleStockException(
							String.format("GBSE is not dealing with SYMBOL=%s", symbol)));
		
		LOGGER.info("Stock found for STOCK_TYPE={}", stock.getType());
		return Optional.of(stock);
	}

	@Override
	public Optional<Stocks> getStockBySymbol(String symbol) throws SimpleStockException, ExecutionException {
		LOGGER.debug("Cache would be loading the data for stock {} going forward", symbol);
		return stocksCache.get(symbol);
	}
}

