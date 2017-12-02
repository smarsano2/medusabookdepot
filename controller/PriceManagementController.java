package com.medusabookdepot.controller;

public class PriceManagementController implements PriceManagementInterface{
	
	public int convertPrice(String price) throws IllegalArgumentException, IndexOutOfBoundsException {

		if (price.equals(""))
			throw new IllegalArgumentException("The price field mustn't be empty!");

		if (!price.contains(".") && !price.contains(",")) {
			price += ".00";
		} else if (price.charAt(price.length() - 2) == '.' || price.charAt(price.length() - 2) == ',') {
			price += "0";
		} else if (price.charAt(price.length() - 3) == '.' || price.charAt(price.length() - 3) == ',') {
			// Correct input, nothing to do
		} else
			throw new IllegalArgumentException("Price format not valid! (IE 12.50)");

		return Integer.parseInt(new StringBuilder(price).deleteCharAt(price.length() - 3).toString());
	}

	public String convertPriceToString(int price) {

		if(price == 0){
			return "0.00";
		}
		String priceInt = Integer.toString(price).substring(0, Integer.toString(price).length() - 2);
		String priceCent = Integer.toString(price).substring(Integer.toString(price).length() - 2);
		return (priceInt + "." + priceCent);
	}
}
