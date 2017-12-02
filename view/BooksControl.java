/**
 * 'books.fxml' and 'addBook.fxml' Control Class
 */

package com.medusabookdepot.view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.view.alert.AlertTypes;
import com.medusabookdepot.view.alert.AlertTypesImpl;

import java.io.IOException;
import java.util.Optional;

import com.medusabookdepot.controller.BooksController;

public class BooksControl extends ScreenControl {
	
	// Reference to the controller
    private BooksController booksController = BooksController.getInstanceOf();
    
    // Aler panel to manage exceptions
    private final AlertTypes alert = new AlertTypesImpl();

    @FXML
    private TableView<StandardBookImpl> stdBooksTable;
    
    @FXML
    private TableColumn<StandardBookImpl, String> isbnColumn, titleColumn, yearColumn, pagesColumn, 
    	serieColumn, genreColumn, authorColumn, priceColumn;

    @FXML
    private TextField isbnField, titleField, yearField, pagesField, serieField, genreField, authorField, priceField;

    @FXML
    private Button delete;
    @FXML
    private Button convert;
    @FXML
    private TextField searchField;
    
    public BooksControl() {
    	super();
    }

    /**
     * Called after the fxml file has been loaded; this method initializes 
     * the fxml control class. 
     */
    @FXML
    private void initialize() {
	
        // Initialize the table
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asString());
        pagesColumn.setCellValueFactory(cellData -> cellData.getValue().pagesProperty().asString());
        serieColumn.setCellValueFactory(cellData -> cellData.getValue().serieProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        priceColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(booksController.convertPriceToString
        		(cellData.getValue().getPrice())));

        // Add observable list data to the table
        stdBooksTable.setItems(booksController.getBooks());

        // Make the table columns editable by double clicking
        this.edit();

        // Listen for selection changes and enable delete button
        this.update();
        
        // Use a 'searchField' to search for books in the tableView
        this.search();
    }
    
    /**
     * Called when the user press the 'add' button;
     * This method adds a new book to the controller ObservableList of books
     */
    @FXML
    private void add() {
        try {
           booksController.addBook(isbnField.getText(), titleField.getText(), yearField.getText(),
                    pagesField.getText(), serieField.getText(), genreField.getText(),
                    authorField.getText(), priceField.getText());
           this.clear();
        } catch (IndexOutOfBoundsException e){
        	alert.showPriceError();
        } catch (Exception e) {
        	alert.showWarning(e);
        }
    }
    
    /**
     * Called when the user edit a book field directly from the table;
     * This method edits the selected field in the observableList of books and 
     * makes all the fields editable directly from the table
     */
    @SuppressWarnings("unchecked")
	private void edit() {
    	    
        for(TableColumn<StandardBookImpl, ?> n: stdBooksTable.getColumns()){
			if(n instanceof TableColumn){
				//Set all the columns as editable directly from the tableView
				((TableColumn<StandardBookImpl, String>)n).setCellFactory(TextFieldTableCell.forTableColumn());
				
				((TableColumn<StandardBookImpl, String>)n).setOnEditCommit(t -> {
		        	try{
		        		String newValue = t.getNewValue();
		        		StandardBookImpl book = t.getTableView().getItems().get(t.getTablePosition().getRow());
		        		
		        		if(((TableColumn<StandardBookImpl, String>)n).getText().equals("ISBN")) 
		        				booksController.editISBN(book, newValue);
		        		
		        		else if(((TableColumn<StandardBookImpl, String>)n).getText().equals("Title")) 
	        				booksController.editTitle(book, newValue); 
		        		
		        		else if(((TableColumn<StandardBookImpl, String>)n).getText().equals("Year")) 
	        				booksController.editYear(book, newValue);
		        		
		        		else if(((TableColumn<StandardBookImpl, String>)n).getText().equals("Pages")) 
	        				booksController.editPages(book, newValue);
		        		
		        		else if(((TableColumn<StandardBookImpl, String>)n).getText().equals("Serie")) 
	        				booksController.editSerie(book, newValue);
		        		
		        		else if(((TableColumn<StandardBookImpl, String>)n).getText().equals("Genre")) 
	        				booksController.editGenre(book, newValue);
		        		
		        		else if(((TableColumn<StandardBookImpl, String>)n).getText().equals("Author")) 
	        				booksController.editAuthor(book, newValue);
		        		
		        		else if(((TableColumn<StandardBookImpl, String>)n).getText().equals("Price")) 
	        				booksController.editPrice(book, newValue);
		        		
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
     * Method to delete the selected element from the observableList of books
     */
    @FXML
    private void delete() {

        Optional<ButtonType> result = alert.showConfirmation(stdBooksTable.getSelectionModel().getSelectedItem().getTitle());

        if (result.get() == ButtonType.OK) {
            int selectedIndex = stdBooksTable.getSelectionModel().getSelectedIndex();
            booksController.removeBook(stdBooksTable.getItems().get(selectedIndex));
        }
    }

    /**
     * Called when the user enter something in the search field;
     * It search the entered string in all the books fields(title, ISBN, etc..)
     */
    private void search(){
    	searchField.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (!newValue.isEmpty()){
		        stdBooksTable.setItems(FXCollections.observableArrayList(booksController.searchBook(newValue)));
        	}else stdBooksTable.setItems(booksController.getBooks());
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
            booksController.convert();
            alert.showConverted();
        } catch (IOException e) {
            alert.showConvertError(e);
        }
    }
    
    /**
	 * It listen for selection changes to disable/enable the delete button 
	 * when the user selects something in the table
	 */
    private void update(){
    	delete.setDisable(true);
        stdBooksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            delete.setDisable(false);
        });
    }
}