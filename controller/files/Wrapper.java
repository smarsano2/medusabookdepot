package com.medusabookdepot.controller.files;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;

/**
 * Helper class to wrap a list of elements. This is used for saving the list of elements to an XML file. 
 **/
public class Wrapper<A> {

    private List<A> items;

    public void setElements(List<A> items) {
        this.items = items;
    }
    
    @XmlAnyElement(lax=true)
    public List<A> getElements() {
        return items;
    }
}
