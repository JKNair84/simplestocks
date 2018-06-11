package com.jpmc.assignments.simplestock.services;

import static com.jpmc.assignments.simplestock.models.StockTypes.COMMON;
import static com.jpmc.assignments.simplestock.models.StockTypes.PREFERRED;

import org.testng.annotations.DataProvider;

/**
 * DataSet for tests
 * 
 * @author jnair1
 *
 */
public class DataSetForStocksTests {


  @DataProvider(name = "DIVIDEND_PROVIDER")
  public static Object[][] dividendData() {
    
    return new Object[][] { 
    	// {symbol, price, expectedDividend}
    	{"TEA", 50, 0},
        {"POP", 50, 0.16},
        {"ALE", 100, 0.23},
        {"GIN", 130, 0.015384615384615385},
        {"JOE", 110, 0.11818181818181818}};
  }
  
  @DataProvider(name = "PE_RATIO_PROVIDER")
  public static Object[][] peRatioData() {
    
    return new Object[][] { 
    	// {symbol, price, expectedPE}
    	{"TEA", 45, Double.POSITIVE_INFINITY},
        {"POP", 12, 18.0},
        {"ALE", 33, 47.347826086956516},
        {"GIN", 67, 22.445},
        {"JOE", 23, 40.69230769230769}};
  }
  
  @DataProvider(name = "STOCKS_PROVIDER")
  public static Object[][] peStocksData() {
    
    return new Object[][] { 
    	// {symbol, type, lastDividend, fixedDividend, parValue}
    	{"TEA", COMMON, 0, 0, 100},
        {"POP", COMMON, 8, 0, 100},
        {"ALE", COMMON, 23, 0, 60},
        {"GIN", PREFERRED, 8, 0.02, 100},
        {"JOE", COMMON, 13, 0, 250}
       };
  }
  
  @DataProvider(name = "DATASET_FOR_DIVIDEND_CALC")
  public static Object[][] dividendDataProvider() {
    
    return new Object[][] { 
    	// {symbol, type, lastDividend, fixedDividend, parValue, expectedDividend, expectedPE}
    	{"TEA", COMMON, 0, 0, 100, 50, 0, Double.POSITIVE_INFINITY},
        {"POP", COMMON, 8, 0, 100, 50, 0.16, 6.25},
        {"ALE", COMMON, 23, 0, 60, 100, 0.23, 4.3478260869565215},
        {"GIN", PREFERRED, 8, 0.02, 100, 130, 0.015384615384615385, 16.25},
        {"JOE", COMMON, 13, 0, 250, 110, 0.11818181818181818, 8.461538461538462}};
  }
}
