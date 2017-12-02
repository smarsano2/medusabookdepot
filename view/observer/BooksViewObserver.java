package com.medusabookdepot.view.observer;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;

import javafx.collections.ObservableList;

public interface BooksViewObserver {

	/**
	 * Add a new book in the list
	 * 
	 * @param ISBN
	 * @param Name
	 * @param Year
	 * @param Pages
	 * @param Serie
	 * @param Genre
	 * @param Author
	 * @param <b>Price</b>
	 *            in string format
	 * @throws IllegalArgumentException
	 *             if ISBN already exists
	 */
	public void addBook(String isbn, String name, String year, String pages, String serie, String genre, String author,
			String price) throws IllegalArgumentException, IndexOutOfBoundsException;

	/**
	 * Search a book in the list
	 * 
	 * @param Book
	 * @return StandardBook if it found the book, else <b>null</b>
	 */
	public StandardBookImpl searchBook(StandardBook book);

	/**
	 * Search a string in ALL fields of book object and add it to results if it
	 * is contained in field
	 * 
	 * @param String
	 *            to search
	 * @return <b>List</b> of all books found
	 */
	public List<StandardBookImpl> searchBook(String value);

	/**
	 * Multifilter for books search. It search in the books list if you don't
	 * pass a depot, or in a specific depot if you pass it
	 * 
	 * @param <b>Depot</b>
	 *            if you want to search in a specific depot
	 * @param ISBN
	 * @param Name
	 * @param Year
	 * @param Pages
	 * @param Serie
	 * @param Genre
	 * @param Author
	 * @return <b>Stream<of StandardBooks></b> found by filtering the books list
	 */
	public Stream<StandardBookImpl> searchBook(Optional<Depot> depot, Optional<String> isbn, Optional<String> name,
			Optional<String> year, Optional<String> pages, Optional<String> serie, Optional<String> genre,
			Optional<String> author);

	/**
	 * Remove a book from the list
	 * 
	 * @param Book
	 * @throws NoSuchElementException
	 *             if element is not present in books list
	 */
	public void removeBook(StandardBook book) throws NoSuchElementException;

	/**
	 * Search isbn and remove relative book
	 * 
	 * @param isbn
	 *            of book to remove
	 */
	public void removeBook(String isbn);

	/**
	 * Check if the input for a new book is correct
	 * 
	 * @param ISBN
	 * @param Year
	 * @param Serie
	 * @param Genre
	 * @param Author
	 * @return <b>True</b> if input is valid, else a exception
	 * @throws IllegalArgumentException
	 *             if the arguments are not valid
	 */
	public boolean isInputValid(String isbn, String year, String pages, String serie, String genre, String author)
			throws IllegalArgumentException;

	/**
	 * Convert the XML file to PDF
	 * 
	 * @throws IOException
	 */
	public void convert() throws IOException;

	/**
	 * Edit isbn number
	 * 
	 * @param Book
	 * @param isbn
	 * @throws IllegalArgumentException
	 */
	public void editISBN(StandardBook book, String isbn);

	/**
	 * Edit book name
	 * 
	 * @param Book
	 * @param Name
	 * @throws IllegalArgumentException
	 *             if the argument passed is empty
	 */
	public void editTitle(StandardBook book, String title);

	/**
	 * Edit book release year
	 * 
	 * @param Book
	 * @param Year
	 * @throws IllegalArgumentException
	 */
	public void editYear(StandardBook book, String year);

	/**
	 * Edit book pages number
	 * 
	 * @param Book
	 * @param Pages
	 * @throws IllegalArgumentException
	 */
	public void editPages(StandardBook book, String pages);

	/**
	 * Edit book serie
	 * 
	 * @param Book
	 * @param Serie
	 * @throws IllegalArgumentException
	 *             if the argument passed is empty
	 */
	public void editSerie(StandardBook book, String serie);

	/**
	 * Edit book genre
	 * 
	 * @param Book
	 * @param Genre
	 * @throws IllegalArgumentException
	 *             if the argument passed is empty
	 */
	public void editGenre(StandardBook book, String genre);

	/**
	 * Edit book author
	 * 
	 * @param Book
	 * @param Author
	 * @throws IllegalArgumentException
	 *             if the argument passed is empty
	 */
	public void editAuthor(StandardBook book, String author);

	/**
	 * Edit book price
	 * 
	 * @param Book
	 * @param Price
	 */
	public void editPrice(StandardBook book, String price);

	/**
	 * @return The list of saved books
	 */
	public ObservableList<StandardBookImpl> getBooks();
}
