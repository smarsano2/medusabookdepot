/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.sun.xml.internal.bind.AnyTypeAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * This interface represents a transfer
 * @author Marcello_Feroce
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Transfer {
    /**
     * 
     * @return the CanSendTransferrer object that sends the transfer
     */
    public CanSendTransferrer getSender();
    /**
     * 
     * @return the Transferrer object that receives the transfer
     */
    public Transferrer getReceiver();
    /**
     * 
     * @return the StandardBook transfered
     */
    public StandardBookImpl getBook();
    /**
     * 
     * @return the leaving date of the transfer
     */
    public java.util.Date getLeavingDate();
    /**
     * 
     * @param sender is the new sender of the transfer
     */
    public void setSender(CanSendTransferrer sender);
    /**
     * 
     * @param receiver is the new receiver of the transfer
     */
    public void setReceiver(Transferrer receiver);
    /**
     * 
     * @param book is the new map of StandardBook, Integer i want to set as whole of books to be transferred
     */
    public void setBook(StandardBookImpl book);
    /**
     * 
     * @param leavingDate is the new leaving date of the transfer
     */
    public void setLeavingDate(Date leavingDate);
    /**
     * 
     * @return the tracking number of the transfer
     */
    public String getTrackingNumber();
    /**
     * 
     * @return the quantity of books brought by the transfer
     */
    public int getQuantity();
    /**
     * 
     * @return the quantity of books brought by the transfer
     */
    public IntegerProperty quantityProperty();
    /**
     * 
     * @param quantity is the new quantity of books of the transfer
     */
    public void setQuantity(int quantity);
    /**
     * 
     * @return the total price of the books carried in the transfer
     */
    public int getTotalPrice();
    /**
     * 
     * @return the total price of the books carried in the transfer
     */
    public IntegerProperty totalPriceProperty();
    /**
     * 
     * @param trackingnumber is the new tracking number of the transfer
     */
    public void setTrackingNumber(String trackingnumber);
    /**
     * 
     * @return the tracking number
     */
    public StringProperty trackingNumberProperty();
	/**
	 * 
	 * @return type of the transfer
	 */
	public String getType();
	/**
	 * 
	 * @return type of the transfer
	 */
	public StringProperty typeProperty();
	/**
	 * 
	 * @param type is the new type of the transfer
	 */
	public void setType(String type);


}
