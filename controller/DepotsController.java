package com.medusabookdepot.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.view.observer.DepotsViewObserver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DepotsController  implements DepotsViewObserver{

	private final ObservableList<DepotImpl> depots = FXCollections.observableArrayList();
	private static DepotsController singDepots;

	// Fields for file load and save, and for converting to PDF
	private final static String NAME = "depots";
	private FileManager<DepotImpl> fileManager = new FileManager<>(depots, DepotImpl.class, NAME);

	private DepotsController() {
		super();
	}

	public static DepotsController getInstanceOf() {

		return (DepotsController.singDepots == null ? new DepotsController() : DepotsController.singDepots);
	}

	public void addDepot(DepotImpl... depot) {

		for (DepotImpl n : depot) {

			depots.add(n);
		}
	}

	public void addDepot(String name) throws IllegalArgumentException {

		if (name.equals("")) {
			throw new IllegalArgumentException("The name must be not empty!");
		}
		if (this.searchDepot(name).size() >= 1) {
			throw new IllegalArgumentException("Depot " + name + " is already present!");
		}
		depots.add(new DepotImpl(name, new HashMap<>()));
		fileManager.saveDataToFile();
	}

	public Stream<DepotImpl>filterDepot(String name){
		
		return this.getDepots().stream().filter(e->e.getName().toLowerCase().contains(name.toLowerCase()));
	}
	
	public ObservableList<DepotImpl> searchDepot(String name) {
		ObservableList<DepotImpl> result = FXCollections.observableArrayList();

		this.getDepots().stream().forEach(e -> {
			if (e.getName().toLowerCase().contains(name.toLowerCase())) {
				result.add(e);
			}
		});
		return result;
	}

	public ObservableList<Entry<StandardBookImpl, Integer>> searchDepot(
			ObservableList<Entry<StandardBookImpl, Integer>> set, String value) {
		ObservableList<Entry<StandardBookImpl, Integer>> result = FXCollections.observableArrayList();

		set.stream().forEach(e -> {
			StandardBookImpl book = e.getKey();
			if (book.getTitle().toLowerCase().contains(value.toLowerCase())
					|| book.getIsbn().toLowerCase().contains(value.toLowerCase())
					|| Integer.toString(book.getYear()).contains(value)
					|| book.getAuthor().toLowerCase().contains(value.toLowerCase())
					|| book.getGenre().toLowerCase().contains(value.toLowerCase())
					|| book.getSerie().toLowerCase().contains(value.toLowerCase())
					|| Integer.toString(book.getPrice()).contains(value)
					|| Integer.toString(book.getPages()).contains(value)
					|| Integer.toString(e.getValue()).contains(value)) {
				result.add(e);
			}
		});

		return result;
	}

	public void removeDepot(DepotImpl depot) throws NoSuchElementException {

		try {
			depots.remove(depot);
		} catch (Exception e) {
			throw new NoSuchElementException("No such element in list!");
		}
		fileManager.saveDataToFile();
	}

	public void editName(DepotImpl depot, String name) {

		if (this.searchDepot(name).size() >= 1) {
			throw new IllegalArgumentException("Depot " + name + " is already present!");
		}
		try {
			depots.get(depots.indexOf(depot)).setName(name);
		} catch (Exception e) {
			throw new IllegalArgumentException("Depot not found");
		}

		fileManager.saveDataToFile();
	}

	public void editBookQuantity(DepotImpl depot, StandardBookImpl book, String value) {

		try {
			Integer.parseInt(value);
		} catch (Exception e) {
			throw new IllegalArgumentException("Value must be an integer!");
		}
		if (Integer.parseInt(value) < 0) {
			throw new IllegalArgumentException("Not enough books in depot");
		}
		if (Integer.parseInt(value) == 0) {

			Map<StandardBookImpl, Integer> booksToRemove = new HashMap<>();
			booksToRemove.put(book, depot.getQuantityFromStandardBook(book));
			depot.removeBooks(booksToRemove);
		} else {
			depot.setQuantityFromBook(book, Integer.parseInt(value));
		}

		fileManager.saveDataToFile();
	}

	public ObservableList<DepotImpl> getDepots() {

		return depots;
	}

	public void convert() throws IOException {

		fileManager.convertXML2PDF();

	}
}
