/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.medusabookdepot.model.modelInterface.StandardBook;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 */
@XmlRootElement(name="book")
public class StandardBookImpl implements StandardBook, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7759011458602273856L;
    private final StringProperty isbn;
    private final StringProperty title;
    private final IntegerProperty year;
    private final IntegerProperty pages;
    private final StringProperty serie;
    private final StringProperty genre;
    private final StringProperty author;
    private final IntegerProperty price;

    // Default Constructor
    public StandardBookImpl() {
        this(null, null, 0, 0, null, null, null, 0);
    }
    
    /**
     * 
     * @param isbn is the isbn of the book
     * @param title is the title of the book
     * @param year is the year of the book
     * @param pages is the number of pages of the book
     * @param serie is the serie of the book
     * @param genre is the genre of the book
     * @param author is the author of the book
     * @param price is the price of the book
     */
    public StandardBookImpl(String isbn, String title, int year, int pages, String serie, String genre, String author,
            int price) {
        this.isbn = new SimpleStringProperty(isbn);
        this.title = new SimpleStringProperty(title);
        this.year = new SimpleIntegerProperty(year);
        this.pages = new SimpleIntegerProperty(pages);
        this.serie = new SimpleStringProperty(serie);
        this.genre = new SimpleStringProperty(genre);
        this.author = new SimpleStringProperty(author);
        this.price = new SimpleIntegerProperty(price);
    }
    @Override
    public String getIsbn() {
        return isbn.get();
    }
    @Override
    public StringProperty isbnProperty() {
        return isbn;
    }
    @Override
    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }
    @Override
    public String getTitle() {
        return title.get();
    }
    @Override
    public StringProperty titleProperty() {
        return title;
    }
    @Override
    public void setTitle(String title) {
        this.title.set(title);
    }
    @Override
    public int getYear() {
        return year.get();
    }
    @Override
    public IntegerProperty yearProperty() {
        return year;
    }
    @Override
    public void setYear(int year) {
        this.year.set(year);
    }
    @Override
    public int getPages() {
        return pages.get();
    }
    @Override
    public IntegerProperty pagesProperty() {
        return pages;
    }
    @Override
    public void setPages(int pages) {
        this.pages.set(pages);
    }
    @Override
    public String getSerie() {
        return serie.get();
    }
    @Override
    public StringProperty serieProperty() {
        return serie;
    }
    @Override
    public void setSerie(String serie) {
        this.serie.set(serie);
    }
    @Override
    public String getGenre() {
        return genre.get();
    }
    @Override
    public StringProperty genreProperty() {
        return genre;
    }
    @Override
    public void setGenre(String genre) {
        this.genre.set(genre);
    }
    @Override
    public String getAuthor() {
        return author.get();
    }
    @Override
    public StringProperty authorProperty() {
        return author;
    }
    @Override
    public void setAuthor(String author) {
        this.author.set(author);
    }
    @Override
    public int getPrice() {
        return price.get();
    }
    @Override
    public IntegerProperty priceProperty() {
        return price;
    }
    @Override
    public void setPrice(int price) {
        this.price.set(price);
    }
    @Override
    public String toString() {
        return "libro: " + this.title.get() + ", isbn " + this.isbn.get() + ", pagine " + this.pages.get() + ", prezzo "
                + this.price.get() + ", anno " + this.year.get() + ", autore " + this.author.get() + ", genere "
                + this.genre.get() + ", collana " + this.serie.get() + "\n";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn.get() == null) ? 0 : isbn.get().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandardBookImpl other = (StandardBookImpl) obj;
		if (isbn.get() == null) {
			if (other.isbn.get() != null)
				return false;
		} else if (!isbn.get().equals(other.isbn.get()))
			return false;
		return true;
	}
    
    
}
