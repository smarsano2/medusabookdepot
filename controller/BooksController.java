package com.medusabookdepot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.view.observer.BooksViewObserver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BooksController extends ConvertController implements BooksViewObserver {

	private final static String NAME = "books"; // Name of the file, for the FileManager class
	private static final int ISBN_LENGTH = 13;
	private final ObservableList<StandardBookImpl> books = FXCollections.observableArrayList();
	private static BooksController singBook;
	
	private FileManager<StandardBookImpl> fileManager = new FileManager<>(books, StandardBookImpl.class, NAME);

	private BooksController() {
		super();
	}

	public static BooksController getInstanceOf() {

		return (BooksController.singBook == null ? new BooksController() : BooksController.singBook);
	}

	public void addBook(String isbn, String name, String year, String pages, String serie, String genre, String author,
			String price) throws IllegalArgumentException, IndexOutOfBoundsException {

		if (this.isInputValid(isbn, year, pages, serie, genre, author)) {

			books.add(new StandardBookImpl(isbn, name, Integer.parseInt(year), Integer.parseInt(pages), serie, genre,
					author, this.convertPrice(price)));
			fileManager.saveDataToFile();
		}
	}

	public StandardBookImpl searchBook(StandardBook book) {

		for (StandardBookImpl b : books) {
			if (book.equals(b)) {

				return b;
			}
		}
		return null;
	}

	public List<StandardBookImpl> searchBook(String value) {
		List<StandardBookImpl> result = new ArrayList<>();

		this.books.stream().forEach(e -> {

			if (e.getIsbn().contains(value) || e.getTitle().toLowerCase().contains(value.toLowerCase())
					|| Integer.toString(e.getYear()).contains(value) || Integer.toString(e.getPages()).contains(value)
					|| e.getSerie().toLowerCase().contains(value.toLowerCase())
					|| e.getGenre().toLowerCase().contains(value.toLowerCase())
					|| e.getAuthor().toLowerCase().contains(value.toLowerCase())
					|| Integer.toString(e.getPrice()).contains(value)) {
				result.add(e);
			}
		});
		return result;
	}

	public Stream<StandardBookImpl> searchBook(Optional<Depot> depot, Optional<String> isbn, Optional<String> name,
			Optional<String> year, Optional<String> pages, Optional<String> serie, Optional<String> genre,
			Optional<String> author) {

		Stream<StandardBookImpl> result = this.books.stream();

		if (isbn.isPresent()) {
			result = result.filter(e -> e.getIsbn().contains(isbn.get()));
		}
		if (name.isPresent()) {
			result = result.filter(e -> e.getTitle().toLowerCase().contains(name.get().toLowerCase()));
		}
		if (year.isPresent()) {
			result = result.filter(e -> Integer.toString(e.getYear()).contains(year.get()));
		}
		if (pages.isPresent()) {
			result = result.filter(e -> Integer.toString(e.getPages()).contains(pages.get()));
		}
		if (serie.isPresent()) {
			result = result.filter(e -> e.getSerie().toLowerCase().contains(serie.get().toLowerCase()));
		}
		if (genre.isPresent()) {
			result = result.filter(e -> e.getGenre().toLowerCase().contains(genre.get().toLowerCase()));
		}
		if (author.isPresent()) {
			result = result.filter(e -> e.getAuthor().toLowerCase().contains(author.get().toLowerCase()));
		}
		if (depot.isPresent()) {

			result = result.filter(e -> depot.filter(f -> f.getQuantityFromStandardBook(e) < 1) != null);
		}

		return result;
	}

	public void removeBook(StandardBook book) throws NoSuchElementException {

		try {
			books.remove(book);
		} catch (Exception e) {
			throw new NoSuchElementException("No such element in list!");
		}
		fileManager.saveDataToFile();
	}

	public void removeBook(String isbn) {

		this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).forEach(e -> {
					this.removeBook(e);
				});
	}

	public boolean isInputValid(String isbn, String year, String pages, String serie, String genre, String author)
			throws IllegalArgumentException {

		if (isbn.equals("") || year.equals("") || pages.equals("") || serie.equals("") || genre.equals("")
				|| author.equals("")) {

			throw new IllegalArgumentException("The fields mustn't be empty!");
		}
		
		try {
			Integer.parseInt(year);
			Integer.parseInt(pages);
		} catch (Exception e) {
			throw new IllegalArgumentException("Year and pages must be integers!");
		}
		if (this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).count() >= 1) {
			throw new IllegalArgumentException(isbn + " is already present!");
		}
		if (isbn.length() != ISBN_LENGTH) {
			throw new IllegalArgumentException(isbn + " should be " + ISBN_LENGTH + " character!");
		}
		if (year.length() != 4
				|| Integer.parseInt(year) > java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)) {
			throw new IllegalArgumentException(
					"Wait a minute, Doc. Are you telling me that you built a time machine... out of Delorian?!");
		}

		return true;
	}

	public void convert() throws IOException {

		fileManager.convertXML2PDF();

	}

	public void editISBN(StandardBook book, String isbn) {

		if (!this.isInputValid(isbn, Integer.toString(book.getYear()), Integer.toString(book.getPages()),
				book.getSerie(), book.getGenre(), book.getAuthor())) {
			throw new IllegalArgumentException("ISBN is not valid!");
		}
		books.get(books.indexOf(book)).setIsbn(isbn);
		fileManager.saveDataToFile();
	}

	public void editTitle(StandardBook book, String title) {

		if (title.equals("")) {
			throw new IllegalArgumentException("The argument must be not empty!");
		}
		
		try {
			books.get(books.indexOf(book)).setTitle(title);
		} catch (Exception e) {
			throw new IllegalArgumentException("Book not found!");
		}
		
		fileManager.saveDataToFile();
	}

	public void editYear(StandardBook book, String year) {
		
		try {
			books.get(books.indexOf(book)).setYear(Integer.parseInt(year));
		} catch (NumberFormatException nf) {
			throw new IllegalArgumentException("Year must be an integer!");
		} catch ( Exception e){
			throw new IllegalArgumentException("Book not found!");
		}
		
		fileManager.saveDataToFile();
	}

	public void editPages(StandardBook book, String pages) {

		try {
			books.get(books.indexOf(book)).setPages(Integer.parseInt(pages));
		} catch (NumberFormatException nf) {
			throw new IllegalArgumentException("Pages number must be an integer!");
		} catch ( Exception e){
			throw new IllegalArgumentException("Book not found!");
		}
		
		fileManager.saveDataToFile();
	}

	public void editSerie(StandardBook book, String serie) {

		if (serie.equals("")) {
			throw new IllegalArgumentException("The argument must be not empty!");
		}
		try {
			books.get(books.indexOf(book)).setSerie(serie);
		} catch (Exception e) {
			throw new IllegalArgumentException("Book not found!");
		}
		
		fileManager.saveDataToFile();
	}

	public void editGenre(StandardBook book, String genre) {

		if (genre.equals("")) {
			throw new IllegalArgumentException("The argument must be not empty!");
		}
		try {
			books.get(books.indexOf(book)).setGenre(genre);
		} catch (Exception e) {
			throw new IllegalArgumentException("Book not found!");
		}
		
		fileManager.saveDataToFile();
	}

	public void editAuthor(StandardBook book, String author) {

		if (author.equals("")) {
			throw new IllegalArgumentException("The argument must be not empty!");
		}
		try {
			books.get(books.indexOf(book)).setAuthor(author);
		} catch (Exception e) {
			throw new IllegalArgumentException("Book not found!");
		}
		
		fileManager.saveDataToFile();
	}

	public void editPrice(StandardBook book, String price) {

		try {
			books.get(books.indexOf(book)).setPrice(this.convertPrice(price));
		} catch (Exception e) {
			throw new IllegalArgumentException("Book not found!");
		}
		
		fileManager.saveDataToFile();
	}

	public ObservableList<StandardBookImpl> getBooks() {

		return books;
	}
}
