/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.Depot;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Marcello_Feroce
 *
 */
@XmlRootElement(name="depot")
public class DepotImpl extends TransferrerImpl implements Depot, CanSendTransferrer, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 47429766209693618L;
    
    @XmlElement
    private Map<StandardBookImpl, Integer> books;
    
    // Default Constructor
    public DepotImpl() {
		this(null,null);
	}
    
    public DepotImpl(String name, Map<StandardBookImpl, Integer> books) {
        super(name);
        if (books == null) {
            this.books = new HashMap<StandardBookImpl, Integer>();
        } else {
            this.books = new HashMap<>(books);// mi creo un altro oggeeto così
                                              // non mantengo il riferimento
                                              // alla mappa di input
        }
    }
    @Override
    public int getQuantity() {
        int x = 0;
        for (StandardBookImpl libro : this.books.keySet()) {
            x += books.get(libro);
        }
        return x;
    }
    @Override
    public int getQuantityFromStandardBook(StandardBookImpl book) {

        int x = 0;
        for (StandardBookImpl libro : this.books.keySet()) {
            if (libro.getIsbn().equals(book.getIsbn())) {
                x += books.get(libro);
            }
        }
        return x;
    }

    @Override
    public int getQuantityFromTitle(String title) {

        int x = 0;
        for (StandardBookImpl libro : this.books.keySet()) {
            if (libro.getTitle().equals(title)) {
                x += books.get(libro);
            }
        }
        return x;
    }

    @Override
    public int getQuantityFromAuthor(String author) {

        int x = 0;
        for (StandardBookImpl libro : this.books.keySet()) {
            if (libro.getAuthor().equals(author)) {
                x += books.get(libro);
            }
        }
        return x;
    }
    @Override
    public int getQuantityFromYear(int year) {

        int x = 0;
        for (StandardBookImpl libro : this.books.keySet()) {
            if (libro.getYear() == year) {
                x += books.get(libro);
            }
        }
        return x;
    }
    public String toString() {
        return "Deposito " + this.name.get() + this.books + "\n";
    }

    @Override
    public String getBooksAsACoolString() {
        String finale = new String("");
        for (Entry<StandardBookImpl, Integer> entry : this.books.entrySet()) {
            finale = finale.concat("libro: "+entry.getKey().getTitle()+"\n\t" + "in quantità " + entry.getValue() + "\n");
        }
        return finale;
    }

    @Override
    public IntegerProperty quantityProperty() {
        return new SimpleIntegerProperty(this.getQuantity());
    }

    @Override
    public IntegerProperty quantityFromStandardBookProperty(StandardBookImpl book) {
        return new SimpleIntegerProperty(this.getQuantityFromStandardBook(book));
    }

    @Override
    public IntegerProperty quantityFromTitleProperty(String title) {
        return new SimpleIntegerProperty(this.getQuantityFromTitle(title));
    }

    @Override
    public IntegerProperty quantityFromAuthorProperty(String author) {
        return new SimpleIntegerProperty(this.getQuantityFromAuthor(author));
    }

    @Override
    public IntegerProperty quantityFromYearProperty(int year) {
        return new SimpleIntegerProperty(this.getQuantityFromYear(year));
    }
    @Override
    public boolean containsBooks(Map<StandardBookImpl, Integer> books) {
        for (Entry<StandardBookImpl, Integer> entry : books.entrySet()) {
            if (!(this.books.containsKey(entry.getKey()) && this.books.get(entry.getKey()).equals(entry.getValue()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void removeBooks(Map<StandardBookImpl, Integer> books) {
        for (Entry<StandardBookImpl, Integer> entry : books.entrySet()) {
            if(this.books.get(entry.getKey()) - entry.getValue()<=0) {
                this.books.remove(entry.getKey());
            }
            else {
                this.books.put(entry.getKey(), this.books.get(entry.getKey()) - entry.getValue());
            }
        }
    }
    
    @Override
    public void addBooks(Map<StandardBookImpl, Integer> books) {
        for (Entry<StandardBookImpl, Integer> entry : books.entrySet()) {
            if (this.books.containsKey(entry.getKey())) {
                this.books.put(entry.getKey(), entry.getValue() + this.books.get(entry.getKey()));
            } else {
                this.books.put(entry.getKey(), entry.getValue());
            }
        }

    }

    @Override
    public boolean isADepot() {
        return true;
    }

    @Override
    public Map<StandardBookImpl, Integer> getBooksFromStandardBookIsbn(List<String> isbns) {
        if (isbns.isEmpty() || isbns == null) {
            return this.books;
        } else {
            Map<StandardBookImpl, Integer> libri = new HashMap<>();
            for (Entry<StandardBookImpl, Integer> entry : this.books.entrySet()) {
                if (isbns.contains(entry.getKey().getIsbn())) {
                    libri.put(entry.getKey(), entry.getValue());
                }
            }
            return libri;
        }
    }
    @Override
    public List<StandardBookImpl> getStandardBooksAsList() {
        List<StandardBookImpl> lis = new ArrayList<>();
        for (StandardBookImpl b : this.books.keySet()) {
            // copia difensiva
            lis.add(new StandardBookImpl(b.getTitle(), b.getIsbn(), b.getYear(), b.getPages(), b.getSerie(),
                    b.getGenre(), b.getAuthor(), b.getPrice()));
        }
        return lis;
    }
    @Override
    public List<String> getStandardBooksIsbns() {
        List<String> lis = new ArrayList<>();
        for (StandardBookImpl b : this.books.keySet()) {
            // copia difensiva
            lis.add(new String(b.getIsbn()));
        }
        return lis;
    }
    @Override
    public Map<StandardBookImpl, Integer> getBooks() {
        Map<StandardBookImpl, Integer> map = new HashMap<>();
        for (Entry<StandardBookImpl, Integer> ee : this.books.entrySet()) {
            map.put(ee.getKey(), ee.getValue());
        }
        return map;// copia difensiva
    }

    @Override
    public Map<StandardBookImpl, Integer> getBooksFromStandardBookIsbnAndQuantity(
            List<Pair<String, Integer>> isbnsAndQuantities) {
        if (isbnsAndQuantities.isEmpty() || isbnsAndQuantities == null) {
            return this.books;
        }
        List<String> ss = new ArrayList<>();
        for (Pair<String, Integer> pa : isbnsAndQuantities) {
            ss.add(pa.getFirst());
        }
        Map<StandardBookImpl, Integer> mappa = getBooksFromStandardBookIsbn(ss);
        List<Integer> ii = new ArrayList<>();
        for (Pair<String, Integer> pa : isbnsAndQuantities) {
            ii.add(pa.getSecond());
        }
        int x = 0;
        for (Entry<StandardBookImpl, Integer> en : mappa.entrySet()) {
            en.setValue(ii.get(x));
            x++;
        }
        return mappa;
    }

    @Override
    public List<Pair<String, Integer>> getBookIsbnsAsListOfPair() {
        List<Pair<String, Integer>> lis = new ArrayList<>();
        for (Entry<StandardBookImpl, Integer> en : this.books.entrySet()) {
            lis.add(new Pair<String, Integer>(en.getKey().getIsbn(), en.getValue()));
        }
        return lis;
    }

    @Override
    public void addBook(Pair<StandardBookImpl, Integer> pair) {
        if(!this.books.containsKey(pair.getFirst())) {
            this.books.put(pair.getFirst(), pair.getSecond());
        }
    }
    
    public void setQuantityFromBook(StandardBookImpl book, int quantity){

    	this.books.put(book, quantity);
    }
}
