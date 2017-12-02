/**
 * 'email.fxml' Control Class
*/

package com.medusabookdepot.view;

import javax.mail.MessagingException;

import com.medusabookdepot.controller.files.EmailSender;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EmailControl extends ScreenControl{
	
		// Reference to the email sender
		private final EmailSender emailSender = new EmailSender();
		
		// Aler panel to manage exceptions
	    private final AlertTypes alert = new AlertTypesImpl();
	    
		@FXML
		private TextField toField;
		@FXML
		private TextField subjectField;
		@FXML
		private TextField attachedField;
		@FXML
		private TextArea messageArea;
		
		private ObservableList<String> paths = FXCollections.observableArrayList();
		
		@FXML
		private ListView<String> listView;

		public EmailControl(){
			super();
			paths = emailSender.setPaths();
		}
		
		/**
	     * Called after the fxml file has been loaded; this method initializes 
	     * the fxml control class. 
	     */
	    public void initialize() {
	    	attachedField.setEditable(false);
	    	listView.setItems(paths);
	    	if(paths.isEmpty()) alert.showWarning(new NullPointerException("There are no converted files in the folder."));
	    }
	    
	    /**
	     * Called when the user press the 'addAttachment' button;
	     * This method sets the attachment field
	     */
		@FXML
	    private void addAttachment() {
	        try {
	        	attachedField.setText(listView.getSelectionModel().getSelectedItem());
	        } catch (Exception e) {
	        	alert.showWarning(e);
	        }
	    }
		
		/**
	     * Called when the user press the send button
	     * This method sends the email to one or more receivers
	     */
		public void send() {			
			try {
				emailSender.send(toField.getText(), subjectField.getText(), messageArea.getText(), attachedField.getText());
				alert.showEmailSentSuccessfully();
			} catch (MessagingException e) {
				alert.showEmailNotSentError(e);
			} catch (IllegalArgumentException e){
				alert.showError(e);
			}
		}
}
