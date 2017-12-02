/**
 * Switch Screen Controller Class, inherited by all fxml files
 */

package com.medusabookdepot.view;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;

/**
 */
public class ScreenControl extends GUI{
	
	@FXML
	private Button movements, addMovement, depots, books, customers, addCustomer, addBook, addDepot, statistics, email, credits;
	
	@FXML
	private VBox vBoxFields;
	
	/**
	 * Method to switch screen. It loads the xml files according to the id
	 * of the button that the user has pressed
	 */
	@FXML
	public void switchScreen(ActionEvent event) {
		try {
			
			URL paneMovementsUrl = getClass().getResource(((Control)event.getSource()).getId()+".fxml"); //*((Control)event.getSource()).getId()* get button name
			ScrollPane paneMovements = FXMLLoader.load( paneMovementsUrl );

			this.getMainPane().setCenter(paneMovements);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to clear all the textFields after add button has been pressed
	 */
    @SuppressWarnings("rawtypes")
	public void clear(){
    	for(Node node: vBoxFields.getChildren()){
    		if (node instanceof TextField) {
    	        ((TextField)node).clear();
    	    }
    		if (node instanceof ChoiceBox) {
    	        ((ChoiceBox)node).getSelectionModel().clearSelection();
    	    }
    		if (node instanceof ComboBox) {
    	        ((ComboBox)node).getSelectionModel().clearSelection();
    	    }
    	}
    }
    
    @FXML
    public void openPopup(ActionEvent event){
    	
    	final Popup popup = new Popup();
		popup.setAutoHide(true);
		popup.setAutoFix(true);
		
		// Calculate popup placement coordinates.
		Node eventSource = (Node) event.getSource();
		Bounds sourceNodeBounds = eventSource.localToScreen(eventSource.getBoundsInLocal());
		popup.setX(sourceNodeBounds.getMinX() - 150.0);
		popup.setY(sourceNodeBounds.getMaxY() + 5.0);
		
		Label credits = new Label ("Authors:\n\nCecchetti Giulia\n\nColombo Andrea\n\nCroccolino Lorenzo\n\nFeroce Marcello");
		credits.setTextAlignment(TextAlignment.CENTER);
		credits.setPadding(new Insets(32, 38, 32, 38));
		
		popup.getContent().add(credits);
		popup.show(this.getMainPane().getScene().getWindow());
    }
}