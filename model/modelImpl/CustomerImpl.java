/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlSeeAlso;

import com.medusabookdepot.model.modelInterface.Customer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Abstract class that implements Customer interface, and extends the abstract
 * class TransferrerImpl, which implements Transferrer
 * 
 * @author Marcello_Feroce
 *
 */
@XmlSeeAlso({PersonImpl.class, LibraryImpl.class, PrinterImpl.class})
public abstract class CustomerImpl extends TransferrerImpl implements Customer, Serializable {// template
                                                                                              // method

    /**
     * 
     */
    private static final long serialVersionUID = -9063812037630746920L;
    protected StringProperty address;
    protected StringProperty telephoneNumber;
    public CustomerImpl(String name, String address, String telephoneNumber) {
        super(name);
        this.address = new SimpleStringProperty(address);// qui e subito sotto
                                                         // mi creo un altro
                                                         // oggetto, così da non
                                                         // mantenere il
                                                         // riferimento con gli
                                                         // oggetti in input
        this.telephoneNumber = new SimpleStringProperty(telephoneNumber);
    }
    /**
     * get the address of the customer, using a defensive copy
     */
    @Override
    public String getAddress() {
        String addr = new String(this.address.get());// copia difensiva
        return addr;
    }
    /**
     * get the telephone number of the customer, using a defensive copy
     */
    @Override
    public String getTelephoneNumber() {
        String tel = new String(this.telephoneNumber.get());// copia difensiva
        return tel;
    }
    /**
     * set the address of the customer, creating a new object, so doesn't keep the referement with the input
     */
    @Override
    public void setAddress(String address) {
        this.address.set(new String(address));
    }
    /**
     * set the telephone number of the customer, creating a new object, so doesn't keep the referement with the input
     */
    @Override
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber.set(new String(telephoneNumber));
    }
    /**
     * always returns false, because a Customer is not a Depot
     */
    @Override
    public boolean isADepot() {
        return false;
    }
    /**
     * abstract method, return true if the Customer object is istance of Printer
     */
    @Override
    public abstract boolean isAPrinter();
    /**
     * abstract method, return true if the Customer object is istance of Person
     */
    @Override
    public abstract boolean isAPerson();
    /**
     * abstract method, return true if the Customer object is istance of Library
     */
    @Override
    public abstract boolean isALibrary();
    /**
     * return the address as StringProperty
     */
    @Override
    public StringProperty addressProperty() {
        StringProperty addr = new SimpleStringProperty(this.address.get());// copia
                                                                           // difensiva
        return addr;
    }
    /**
     * return the telephone number as StringProperty
     */
    @Override
    public StringProperty telephoneNumberProperty() {
        StringProperty tel = new SimpleStringProperty(this.telephoneNumber.get());// copia
                                                                                  // difensiva
        return tel;
    }
    @Override
    abstract public String toString();
}
