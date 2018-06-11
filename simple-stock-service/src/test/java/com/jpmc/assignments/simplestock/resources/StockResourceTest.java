package com.jpmc.assignments.simplestock.resources;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.jpmc.assignments.simplestock.models.StockTransaction;
import com.jpmc.assignments.simplestock.models.Stocks;
import com.jpmc.assignments.simplestock.services.StockService;

/**
 * Stock service test
 * 
 * @author jnair1
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = StockResource.class, secure = false)
public class StockResourceTest {
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private StockService service;
	
	StockTransaction mockTransaction;
	
	@Before
	public void prepare() {
		mockTransaction = new StockTransaction(null, 0.7272727272727273, 18.0, 12.0, Stocks.POP); 
	}
	
	@Test
	public void calculateDividendYield() throws Exception {
		
		given(service.getStockBySymbol(Mockito.anyString())).willReturn(Optional.of(Stocks.POP));
		given(service.calculateDividendYield(Mockito.any())).willReturn(mockTransaction);

		mvc.perform(get("/stocks/dividend?symbol=POP&price=12")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.dividendYield", is(0.7272727272727273)))
				.andExpect(jsonPath("$.stock", is("POP")))
				.andExpect(jsonPath("$.price", is(12.0)));
	}

	
	@Test
	public void calculatePriceEarningRatio() throws Exception {
		
		given(service.getStockBySymbol(Mockito.anyString())).willReturn(Optional.of(Stocks.POP));
		given(service.calculatePriceEarningRatio(Mockito.any())).willReturn(mockTransaction);

		mvc.perform(get("/stocks/pe-ratio?symbol=POP&price=12")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.dividendYield", is(0.7272727272727273)))
				.andExpect(jsonPath("$.peRatio", is(18.0)))
				.andExpect(jsonPath("$.stock", is("POP")))
				.andExpect(jsonPath("$.price", is(12.0)));
	}
	
	@Test
	public void calculateDividendYieldForIncorrectURL() throws Exception {
		
		given(service.getStockBySymbol(Mockito.anyString())).willReturn(Optional.of(Stocks.POP));
		given(service.calculateDividendYield(Mockito.any())).willReturn(mockTransaction);

		mvc.perform(get("/stocks/invalid-dividend?symbol=POP&price=12")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
	
}
