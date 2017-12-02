package com.medusabookdepot.view.observer;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import com.medusabookdepot.model.modelImpl.CustomerImpl;

import javafx.collections.ObservableList;

public interface CustomersViewObserver {

	/**
	 * Add a new customer to the list of all customers
	 * 
	 * @param Name
	 *            of new customer
	 * @param Address
	 *            of new customer
	 * @param Telephone
	 *            number of new customer
	 * @param Type
	 *            of customer: 1) Library 2) Person
	 */
	public void addCustomer(String name, String address, String telephoneNumber, String type);
	
	/**
	 * Search a string in all field of customer properties
	 * @param Value to search
	 * @return List of customers that contains the value
	 */
	public List<CustomerImpl> searchCustomer(String value);
	
	/**
	 * Search a customer in his list
	 * 
	 * @param Name
	 *            of customer
	 * @param Address
	 *            of customer
	 * @param Telephone
	 *            number of customer
	 * @return None elements if it doesn't find the element(s)
	 */
	public Stream<CustomerImpl> searchCustomer(Optional<String> name, Optional<String> address,
			Optional<String> telephoneNumber);

	/**
	 * Check if a customer is already present in list
	 * @param Address
	 * @param Telephone number
	 * @return <b>True</b> if he's already present, else <b>False</b>
	 */
	public boolean customerIsPresent(String address, String telephoneNumber);

	/**
	 * Remove a customer from the list
	 * 
	 * @param Customer
	 * @throws NoSuchElementException
	 *             if it doesn't find the customer
	 */
	public void removeCustomer(CustomerImpl customer) throws NoSuchElementException;
	
	/**
	 * Edit a customer name
	 * 
	 * @param Customer
	 * @param Name
	 */
	public void editName(CustomerImpl customer, String name);

	/**
	 * Edit a customer address
	 * 
	 * @param Customer
	 * @param Address
	 */
	public void editAddress(CustomerImpl customer, String address);

	/**
	 * Edit a customer telephone number
	 * 
	 * @param Customer
	 * @param Telephone
	 */
	public void editPhone(CustomerImpl customer, String phone);
	
	/**
	 * @return The list of saved books
	 */
	public ObservableList<CustomerImpl> getCustomers();
	
	/**
	 * Convert the XML file to PDF
	 * 
	 * @throws IOException
	 */
	public void convert() throws IOException;
}
