package com.jpmc.assignments.simplestock.models;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 
 * @author jnair1
 *
 */
public class Trade {

	private final String id;
	private final String symbol;

	private final Integer quantity;

	private final Indicator indicator;

	private final Double price;

	private final LocalDateTime tradeDate;

	public Trade(Indicator indicator, String symbol, int quantity, double price) {
		this.id = UUID.randomUUID().toString();
		this.indicator = indicator;
		this.symbol = symbol;
		this.quantity = quantity;
		this.price = price;
		this.tradeDate = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getTradeDate() {
		return tradeDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public double getPrice() {
		return price;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + symbol.hashCode();
		result = prime * result + indicator.hashCode();
		result = prime * result + tradeDate.hashCode();
		result = prime * result + id.hashCode();
		result = prime * result + quantity.hashCode();
		result = prime * result + price.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Trade trade = (Trade) obj;
		if (!id.equals(trade.getId()))
			return false;
		if (!symbol.equals(trade.getSymbol()))
			return false;
		if (quantity != trade.getQuantity())
			return false;
		if (price != trade.getPrice())
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Trade [id=" + id + ", indicator=" + indicator + ", quantity=" + quantity + ", symbol=" + symbol + ", price="
				+ price + ", tradeDate=" + tradeDate + "]";
	}

}
