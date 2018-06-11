package com.jpmc.assignments.simplestock.resources;

import static com.google.common.base.Preconditions.checkArgument;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.assignments.simplestock.exception.SimpleStockException;
import com.jpmc.assignments.simplestock.models.Indicator;
import com.jpmc.assignments.simplestock.models.Trade;
import com.jpmc.assignments.simplestock.services.TradeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/trade")
@Api(value = "TRADE", description = "This section details the Trading APIs", position = 2)
/**
 * 
 * @author jnair1
 *
 */
public class TradeResource {

	private Logger LOGGER = LoggerFactory.getLogger(TradeResource.class);
	
	private TradeService tradeService; 
	
	@Autowired
	public TradeResource(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
	
	/**
	 * rest api exposed for displaying all the transactions
	 * 
	 * @return
	 * @throws SimpleStockException
	 */
	@ApiOperation (
			value="Get all the recent transactions",
			response=Map.class)
	@RequestMapping(value = "/transactions", method= RequestMethod.GET)
	public Map<String, List<Trade>> getRecentTransactions() throws SimpleStockException {
		Map<String, List<Trade>> response = new HashMap<String, List<Trade>>();
		response = tradeService.getRecentTransactions();
		
		// print entire data in case of only debug
		if(LOGGER.isDebugEnabled()){
			print(response);
		}
		return response;
	}

	/**
	 * rest api for buying and selling
	 * 
	 * @param symbol
	 * @param price
	 * @param quantity
	 * @param type
	 * @return
	 * @throws SimpleStockException
	 */
	@ApiOperation (
			value="Record a trade, with timestamp, quantity of shares, buy or sell indicator and trade price",
			response=Map.class)
	@RequestMapping(value = "/transact", method= RequestMethod.POST)
	public Map<String, Trade> trade(
			@ApiParam(value = "Symbol on which trade needs to happen {TEA,POP,ALE,GIN,JOE}", required = true)
			@RequestParam(value="symbol", defaultValue="GIN") String symbol,
			@ApiParam(value = "Trade Price", required = true)
			@RequestParam(value="price", defaultValue="10") double price,
			@ApiParam(value = "Share Quantity that needs to be traded", required = true)
			@RequestParam(value="quantity", defaultValue="10") int quantity,
			@ApiParam(value = "Trading Indicator {BUY,SELL}", required = true)
			@RequestParam(value="type", defaultValue="BUY") String type) throws SimpleStockException {
		
		//validate the input
		checkArgument(StringUtils.isNotBlank(symbol)  & symbol.length() == 3, "Symbol length is not 3");
		checkArgument(price > 0, "Invalid price entered");
		checkArgument(quantity > 0, "Invalid price entered");
		
		Indicator tradeType = EnumSet.allOf(Indicator.class).stream().filter(e -> e.getType().equals(type.toUpperCase()))
		.findFirst().orElseThrow(() -> new SimpleStockException(String.format("Invalid trade type %s", type)));
		
		
		Trade capturedTrade = tradeService.recordTrade(new Trade(tradeType, symbol, quantity, price));

		LOGGER.info("Trade recorded with TRANSACTION_ID={}", capturedTrade.getId());
		Map<String, Trade> response = new HashMap<String, Trade>();
		response.put("transactionDetails", capturedTrade);
		return response;
	}
	
	/**
	 * rest api to calculate the volume weighted price. This would be specific to the stock.
	 * All the trades would be fetched for the specified stock
	 * 
	 * @param symbol
	 * @return
	 * @throws SimpleStockException
	 */
	@ApiOperation (
			value="Calculate Volume Weighted Weight Price for the Symbol",
			response=Map.class)
	@RequestMapping(value="/volumeWeightedPrice", method = RequestMethod.GET)
	public Map<String, BigDecimal> calculateVolumeWeightedStockPrice(
			@ApiParam(value = "Symbol on which trade needs to happen {TEA,POP,ALE,GIN,JOE}", required = true)
			@RequestParam(value="symbol", defaultValue="GIN") String symbol) throws SimpleStockException {
		
		checkArgument(StringUtils.isNotBlank(symbol)  & symbol.length() == 3, "Symbol length is not 3");
		
		Map<String, BigDecimal> response = new HashMap<String, BigDecimal>();
		response.put("calculateVolumeWeightedPrice", tradeService.calculateVolumeWeightedPriceForStock(symbol));;
		return response;
	}
	
	
	/**
	 * rest api to call the GBCE index
	 * @return
	 */
	@ApiOperation (
			value="Calculate GBCE Index",
			response=Map.class)
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public Map<String, BigDecimal> calculateGBCEIndex() {
		
		Map<String, BigDecimal> response = new HashMap<String, BigDecimal>();
		try {
			response.put("gbceIndex", tradeService.calculateGBCEIndex());;
		} catch (SimpleStockException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	/*
	 * print response
	 * @param response
	 */
	private void print(Map<String, List<Trade>> response) {
		
		LOGGER.debug("-------- Summary of all trades performed --------");
	    LOGGER.debug("       |        | # stocks | # stocks | highest  |  lowest  ");
		LOGGER.debug(" Stock | total  |   bought |   sold   | price    |  price   ");
		LOGGER.debug("-------|--------|----------|----------|----------|----------");
		
		response.entrySet().parallelStream().forEach(entry -> {

			List<Trade> tradeLines = entry.getValue();
			if(tradeLines.size() > 0) {
				long stockTradesCount = tradeLines.stream().count();
				int stockPurchases = tradeLines.stream().filter(trade -> trade.getIndicator() == Indicator.BUY)
						.mapToInt(Trade::getQuantity).sum();
				int stockSales = tradeLines.stream().filter(trade -> trade.getIndicator() == Indicator.SELL)
						.mapToInt(Trade::getQuantity).sum();
				double maxStockPrice = tradeLines.stream().mapToDouble(Trade::getPrice).max()
						.orElse(Double.NaN);
				double minStockPrice = tradeLines.stream().mapToDouble(Trade::getPrice).min()
						.orElse(Double.NaN);

				LOGGER.debug(String.format(" %5.5s | %6d | %8d | %8d | %8.3f | %8.3f ",
				          entry.getKey(), stockTradesCount, stockPurchases, stockSales, maxStockPrice, minStockPrice));
			}
		});
	}
}
