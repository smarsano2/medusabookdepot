package com.medusabookdepot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import com.medusabookdepot.model.modelImpl.LibraryImpl;
import com.medusabookdepot.model.modelImpl.PersonImpl;
import com.medusabookdepot.model.modelImpl.PrinterImpl;
import com.medusabookdepot.view.observer.CustomersViewObserver;
import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.model.modelImpl.CustomerImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomersController implements CustomersViewObserver {

	private final ObservableList<CustomerImpl> customers = FXCollections.observableArrayList();

	private static CustomersController singCustomers;
	
	// Fields for file load and save, and for converting to PDF
	private final static String NAME = "customers";
	private FileManager<CustomerImpl> fileManager = new FileManager<>(customers, CustomerImpl.class, NAME);

	private CustomersController(){
		super();
	}

	public static CustomersController getInstanceOf() {

		return (CustomersController.singCustomers == null ? new CustomersController() : CustomersController.singCustomers);
	}

	public void addCustomer(String name, String address, String telephoneNumber, String type) {

		if (!this.isCustomerValid(name, address, telephoneNumber, type)) {
			throw new IllegalArgumentException(
					"FAIL: " + address + " and/or " + telephoneNumber + "are/is already present/s");
		}

		//To be sure
		type = type.toLowerCase();
		switch (type) {
		case "library":
			customers.add(new LibraryImpl(name, address, telephoneNumber));
			break;
		case "person":
			customers.add(new PersonImpl(name, address, telephoneNumber));
			break;
		case "printer":
			customers.add(new PrinterImpl(name, address, telephoneNumber));
			break;
		}

		fileManager.saveDataToFile();
	}
	
	public List<CustomerImpl> searchCustomer(String value){
		List<CustomerImpl> result = new ArrayList<>();
		value.toLowerCase();
		this.customers.stream().forEach(e->{
			if(e.getAddress().toLowerCase().contains(value) || e.getTelephoneNumber().toLowerCase().contains(value) || e.getName().toLowerCase().contains(value)){
				result.add(e);
			}
		});
		
		return result;
	}
	
	public Stream<CustomerImpl> searchCustomer(Optional<String> name, Optional<String> address,
			Optional<String> telephoneNumber) {

		Stream<CustomerImpl> result = this.customers.stream();

		if (name.isPresent()) {
			result = result.filter(e -> e.getName().toLowerCase().contains(name.get().toLowerCase()));
		}
		if (address.isPresent()) {
			result = result.filter(e -> e.getAddress().toLowerCase().contains(address.get().toLowerCase()));
		}
		if (telephoneNumber.isPresent()) {
			result = result.filter(e -> e.getTelephoneNumber().toLowerCase().contains(telephoneNumber.get().toLowerCase()));
		}

		return result;
	}

	public boolean customerIsPresent(String address, String telephoneNumber) {
		
		return (this.searchCustomer(Optional.empty(), Optional.of(address), Optional.of(telephoneNumber)).count() >= 1);
	}
	
	public boolean isCustomerValid(String name, String address, String telephoneNumber, String type){
		
		if(name.equals("") || address.equals("") || telephoneNumber.equals("") || type.equals("")){
			throw new IllegalArgumentException("The arguments must be not empty!");
		}
		
		return true;
	}

	public void removeCustomer(CustomerImpl customer) throws NoSuchElementException {

		try {
			customers.remove(customer);
		} catch (Exception e) {

			throw new NoSuchElementException("No such element in list!");
		}
		fileManager.saveDataToFile();
	}
	
	public void editName(CustomerImpl customer, String name) {

		if(!isCustomerValid(name, customer.getAddress(), customer.getTelephoneNumber(), customer.typeProperty().toString())){
			throw new IllegalArgumentException("Arguments are not right!");
		}
		try {
			customers.get(customers.indexOf(customer)).setName(name);
		} catch (Exception e) {
			throw new IllegalArgumentException("Customer not found");
		}
		
		fileManager.saveDataToFile();
	}

	public void editAddress(CustomerImpl customer, String address) {
		
		if(!isCustomerValid(customer.getName(), address, customer.getTelephoneNumber(), customer.typeProperty().toString())){
			throw new IllegalArgumentException("Arguments are not right!");
		}
		try {
			customers.get(customers.indexOf(customer)).setAddress(address);
		} catch (Exception e) {
			throw new IllegalArgumentException("Customer not found");
		}
		
		fileManager.saveDataToFile();
	}

	public void editPhone(CustomerImpl customer, String phone) {

		if(!isCustomerValid(customer.getName(), customer.getAddress(), phone, customer.typeProperty().toString())){
			throw new IllegalArgumentException("Arguments are not right!");
		}
		try {
			customers.get(customers.indexOf(customer)).setTelephoneNumber(phone);
		} catch (Exception e) {
			throw new IllegalArgumentException("Customer not found");
		}
		fileManager.saveDataToFile();
	}
	
	public ObservableList<CustomerImpl> getCustomers() {

		return customers;
	}
	
	public void convert() throws IOException {

		fileManager.convertXML2PDF();

	}
	
}