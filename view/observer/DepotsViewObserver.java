package com.medusabookdepot.view.observer;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;

import javafx.collections.ObservableList;

public interface DepotsViewObserver {


	/**
	 * Add one ore more before created depots in the list
	 * 
	 * @param depot
	 */
	// non sono convinto della sua utilità...
	public void addDepot(DepotImpl... depot);

	/**
	 * Add a empty depot from name
	 * 
	 * @param <b>Name
	 *            of new depot</b>
	 * @throws IllegalArgumentException
	 *             if another depot is registered with the same name or it's
	 *             empty
	 */
	public void addDepot(String name) throws IllegalArgumentException;
	
	/**
	 * Filter depots list with name
	 * @param Name
	 * @return Stream of depots that name contains the string passed
	 */
	public Stream<DepotImpl>filterDepot(String name);
	
	/**
	 * Search a depot in the list
	 * 
	 * @param Depot
	 *            name
	 * @return Empty ObservableList if none depot was found, else the depot(s)
	 */
	public ObservableList<DepotImpl> searchDepot(String name);

	public ObservableList<Entry<StandardBookImpl, Integer>> searchDepot(
			ObservableList<Entry<StandardBookImpl, Integer>> set, String value);

	/**
	 * Remove a depot from the list
	 * 
	 * @param Depot
	 * @throws <b>NoSuchElementException</b>
	 *             if you are trying to remove a depot that not exists
	 */
	public void removeDepot(DepotImpl depot) throws NoSuchElementException;

	public void editName(DepotImpl depot, String name);

	/**
	 * Replace book quantity with new passed value, if passed value is 0, revove
	 * the book from depot
	 * 
	 * @param Depot
	 * @param Book
	 * @param Value
	 */
	public void editBookQuantity(DepotImpl depot, StandardBookImpl book, String value);

	/**
	 * Get the observable list of depots
	 */
	public ObservableList<DepotImpl> getDepots();

	/**
	 * Convert the XML file to PDF
	 * 
	 * @throws IOException
	 */
	public void convert() throws IOException;
}
