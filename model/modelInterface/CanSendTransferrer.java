package com.medusabookdepot.model.modelInterface;

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.sun.xml.internal.bind.AnyTypeAdapter;
/**
 * This interface represents a Transferrer that can send
 * @author Marcello_Feroce
 * 
 *
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface CanSendTransferrer extends Transferrer {

    /**
     * 
     * @param books is the map of StandardBook, Integer i want to verify if it is contained in CanSendTransferrer
     * @return true if books is contained, else false
     */
    public boolean containsBooks(Map<StandardBookImpl, Integer> books);
}
