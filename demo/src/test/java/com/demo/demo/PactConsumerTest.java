package com.demo.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.demo.controller.AllSalesController;
import com.demo.demo.controller.TotalSales;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName="StoreSalesProvider")
public class PactConsumerTest {
	
	@Pact(consumer="AllSalesConsumer")
	public RequestResponsePact allSalesDetailsConfig(PactDslWithProvider builder)
	{
		return builder.given("Sales info")
		.uponReceiving("All Store Sales Details")
		.path("/storeSales")
		.willRespondWith()
		.status(200)
		.body(PactDslJsonArray.arrayMinLike(2)
				.stringType("storeLocaton")
				.integerType("sales", 100).closeObject()).toPact();
	}
	
	
	@Test
	@PactTestFor(pactMethod="allSalesDetailsConfig", port = "9999")
	public void testSalesSum(MockServer mockServer) throws JsonMappingException, JsonProcessingException
	{
		AllSalesController allSalesController = new AllSalesController();
		allSalesController.setbaseURL(mockServer.getUrl());
		TotalSales totalsales = allSalesController.getAllSales();
		
		String expectedResponse = "{\"onlineSales\":20,\"inStoreSales\":200,\"overAllSales\":220}";
		ObjectMapper obj = new ObjectMapper();
		String actualResponse = obj.writeValueAsString(totalsales);
		
		Assertions.assertEquals(expectedResponse, actualResponse);
	}
	

}
