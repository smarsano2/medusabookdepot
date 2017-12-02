package com.medusabookdepot.view.observer;

public interface StatisticsViewObserver {
	/**
	 * It finds how many movements are done in months and put them in an integer
	 * array
	 * 
	 * @param Year
	 * @return Array of movements per month
	 */
	public int[] getMovStats(String year);

	/**
	 * It finds eatnings per month and put them in an array
	 * @param Year
	 * @return Array of earnings per month
	 */
	public double[] getPriceStats(String year);
}
