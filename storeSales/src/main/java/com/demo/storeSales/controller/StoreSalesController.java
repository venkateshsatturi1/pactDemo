package com.demo.storeSales.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreSalesController {
	

	@GetMapping(value = "/storeSales")
	public List<StoreSalesData> getStoreSales()
	{
		List<StoreSalesData> allStoreData = new ArrayList();
		
		StoreSalesData canStore = new StoreSalesData();
		canStore.setStoreLocaton("Canada");
		canStore.setSales("100");
		
		StoreSalesData usStore = new StoreSalesData();
		usStore.setStoreLocaton("US");
		usStore.setSales("50");
		
		allStoreData.add(usStore);
		allStoreData.add(canStore);
		
		return allStoreData;
		
		
		
		
		
	}
	
}
