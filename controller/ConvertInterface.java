package com.medusabookdepot.controller;

/**
 * 
 * Management of conversions
 *
 */
public interface ConvertInterface {

	/**
	 * Convert price from string to integer
	 * 
	 * @param <b>Book
	 *            price</b> in string format
	 * @return Price in integer format
	 * @throws IllegalArgumentException
	 *             and IndexOutOfBoundsException
	 */
	public int convertPrice(String price);

	/**
	 * Convert price from string to integer
	 * 
	 * @param <b>Book
	 *            price</b> in Integer format
	 */
	public String convertPriceToString(int price);
}
