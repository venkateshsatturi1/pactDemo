package com.demo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AllSalesController {

	String baseUrl ="http://localhost:8081";

	@GetMapping(value = "/getAllSales")
	public TotalSales getAllSales() throws JsonMappingException, JsonProcessingException
	{
		TotalSales totalSales = new TotalSales();
		
		int onlineSales = 20;
		
		long sum = 0;
		for(int i=0;i<getAllStoreSaleDetails().length;i++)
		{
			sum = sum + getAllStoreSaleDetails()[i].getSales();
		}
		
		int inStoreSales = (int) sum;
		
		int overallSales = inStoreSales + onlineSales;
		totalSales.setInStoreSales(inStoreSales);
		totalSales.setOnlineSales(onlineSales);
		totalSales.setOverAllSales(overallSales);
		
		return totalSales;
		
		
	}
	
public StoreSalesData[] getAllStoreSaleDetails() throws JsonMappingException, JsonProcessingException
	
	{
		
		RestTemplate restTemplate =new RestTemplate();
		
		ResponseEntity<String>	response =restTemplate.getForEntity(baseUrl+"/storeSales", String.class);
		ObjectMapper mapper = new ObjectMapper();
	
		StoreSalesData[] allstoreSalesData = mapper.readValue(response.getBody(), StoreSalesData[].class);
		return allstoreSalesData;

		
		
	}

	public void setbaseURL(String url) {
		baseUrl = url;
	}

}
