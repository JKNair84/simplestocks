package com.jpmc.assignments.simplestock.resources;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.jpmc.assignments.simplestock.models.Indicator;
import com.jpmc.assignments.simplestock.models.Trade;
import com.jpmc.assignments.simplestock.services.TradeService;

/**
 * Trade resource test
 * @author jnair1
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = TradeResource.class, secure = false)
public class TradeResourceTest {
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private TradeService service;
	
	Trade mockSelling;
	Trade mockBuying;
	String mockId;
	Map<String,List<Trade>> mockTransactions;
	List<Trade> mockTrades;
	
	
	
	@Before
	public void prepare() {
		mockSelling = new Trade(Indicator.SELL, "POP", 11, 12);
		mockBuying = new Trade(Indicator.BUY, "POP", 22, 23);
		mockId = "3c3ff26e-2a80-4e2b-a485-d243c2665ec3";
		
		mockTransactions = new HashMap<String, List<Trade>>();
		mockTrades = new ArrayList<Trade>();
		mockTrades.add(mockSelling);
		mockTrades.add(mockBuying);
		mockTransactions.put("POP", mockTrades);
		
	}
	
	@Test
	public void recordBuying() throws Exception {
		
		given(service.recordTrade(Mockito.any())).willReturn(mockBuying);

		mvc.perform(post("/trade/transact?symbol=POP&price=12&quantity=1&type=BUY")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void recordSelling() throws Exception {
		
		given(service.recordTrade(Mockito.any())).willReturn(mockSelling);

		mvc.perform(post("/trade/transact?symbol=POP&price=12&quantity=1&type=SELL")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void recordSellingInValidIndicator() throws Exception {
		
		given(service.recordTrade(Mockito.any())).willReturn(mockSelling);

		mvc.perform(post("/trade/transact?symbol=POP&price=12&quantity=1&type=SELLs")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.message", is("Invalid trade type SELLs")));
	}
	
	@Test
	public void recordSellingInValidQuantity() throws Exception {
		
		given(service.recordTrade(Mockito.any())).willReturn(mockSelling);

		mvc.perform(post("/trade/transact?symbol=POP&price=12&quantity=-1&type=SELL")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.message", is("Validation failedInvalid price entered")));
	}
	
	@Test
	public void getRecentTransactions() throws Exception {
		
		given(service.getRecentTransactions()).willReturn(mockTransactions);

		mvc.perform(get("/trade/transactions")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void calculateGBCEIndex() throws Exception {
		
		BigDecimal mockedDecimal = BigDecimal.valueOf(1d);
		given(service.calculateGBCEIndex()).willReturn(mockedDecimal);

		mvc.perform(get("/trade/index")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.gbceIndex", is(1.0)));
	}
	
	@Test
	public void calculateVolumeWeightedPriceForStock() throws Exception {
		
		BigDecimal mockedDecimal = BigDecimal.valueOf(1d);
		given(service.calculateVolumeWeightedPriceForStock(Mockito.any())).willReturn(mockedDecimal);

		mvc.perform(get("/trade/volumeWeightedPrice?symbol=GIN")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.calculateVolumeWeightedPrice", is(1.0)));
	}
	
	@Test
	public void calculateVolumeWeightedPriceForInvalidStock() throws Exception {
		
		BigDecimal mockedDecimal = BigDecimal.valueOf(1d);
		given(service.calculateVolumeWeightedPriceForStock(Mockito.any())).willReturn(mockedDecimal);

		mvc.perform(get("/trade/volumeWeightedPrice?symbol=GINs")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Validation failedSymbol length is not 3")));
	}


}
