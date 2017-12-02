/**
 * 'addMovement.fxml' Control Class
 */

package com.medusabookdepot.view;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import com.medusabookdepot.controller.MovementsController;
import com.medusabookdepot.model.modelImpl.TransferImpl;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;
import com.medusabookdepot.view.util.AutoCompleteComboBoxListener;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class AddMovementControl extends ScreenControl{
	
	// Reference to the controller
	private final MovementsController movementsController = MovementsController.getInstanceOf();
	
	// Aler panel to manage exceptions
    private final AlertTypes alert = new AlertTypesImpl();

	@FXML
    private TableView<TransferImpl> movementsTable;
	
	@FXML
    private TableColumn<TransferImpl, String> quantityColumn, isbnColumn, titleColumn, 
    	senderColumn, receiverColumn, dateColumn, trackingColumn, totalPriceColumn, typeColumn;
    
    @FXML
    private ComboBox<String> senderBox, receiverBox, isbnBox, titleBox;;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField trackingField, quantityField;
    
    @SuppressWarnings("unused")
	private AutoCompleteComboBoxListener<String> autoCompleteFactory;

    @FXML
    private HBox hBoxFields;
	
	public AddMovementControl(){
		super();
	}
    
	/**
     * Called after the fxml file has been loaded; this method initializes 
     * the fxml control class. 
     */
    @FXML
    private void initialize() {

    	// Initialize the table
		quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asString());
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().isbnProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().titleProperty());
        senderColumn.setCellValueFactory(cellData -> cellData.getValue().getSender().nameProperty());
        receiverColumn.setCellValueFactory(cellData -> cellData.getValue().getReceiver().nameProperty());
        dateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLeavingDate().toString()));
        trackingColumn.setCellValueFactory(cellData -> cellData.getValue().trackingNumberProperty());
        totalPriceColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(movementsController.convertPriceToString
        		(cellData.getValue().getTotalPrice())));
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        
        movementsTable.setItems(movementsController.getTempData());
        
        titleBox.setItems(FXCollections.observableArrayList(movementsController.getTitlesString()));
        autoCompleteFactory = new AutoCompleteComboBoxListener<String>(titleBox);
        senderBox.setItems(FXCollections.observableArrayList(movementsController.getCanSendTransferrersString()));
        autoCompleteFactory = new AutoCompleteComboBoxListener<String>(senderBox);
        
        // Listen for selection changes and enable delete button or filter the comboBoxes
        this.update();
    }
    
    /**
     * Called when the user press the 'add' button;
     * Method to add a new movement to the controller ObservableList
     * Method to add a new movement to a temporary ObservableList shown in the TableView
     */
    @FXML
    private void add(){

    	try {
    		this.checkIfEmpty();
    		Instant instant = Instant.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()));
    		Date date = Date.from(instant);
    		movementsController.addMovement(senderBox.getValue(), receiverBox.getValue(), date, isbnBox.getValue(), quantityField.getText(), trackingField.getText());
    		this.clear();
         } catch (Exception e) {
             alert.showWarning(e);
         }
    }

    /**
	 * It listen for selection changes to disable/enable the delete button 
	 * when the user selects something in the table
	 * This method filters the comboBox according to the 'sender' choice
	 */
	private void update(){
		//TODO Refactor code
		
        // Listen for selection changes of titleBox and enable and filter isbnBox 
        isbnBox.setDisable(true);
        titleBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	// Enable button if the title ComboBox has a newValue
        	isbnBox.setDisable(false);
        	// Set the items of the isbn ComboBox from a list of all the values possible from the selected title
        	isbnBox.setItems(FXCollections.observableArrayList(movementsController.getAllIsbnFromTitle(newValue)));
        	// If there is only one isbn possible value, select it
        	if(movementsController.getAllIsbnFromTitle(newValue).size()==1) {
        		isbnBox.getSelectionModel().select(0);
	        }
        	// If the list of all possible values is empty, or the title ComboBox is still empty, disable the isbn ComboBox 
            if(titleBox.getSelectionModel().isEmpty() || movementsController.getAllIsbnFromTitle(newValue).isEmpty()) {
            	isbnBox.setDisable(true);     	
            }
        });
        
        // Listen for selection changes of senderBox and enable and filter receiverBox 
        receiverBox.setDisable(true);
        senderBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	// Enable button if the title ComboBox has a newValue
        	receiverBox.setDisable(false);
        	// Set the items of the isbn ComboBox from a list of all the values possible from the selected title
        	receiverBox.setItems(FXCollections.observableArrayList(movementsController.getReceiversFromSender(newValue)));
        	autoCompleteFactory = new AutoCompleteComboBoxListener<String>(receiverBox);
        	// If there is only one receiver possible value, select it
        	if(movementsController.getAllIsbnFromTitle(newValue).size()==1) {
        		receiverBox.getSelectionModel().select(0);
	        }
        	// If the list of all possible values is empty, or the sender ComboBox is still empty, disable the receiver ComboBox 
            if(senderBox.getSelectionModel().isEmpty() || movementsController.getReceiversFromSender(newValue).isEmpty()) {
            	receiverBox.setDisable(true);     	
            }
        });
  
        // Listen for selection changes of senderBox and enable and filter titleBox 
        titleBox.setDisable(true);
        senderBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	// Enable button if the title ComboBox has a newValue
        	titleBox.setDisable(false);
        	// Set the items of the isbn ComboBox from a list of all the values possible from the selected title
        	try{
        		titleBox.setItems(FXCollections.observableArrayList(movementsController.getTitleFromTransferrer(newValue)));
	        	// If there is only one receiver possible value, select it
	        	if(movementsController.getTitleFromTransferrer(newValue).size()==1) {
	        		receiverBox.getSelectionModel().select(0);
		        }
	        	// If the list of all possible values is empty, or the sender ComboBox is still empty, disable the receiver ComboBox 
	            if(senderBox.getSelectionModel().isEmpty() || movementsController.getTitleFromTransferrer(newValue).isEmpty()) {
	            	titleBox.setDisable(true);     	
	            }
        	} catch (Exception e){
            	alert.showError(e);
            }
        });
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	/**
	 * Method to clear only the Quantity, Title and ISBN fields;
	 * Overrides clear method from ScreenControl because when the user is adding movements, 
	 * most of time he's keeping the same sender, receiver and tracking number but just changing 
	 * book and quantity 
	 */
	public void clear(){
    	for(Node node: hBoxFields.getChildren()){
    		if (node instanceof TextField) {
    	        ((TextField)node).clear();
    	    }
    		if (node instanceof ComboBox) {
    	        ((ComboBox)node).getSelectionModel().clearSelection();
    	    }
    	}
    }
	
	/**
	 * This method checks if textBoxes or choiceBoxes are empty 
	 * before calling controller methods
	 */
	@SuppressWarnings("rawtypes")
	public void checkIfEmpty(){

		if(dateField.getValue()==null) throw new IllegalArgumentException("One or more field are empty");
		
    	for(Node node: hBoxFields.getChildren()){
    		if (node instanceof TextField) {
    	        if(((TextField)node).getText().isEmpty()) throw new NullPointerException("One or more field are empty");
    	    }
    		if (node instanceof ChoiceBox) {
    			if(((ChoiceBox)node).getSelectionModel().isEmpty()) throw new NullPointerException("One or more field are empty");
    	    }
    		if (node instanceof ComboBox) {
    			if(((ComboBox)node).getSelectionModel().isEmpty()) throw new NullPointerException("One or more field are empty");
    	    }
    	}
    }
}