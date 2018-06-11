package com.jpmc.assignments.simplestock.models;

import java.util.UUID;

/**
 * 
 * @author jnair1
 *
 */
public final class StockTransaction {

	private final String transactionId;
	
	private Double dividendYield;
	private Double peRatio;
	private Double price;

	private Stocks stock;

	
	public StockTransaction(String transactionId, double dividendYield, 
			double peRatio, double price, Stocks stock) {
		this.transactionId = UUID.randomUUID().toString();
		this.dividendYield = dividendYield;
		this.peRatio = peRatio;
		this.price = price;
		this.stock = stock;
	}
	
	public StockTransaction(double price, Stocks stock) {
		this.transactionId = UUID.randomUUID().toString();
		this.price = price;
		this.stock = stock;
	}

	
	/**
	 * A stock is equal to another object if the object is a stock and if it's
	 * symbols are equal.
	 * 
	 * @param o
	 *            The object to be compared to this stock.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		StockTransaction transaction = (StockTransaction) o;
		
		if(stock.getSymbol().equals(transaction.getStock().getSymbol()) &&
				transactionId.equals(transaction.getTransactionId()) &&
						price==transaction.getPrice() &&
						dividendYield == transaction.getDividendYield()){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + stock.getSymbol().hashCode();
		result = prime * result + dividendYield.hashCode();
		result = prime * result + peRatio.hashCode();
		result = prime * result + price.hashCode();
		result = prime * result + transactionId.hashCode();
		return result;
	
	}

	@Override
	public String toString() {
		return "Stock{" +  
				"transactionId='" + transactionId + "'" +
				"symbol='" + stock.getSymbol() + '\'' + ", stockType=" + stock.getType() + ", lastDividend=" + stock.getLastDividend()
				+ ", fixedDividend=" + stock.getFixedDividend() + ", parValue=" + stock.getParValue() 
				+ ", dividendYield=" + dividendYield + ", peRatio=" + peRatio + '}';
	}

	
	public String getTransactionId() {
		return transactionId;
	}

	public double getDividendYield() {
		return dividendYield;
	}

	public double getPeRatio() {
		return peRatio;
	}

	public double getPrice() {
		return price;
	}

	public Stocks getStock() {
		return stock;
	}
	
	

}
