/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * This interface represents a StandardBook object
 * @author Marcello_Feroce
 */
public interface StandardBook {

    /**
     * @return The book's title
     */
    public String getTitle();
    /**
     * @return The book's title
     */
    public StringProperty titleProperty();
    /**
     * 
     * @return the isbn of the book
     */
    public String getIsbn();
    /**
     * 
     * @return the isbn of the book
     */
    public StringProperty isbnProperty();
    /**
     * 
     * @return the year of the book
     */
    public int getYear();
    /**
     * 
     * @return the year of the book
     */
    public IntegerProperty yearProperty();
    /**
     * 
     * @return the pages of the book
     */
    public int getPages();
    /**
     * 
     * @return the pages of the book
     */
    public IntegerProperty pagesProperty();
    /**
     * 
     * @return the serie of the book
     */
    public String getSerie();
    /**
     * 
     * @return the serie of the book
     */
    public StringProperty serieProperty();
    /**
     * 
     * @return the genre of the book
     */
    public String getGenre();
    /**
     * 
     * @return the genre of the book
     */
    public StringProperty genreProperty();
    /**
     * 
     * @return the author of the book
     */
    public String getAuthor();
    /**
     * 
     * @return the author of the book
     */
    public StringProperty authorProperty();
    /**
     * 
     * @return the price of the book
     */
    public int getPrice();
    /**
     * 
     * @return the price of the book
     */
    public IntegerProperty priceProperty();

    // ====================================== \\
    /**
     * 
     * @param title is the new title of the book
     */
    public void setTitle(String title);
    /**
     * 
     * @param isbn is the new isbn of the book
     */
    public void setIsbn(String isbn);
    /**
     * 
     * @param year is the new year of the book
     */
    public void setYear(int year);
    /**
     * 
     * @param pages is the new numbero of pages of the book
     */
    public void setPages(int pages);
    /**
     * 
     * @param serie is the new serie of the book
     */
    public void setSerie(String serie);
    /**
     * 
     * @param genre is the new genre of the book
     */
    public void setGenre(String genre);
    /**
     * 
     * @param author is the new author of the book
     */
    public void setAuthor(String author);
    /**
     * 
     * @param price is the new price of the book
     */
    public void setPrice(int price);
}
