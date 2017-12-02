/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

import javax.xml.bind.annotation.XmlSeeAlso;

import com.medusabookdepot.model.modelImpl.CustomerImpl;
import javafx.beans.property.StringProperty;

/**
 * This interface represents a Transferrer that is not a Depot, so does simple operations
 * @author Marcello_Feroce
 *
 */
@XmlSeeAlso({CustomerImpl.class})
public interface Customer extends Transferrer {
    /**
     * 
     * @return the address of the Customer
     */
    public String getAddress();
    /**
     * 
     * @return the address of the Customer
     */
    public StringProperty addressProperty();
    /**
     * 
     * @return the telephone number of the Customer
     */
    public String getTelephoneNumber();
    /**
     * 
     * @return the telephone number of the Customer
     */
    public StringProperty telephoneNumberProperty();
    /**
     * @param address is the new address of the Customer
     *        this method set the new address as current address
     */
    public void setAddress(String address);

    /**
     * @param telephonenumber is the new telephone number of the Customer
     *        this method set the new telephone number as current address
     */
    public void setTelephoneNumber(String telephonenumber);
    /**
     * 
     * @return true if the Customer object is a printer, else false
     */
    public boolean isAPrinter();
    /**
     * 
     * @return true if the Customer object is a person, else false
     */
    public boolean isAPerson();
    /**
     * 
     * @return true if the Customer object is a library, else false
     */
    public boolean isALibrary();
    /**
     * 
     * @return the type of Customer, e.g. "Library"
     */
    public StringProperty typeProperty();
    

}
