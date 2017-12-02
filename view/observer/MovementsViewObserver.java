package com.medusabookdepot.view.observer;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelImpl.TransferImpl;
import com.medusabookdepot.model.modelInterface.Transfer;

import javafx.collections.ObservableList;

public interface MovementsViewObserver {
	
	/**
	 * Load the MovementsController object or create a new if it doesn't exists
	 * @return Temporaneous ObservableList with the last added movements
	 */
	public ObservableList<TransferImpl> getTempData();

	/**
	 * Add a movement from Transfer object and save it in file
	 * 
	 * @param Movement
	 */
	public void addMovement(TransferImpl transfer);

	/**
	 * Add a new transfer starting by passed strings
	 * 
	 * @param Sender
	 * @param Receiver
	 * @param Leaving
	 *            date
	 * @param Book
	 * @param Quantity
	 * @param Tracking
	 *            number
	 */
	public void addMovement(String sender, String receiver, Date leavingDate, String book, String quantity,
			String trackingNumber);

	/**
	 * Remove one movement from list and adjust the books in depots
	 * 
	 * @param One
	 *            ore more movements
	 * @throws <b>NoSuchElementException</b>
	 *             if you are trying to remove a movement that not exists
	 */
	public void removeMovement(TransferImpl t) throws NoSuchElementException ;

	public String getMovementType(String sender, String receiver);

	/**
	 * Search a transfer in list by tracking number
	 * 
	 * @param <b>Tracking
	 *            number</b>
	 * @return The transfer if it was found, else <b>null</b>
	 */
	public Transfer searchTrasferByTrackingNumber(String tracking);

	/**
	 * Search a string in ALL fields of movement object and add it to results if
	 * it is contained in field
	 * 
	 * @param Value
	 * @return A list of results
	 */
	public List<TransferImpl> searchMovements(String value);

	/**
	 * Assign a passed tracking number at a movement
	 * 
	 * @param <b>Tracking
	 *            number</b> given by courier
	 * @param <b>The
	 *            movement</b> to assign it
	 * @throws <b>IllegalArgumentException</b>
	 *             if there is a registered movement with the same tracking
	 *             number
	 */
	public void assignTrackingNumber(String tracking, Transfer movement) throws IllegalArgumentException;

	/**
	 * Search for transfer from StandardBook
	 * 
	 * @param StandardBookImpl
	 */
	public TransferImpl getTransferFromBook(StandardBookImpl book);

	/**
	 * Check if arguments passed are valid
	 * 
	 * @param Sender
	 * @param Receiver
	 * @param Leaving
	 *            date
	 * @param Book
	 * @param Quantity
	 * @return <b>True</b> if the arguments passed are valid
	 */
	public boolean isMovementValid(String sender, String receiver, Date leavingDate, String book, String quantity);

	/**
	 * Check if the string passed is a depot name
	 * 
	 * @param Name
	 * @return <b>True</b> if really it is, else <b>False</b>
	 */
	public boolean isADepot(String name);

	/**
	 * Return the list of movements
	 * 
	 * @return Movements list
	 */
	public ObservableList<TransferImpl> getMovements();

	/**
	 * @return A ObservableList of all books titles
	 */
	public ObservableList<String> getTitlesString();

	/**
	 * @return A ObservableList of all books ISBNs
	 */
	public ObservableList<String> getIsbnsString();

	/**
	 * @return A ObservableList of only CanSendTransferrer(s) name
	 */
	public ObservableList<String> getCanSendTransferrersString();

	/**
	 * @return A ObservableList of all customers name
	 */
	public ObservableList<String> getCustomersAndDepotsString();

	/**
	 * Return a ObservableList with all ISBNs relative a title (A title may to
	 * more than one ISBN, like "Introduction in Java")
	 * 
	 * @param Title
	 */
	public ObservableList<String> getAllIsbnFromTitle(String title);

	/**
	 * @return An OservableList contains all years that have movements
	 */
	public ObservableList<String> getYearsWithMovements();
	/**
	 * In base of sender return a list of possible receivers
	 * 
	 * @param Sender
	 *            name
	 * @return ObservableList with all possible receivers
	 */
	public ObservableList<String> getReceiversFromSender(String sender);

	/**
	 * Takes a transferrer name and if it's a depot put in list all books inside
	 * its, if it's a library put in list all book titles
	 * 
	 * @param Transferrer
	 *            name
	 * @return A ObservableList of titles
	 */
	public ObservableList<String> getTitleFromTransferrer(String transferrer);
	
	/**
	 * Convert the XML file to PDF
	 * 
	 * @throws IOException
	 */
	public void convert() throws IOException;
}
