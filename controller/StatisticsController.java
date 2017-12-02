package com.medusabookdepot.controller;

import java.time.LocalDate;
import java.time.ZoneId;

import com.medusabookdepot.model.modelImpl.TransferImpl;
import com.medusabookdepot.view.observer.StatisticsViewObserver;

public class StatisticsController extends ConvertController implements StatisticsViewObserver{

	private static StatisticsController singStats;
	
	private static final int MONTHS = 12;
	// Create a monthCounter for each month. Add his values to the series.
	private final double[] priceMonthCounter = new double[MONTHS];
	private final int[] movMonthCounter = new int[MONTHS];
	private final int[] intMonthCounter = new int[MONTHS];
	
	/**
	 * Load the StatisticsController object or create a new if it doesn't exists
	 */
	public static StatisticsController getInstanceOf() {

		return (StatisticsController.singStats == null ? new StatisticsController() : StatisticsController.singStats);
	}
	
	public int[] getMovStats(String year){
		// Clear the barChart and the monthCounter before changing year	
	    for (int i = 0; i < movMonthCounter.length; i++) movMonthCounter[i]=0;
	        
	    for (TransferImpl movement : MovementsController.getInstanceOf().getMovements()) {
	    	// Convert the util.Date to LocalDate
	    	LocalDate date = movement.getLeavingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			// Filter movements by year
	    	if(date.getYear()==Integer.parseInt(year)){
	            int month = date.getMonthValue() - 1; 
	            movMonthCounter[month]++; // Increment the month according to the number of movements
	    	}
	    }
	    return movMonthCounter;
	}
	
	public double[] getPriceStats(String year){
	// Clear the barChart and the monthCounter before changing year	
    for (int i = 0; i < priceMonthCounter.length; i++) priceMonthCounter[i]=0;
        
    for (TransferImpl movement : MovementsController.getInstanceOf().getMovements()) {
    	// Convert the util.Date to LocalDate
    	LocalDate date = movement.getLeavingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		// Filter movements by year
    	// Filter movements by year
	    if(date.getYear()==Integer.parseInt(year)){
	    	int month = date.getMonthValue() - 1; 
	            
	    	switch (movement.getType().toLowerCase()) {
	    	case "return":
	    		intMonthCounter[month]-=movement.getTotalPrice(); // Increment the month according to the number of movements
	    		break;
	    	case "sold":
	    		intMonthCounter[month]+=movement.getTotalPrice(); // Increment the month according to the number of movements
	    		break;
	    	}
	    }
	    
	    for(int i=0; i<priceMonthCounter.length;i++) 
	    	priceMonthCounter[i] = Double.parseDouble(this.convertPriceToString(intMonthCounter[i]));
    }
    
    return priceMonthCounter;
	}
}
