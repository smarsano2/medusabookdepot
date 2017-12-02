/**
 * 'depots.fxml' Control Class
 */

package com.medusabookdepot.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.medusabookdepot.controller.DepotsController;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;
import com.medusabookdepot.view.util.PersistentButtonToggleGroup;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class DepotsControl extends ScreenControl {
	
	// Reference to the controller
	private final DepotsController depotsController = DepotsController.getInstanceOf();
	
	// Aler panel to manage exceptions
    private final AlertTypes alert = new AlertTypesImpl();
    
    // List of books & quantity to view in the table
    private final ObservableList<Entry<StandardBookImpl,Integer>> data = FXCollections.observableArrayList();
    
    // ToggleGroup to have just one toggleButton selected at a time
    private final ToggleGroup buttonsGroup = new PersistentButtonToggleGroup();
    private final List<ToggleButton> buttonsList = new ArrayList<>();

	@FXML
	private TableView<Entry<StandardBookImpl,Integer>> depotsTable;
	
	@FXML
	private TableColumn<Entry<StandardBookImpl,Integer>, String> quantityColumn, isbnColumn, titleColumn, yearColumn,
		pagesColumn, serieColumn, genreColumn, authorColumn, priceColumn;
	
	@FXML
	private HBox hBox;
	
	@FXML
	private TextField searchField;
    
	public DepotsControl(){
		super();
		
		// Creating a button for each depot and adding such button to the group and to the list
		for(DepotImpl depot : depotsController.getDepots()){
			ToggleButton button = new ToggleButton(depot.getName());
			button.setToggleGroup(buttonsGroup);
			buttonsList.add(button);
			button.setUserData(depot.getName());
		}
	}
	
	/**
     * Called after the fxml file has been loaded; this method initializes 
     * the fxml control class. 
     */
	public void initialize() {
		
		// Adding the buttons created in the constructor to the hBox, after the title
		int hBoxPos=2; // hBoxPos is 2 because I need to add buttons after the title, which is in pos 1
		for(ToggleButton button : buttonsList){	
			hBox.getChildren().add(hBoxPos, button);
			hBoxPos++; // the other buttons will be added after the one I just added
		}
		
		// Initializing the table
        quantityColumn.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().isbnProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().titleProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().yearProperty().asString());
        pagesColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().pagesProperty().asString());
        serieColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().serieProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().genreProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().authorProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().priceProperty().asString());
		
        // Method which handle the selection of a depot
        this.filter();
        
        // Use a 'searchField' to search for books in the tableView
        this.search();
        
        // Selecting the first depot of the list for the first time the user opens the screen
        if(!buttonsList.isEmpty()) buttonsList.get(0).setSelected(true);
        
        // Putting data into the table
        depotsTable.setItems(data);
	}
	
	/**
	 * Called when the user selects a toggle-button 
	 * Method to filter depots so in the table there are only the books from the selected depot
	 */
	private void filter(){
		
		buttonsGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(newValue==null){
					// When there are no buttons toggled, the table is empty
					data.clear();
				} else{
					// The table is cleaned
					data.clear();
					// Data is added to the table, relatively to the toggle button selected
					data.addAll(depotsController.filterDepot((String)buttonsGroup.getSelectedToggle().getUserData()).findFirst().get().getBooks().entrySet());
				}
			}
		});
	}
	
	/**
     * Called when the user enter something in the search field;
     * It search the entered string in all the depots fields
     */
    private void search(){
    	searchField.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (!newValue.isEmpty()){
		      depotsTable.setItems(FXCollections.observableArrayList(depotsController.searchDepot(data, newValue)));
        	}else depotsTable.setItems(data);
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
            depotsController.convert();
            alert.showConverted();
        } catch (IOException e) {
           alert.showConvertError(e);
        }
    }
}
