/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.medusabookdepot.model.modelInterface.Transferrer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
@XmlRootElement(name = "transferrer")
@XmlSeeAlso({CustomerImpl.class})
public abstract class TransferrerImpl implements Transferrer, Serializable {// template
                                                                            // method

    /**
     * 
     */
    private static final long serialVersionUID = -7387131023077698466L;
    protected StringProperty name;
    public TransferrerImpl() {
        this(null);
    }
    public TransferrerImpl(String name) {
        this.name = new SimpleStringProperty(name);
    }
    @Override
    public String getName() {
        String name=new String(this.name.get());//copia difensiva
        return name;
    }
    @Override
    public void setName(String name) {
        this.name.set(name);
    }
    @Override
    public StringProperty nameProperty() {
        StringProperty name=new SimpleStringProperty(this.name.get());//copia difensiva
        return name;
    }
    abstract public String toString();
    @Override
    abstract public boolean isADepot();
}
