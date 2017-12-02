/**
 * 'customers.fxml' and 'addCustomer.fxml' Control Class
 */

package com.medusabookdepot.view;
import java.io.IOException;
import java.util.Optional;

import com.medusabookdepot.controller.CustomersController;
import com.medusabookdepot.model.modelImpl.CustomerImpl;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class CustomersControl extends ScreenControl{
	
	// Reference to the controller
	private CustomersController customersController = CustomersController.getInstanceOf();
	
	// Aler panel to manage exceptions
	private final AlertTypes alert = new AlertTypesImpl();

	@FXML
	private TableView<CustomerImpl> customersTable;
	
	@FXML
	private TableColumn<CustomerImpl, String> nameColumn, addressColumn, phoneColumn, typeColumn;
	
	@FXML
    private TextField nameField, addressField, phoneField;	
    @FXML
	private ChoiceBox<String> typeChoiceBox;
    
    @FXML
	private Button delete;
    @FXML
    private TextField searchField;
	
	public CustomersControl(){
		super();
	}
	
	/**
     * Called after the fxml file has been loaded; this method initializes 
     * the fxml control class. 
     */
	@FXML
    private void initialize() {
		
		 // Initialize the table
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().telephoneNumberProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
       
        // Add observable list data to the table
        customersTable.setItems(customersController.getCustomers());
        
        // Make the table columns editable by double clicking
        this.edit();
 
        // Listen for selection changes and enable delete button
        this.update();
        
        // Use a 'searchField' to search for books in the tableView
        this.search();
	}
	
	/**
     * Called when the user press the 'add' button 
     * This method adds a new customer/supplier to the controller ObservableList 
     * of customers
     */
	@FXML
    private void add() {
        try {
           customersController.addCustomer(nameField.getText(), addressField.getText(), phoneField.getText(), typeChoiceBox.getValue());
           this.clear();
        } catch (Exception e) {
        	alert.showWarning(e);
        }
    }
	
	/**
     * Called when the user edit a customer field directly from the table;
     * This method edits the selected field in the observableList of customers and 
     * makes all the fields editable directly from the table
     */
	@SuppressWarnings("unchecked")
	private void edit() {

		 for(TableColumn<CustomerImpl, ?> column: customersTable.getColumns()){
			 if(column instanceof TableColumn){
				//Set all the columns as editable directly from the tableView
				 ((TableColumn<CustomerImpl, String>)column).setCellFactory(TextFieldTableCell.forTableColumn());
					
					((TableColumn<CustomerImpl, String>)column).setOnEditCommit(t -> {
			        	try{
			        		String newValue = t.getNewValue();
			        		CustomerImpl book = t.getTableView().getItems().get(t.getTablePosition().getRow());
			        		
			        		if(((TableColumn<CustomerImpl, String>)column).getText().equals("Name")) 
			        				customersController.editName(book, newValue);
			        		
			        		else if(((TableColumn<CustomerImpl, String>)column).getText().equals("Address")) 
			        			customersController.editAddress(book, newValue); 
			        		
			        		else if(((TableColumn<CustomerImpl, String>)column).getText().equals("Telephone")) 
			        			customersController.editPhone(book, newValue); 
				        }catch(Exception e){
				        	alert.showWarning(e);
				        }
					});
			 }
		 }
	}

	/**
     * On delete button press, opens a confirmation dialog asking if you 
     * really want to delete the element
     * this method deletes the selected element from the observableList of customers
     */
    @FXML
    private void delete() {
    	Optional<ButtonType> result = alert.showConfirmation(customersTable.getSelectionModel().getSelectedItem().getName());

        if (result.get() == ButtonType.OK) {
            int selectedIndex = customersTable.getSelectionModel().getSelectedIndex();
            customersController.removeCustomer(customersTable.getItems().get(selectedIndex));
        }
    }
    
    /**
     * Called when the user enter something in the search field;
     * It search the entered string in all the customers fields(name, address, phone number)
     */
    private void search(){
    	searchField.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (!newValue.isEmpty()){
		        customersTable.setItems(FXCollections.observableArrayList(customersController.searchCustomer(newValue)));
        	}else customersTable.setItems(customersController.getCustomers());
        });
    }
    
    /**
     * Called when the user wants to convert the TableView to a PDF file
     * After converting the file it opens an information dialog to notify
     * the success
     */
    @FXML
    private void convert() {
    	try {
			customersController.convert();
			alert.showConverted();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * It listen for selection changes to disable/enable the delete button 
	 * when the user selects something in the table
	 */
    private void update(){
    	delete.setDisable(true);
        customersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	delete.setDisable(false);
        } );
    }
}
