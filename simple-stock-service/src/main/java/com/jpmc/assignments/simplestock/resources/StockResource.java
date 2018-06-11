package com.jpmc.assignments.simplestock.resources;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.StockTransaction;
import com.jpmc.assignments.simplestock.models.Stocks;
import com.jpmc.assignments.simplestock.services.StockService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author jnair1
 *
 */
@RestController
@RequestMapping("/stocks")
@Api(value = "STOCK", description = "This section details the Stock APIs", position = 1)
public class StockResource {

	private StockService service;
	private Logger LOGGER = LoggerFactory.getLogger(StockResource.class);
	
	@Autowired
	public StockResource(StockService service) {
		this.service = service;
	}

	/**
	 * 
	 * @param symbol
	 * @param price
	 * @return
	 * @throws SimpleStockException 
	 * @throws ExecutionException 
	 */
	@ApiOperation (
			value="Calculate dividend based on Symbol and MarketPrice",
			response=StockTransaction.class)
	@RequestMapping(value="/dividend", method=RequestMethod.GET)
	public StockTransaction calculateDividendYield(
			@ApiParam(value = "Symbol on which trade needs to happen {TEA,POP,ALE,GIN,JOE}", required = true)
			@RequestParam(value = "symbol", defaultValue="GIN") String symbol,
			@ApiParam(value = "Market price", required = true)
			@RequestParam(value = "price", defaultValue="3") float price) throws SimpleStockException, ExecutionException {

		LOGGER.info("calling calculateDividendYield() with SYMBOL={}, PRICE={}", symbol, price);
		// perform basic validations
		checkArgument(StringUtils.isNotBlank(symbol)  & symbol.length() == 3, "Symbol length is not 3");
		checkArgument(price > 0, "Invalid price entered");
		
		
		Optional<Stocks> identifiedStock = service.getStockBySymbol(symbol);
		StockTransaction transaction = service.calculateDividendYield(new StockTransaction(price, identifiedStock.get()));
		
		LOGGER.info("Calculated Dividend for SYMBOL={}, is={} ",identifiedStock.get().getSymbol(), transaction.getDividendYield());
		  
		return transaction;
	}

	/**
	 * 
	 * @param symbol
	 * @param price
	 * @return
	 * @throws SimpleStockException 
	 * @throws ExecutionException 
	 */
	@ApiOperation (
			value="Calculate price earning ratio based on Symbol and MarketPrice",
			response=StockTransaction.class)
	@RequestMapping(value="/pe-ratio", method=RequestMethod.GET)
	public StockTransaction calculatePriceEarningRatio(
			@ApiParam(value = "Symbol on which trade needs to happen {TEA,POP,ALE,GIN,JOE}", required = true)
			@RequestParam(value = "symbol", defaultValue="GIN") String symbol,
			@ApiParam(value = "Market price", required = true)
			@RequestParam(value = "price", defaultValue="3") float price) throws SimpleStockException, ExecutionException {

		LOGGER.info("calling calculatePriceEarningRatio() with SYMBOL={}, PRICE={}", symbol, price);
		// perform basic validations
		checkArgument(StringUtils.isNotBlank(symbol)  & symbol.length() == 3, "Symbol length is not 3");
		checkArgument(price > 0, "Invalid price entered");
		
		
		Optional<Stocks> identifiedStock = service.getStockBySymbol(symbol);
		StockTransaction transaction = service.calculatePriceEarningRatio(new StockTransaction(price, identifiedStock.get()));
			
			
		LOGGER.info("Calculated PriceEarning Ratio for SYMBOL={}, is={} ",identifiedStock.get().getSymbol(), transaction.getPeRatio());
		  
		return transaction;
	}
}
