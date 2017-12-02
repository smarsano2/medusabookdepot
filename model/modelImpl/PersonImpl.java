/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.medusabookdepot.model.modelInterface.Customer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
@XmlRootElement(name="person")
public class PersonImpl extends CustomerImpl implements Customer, Serializable {// strategy

    /**
     * 
     */
    private static final long serialVersionUID = -1881387138018701672L;
    public PersonImpl() {
        super(null, null, null);
    }
    public PersonImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
    }
    @Override
    public String toString() {
        return "Persona: " + this.name.get() + "\n" + this.address.get() + "\n" + this.telephoneNumber.get() + "\n";

    }
    /**
     * always return false
     */
    @Override
    public boolean isAPrinter() {
        return false;
    }
    /**
     * always return true
     */
    @Override
    public boolean isAPerson() {
        return true;
    }
    /**
     * always return false
     */
    @Override
    public boolean isALibrary() {
        return false;
    }
    @Override
    public StringProperty typeProperty() {
        return new SimpleStringProperty("Person");
    }
    

}
