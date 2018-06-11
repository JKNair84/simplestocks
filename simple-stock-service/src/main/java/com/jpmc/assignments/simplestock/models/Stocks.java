package com.jpmc.assignments.simplestock.models;

/**
 * 
 * @author jnair1
 *
 */
public enum Stocks {

	TEA("TEA", StockTypes.COMMON, 0, 0, 100),
	POP("POP", StockTypes.COMMON, 8, 0, 100),
	ALE("ALE", StockTypes.COMMON, 23, 0, 60),
	GIN("GIN", StockTypes.PREFERRED, 8, 0.02, 100),
	JOE("JOE", StockTypes.COMMON, 13, 0, 250);
	
	
	private String symbol;
	
	private StockTypes type;
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public StockTypes getType() {
		return type;
	}

	public void setType(StockTypes type) {
		this.type = type;
	}

	public double getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(double lastDividend) {
		this.lastDividend = lastDividend;
	}

	public double getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public double getParValue() {
		return parValue;
	}

	public void setParValue(double parValue) {
		this.parValue = parValue;
	}

	private double lastDividend;
	private double fixedDividend;
	private double parValue;
	
	private Stocks(String symbol, StockTypes type, double lastDividend, double fixedDividend, double parValue) {
		this.symbol = symbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
		
	}
	
	
	
}
