# Super Simple Stock 

SimpleStock application that will - 

```
For a given stock,
	* Given a market price as input, calculate the dividend yield
	* Given a market price as input, calculate the P/E Ratio
	* Record a trade, with timestamp, quantity of shares, buy or sell indicator and trade price
	* Calculate Volume Weighted Stock Price based on trades in past 15 minutes
Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
```

# Getting Started

## This application is built with Java 8, using
* Maven 
* SpringBoot with web and tomcat starter
* Guava
* Swagger

## For Tests
* Testng
* Mockito
* BDDMOckito
	
# Overview
All the apis are exposed over rest and swagger integrated. This (localhost:8080/swagger-ui.html) can be used as the entry point as the application would be on port 8080.
```
## API details - there are two resource group - (\trade and \stock). Here are the enpoints
* GET /stocks/dividend
** Calculate dividend based on Symbol and MarketPrice
* GET /stocks/pe-ratio
** Calculate price earning ratio based on Symbol and MarketPrice
* GET /trade/index
** Calculate GBCE Index
* POST /trade/transact
** Record a trade, with timestamp, quantity of shares, buy or sell indicator and trade price
* GET /trade/transactions
** Get all the recent transactions
* GET /trade/volumeWeightedPrice
** Calculate Volume Weighted Weight Price for the Symbol
```
* **Guava** - *Guava is used for caching the as there is no need to store the data in database. Guava has its own internal eviction event, which would remove the elments after specified internal. Guava is quick when it comes to development and matched the requirement. There are other Caching like Redis,EHCache, Infinispan etc*
* **Swagger** - *Swagger gives nice api documentation and can be smoothly integrated. Also, gives an opportunity to test the code seamlessly*

# Exceptions
* have created a custom exception
* have also created the handler to cater to exceptions and generate appropriate response

# Tests
* tests have been written for RestServices, Service and Repositories using Mockito, BDDMockito, Hamcrest, TestNg, MockMvc

# Assumption and improvement
* all the transaction details are getting fetch directly from cache. In real time scenario, considering volume, we should have got it done painated
* calculation logic - currently everything is happening within java code, we should be using some external tools like MAPLE, MATLAB, numpy a python library is also helpful
* storage service - currently service layer is calling the repositories and repositories DB(Cache). We could have had a StorageService, with different implementations for Cache and DataBase
* support Json request instead of query params, and pass the same Json to Service
* In real world, user would be having a login page, we can have a request tracer (unique id) associated with his session. All the information that is getting logged would have this id printed. This makes analysing an issue on prod easier when integrated with SPLUNK or Kibana
* Guava - intention was to move the data that is getting removed from in-memory cache to database(another cache). Guava was not calling the onRemoval event immediately when the data is getting evicted
