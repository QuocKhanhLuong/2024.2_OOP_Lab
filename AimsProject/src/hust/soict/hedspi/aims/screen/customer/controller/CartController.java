package hust.soict.hedspi.aims.screen.customer.controller;

import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.media.*;
import hust.soict.hedspi.aims.store.Store;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import hust.soict.hedspi.aims.exception.PlayerException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    @FXML
    private TableView<Media> tblMedia;
    
    @FXML
    private TableColumn<Media, Integer> colMediaId;
    
    @FXML
    private TableColumn<Media, String> colMediaTitle;
    
    @FXML
    private TableColumn<Media, String> colMediaCategory;
    
    @FXML
    private TableColumn<Media, Float> colMediaCost;
    
    @FXML
    private Button btnPlay;
    
    @FXML
    private Button btnRemove;
    
    @FXML
    private Label costLabel;
    
    @FXML
    private TextField tfFilter;
    
    @FXML
    private RadioButton radioBtnFilterId;
    
    @FXML
    private RadioButton radioBtnFilterTitle;
    
    private Cart cart;
    private Store store;
    private FilteredList<Media> filteredData;

    // Constructor that accepts both Store and Cart
    public CartController(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;
    }

    // Default constructor
    public CartController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up the columns' cellValueFactory using PropertyValueFactory
        colMediaId.setCellValueFactory(new PropertyValueFactory<Media, Integer>("id"));
        colMediaTitle.setCellValueFactory(new PropertyValueFactory<Media, String>("title"));
        colMediaCategory.setCellValueFactory(new PropertyValueFactory<Media, String>("category"));
        colMediaCost.setCellValueFactory(new PropertyValueFactory<Media, Float>("cost"));
        
        btnPlay.setVisible(false);
        btnRemove.setVisible(false);
        
        tblMedia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Media>() {
            @Override
            public void changed(ObservableValue<? extends Media> observable, Media oldValue, Media newValue) {
                updateButtonBar(newValue);
            }
        });
        
        // Add ChangeListener to the TextField's text property
        tfFilter.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                showFilteredMedia();
            }
        });
    }
    
    void updateButtonBar(Media media) {
        if (media == null) {
            btnPlay.setVisible(false);
            btnRemove.setVisible(false);
        } else {
            btnRemove.setVisible(true);
            if (media instanceof Playable) {
                btnPlay.setVisible(true);
            } else {
                btnPlay.setVisible(false);
            }
        }
    }
    
    public void setCart(Cart cart) {
        this.cart = cart;
        updateCartDisplay();
        
        // Add a listener to the cart's ObservableList to update total cost automatically
        if (cart != null && cart.getItemsOrdered() != null) {
            cart.getItemsOrdered().addListener(new ListChangeListener<Media>() {
                @Override
                public void onChanged(Change<? extends Media> change) {
                    updateTotalCost();
                }
            });
        }
    }
    
    public void setStore(Store store) {
        this.store = store;
    }
    
    private void updateCartDisplay() {
        if (cart != null) {
            // Wrap the ObservableList in a FilteredList
            filteredData = new FilteredList<>(cart.getItemsOrdered(), p -> true);
            tblMedia.setItems(filteredData);
            updateTotalCost();
        }
    }
    
    private void showFilteredMedia() {
        if (filteredData != null) {
            String filterText = tfFilter.getText().trim();
            
            if (filterText.isEmpty()) {
                // Show all items if filter is empty
                filteredData.setPredicate(media -> true);
            } else {
                // Apply filter based on selected radio button
                if (radioBtnFilterId.isSelected()) {
                    // Filter by ID
                    try {
                        int filterId = Integer.parseInt(filterText);
                        filteredData.setPredicate(media -> media.getId() == filterId);
                    } catch (NumberFormatException e) {
                        // If not a valid number, show no results
                        filteredData.setPredicate(media -> false);
                    }
                } else if (radioBtnFilterTitle.isSelected()) {
                    // Filter by Title
                    filteredData.setPredicate(media -> 
                        media.getTitle().toLowerCase().contains(filterText.toLowerCase())
                    );
                }
            }
        }
    }
    
    private void updateTotalCost() {
        if (cart != null) {
            float total = cart.totalCost();
            costLabel.setText(String.format("%.2f $", total));
        }
    }
    
    @FXML
    private void btnPlayPressed() {
        Media selectedMedia = tblMedia.getSelectionModel().getSelectedItem();
        if (selectedMedia != null && selectedMedia instanceof Playable) {
            try {
                ((Playable) selectedMedia).play();
                
                // Show play dialog
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Playing Media");
                alert.setHeaderText("Now Playing: " + selectedMedia.getTitle());
                
                String content = "";
                if (selectedMedia instanceof DigitalVideoDisc) {
                    DigitalVideoDisc dvd = (DigitalVideoDisc) selectedMedia;
                    content = "Playing DVD: " + dvd.getTitle() + "\nLength: " + dvd.getLength() + " minutes";
                } else if (selectedMedia instanceof CompactDisc) {
                    CompactDisc cd = (CompactDisc) selectedMedia;
                    content = "Playing CD: " + cd.getTitle() + "\nArtist: " + cd.getArtist() + "\nLength: " + cd.getLength() + " minutes";
                }
                
                alert.setContentText(content);
                alert.showAndWait();
                
            } catch (PlayerException e) {
                // Show error dialog for playback issues
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Playback Error");
                errorAlert.setHeaderText("Cannot Play Media");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }
    
    @FXML
    private void btnRemovePressed() {
        Media selectedMedia = tblMedia.getSelectionModel().getSelectedItem();
        if (selectedMedia != null) {
            // Confirm removal
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Remove Item");
            confirmation.setHeaderText("Remove from Cart");
            confirmation.setContentText("Are you sure you want to remove \"" + selectedMedia.getTitle() + "\" from your cart?");
            
            if (confirmation.showAndWait().get() == ButtonType.OK) {
                boolean success = cart.removeMediaSafe(selectedMedia);
                if (!success) {
                    // Show error if removal failed
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Removal Error");
                    errorAlert.setHeaderText("Cannot Remove Item");
                    errorAlert.setContentText("The item could not be removed from the cart.");
                    errorAlert.showAndWait();
                }
                updateTotalCost();
            }
        }
    }
    
    @FXML
    private void btnPlaceOrderPressed() {
        if (cart == null || cart.getItemsOrdered().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Cart");
            alert.setHeaderText("Cannot Place Order");
            alert.setContentText("Your cart is empty. Please add items before placing an order.");
            alert.showAndWait();
            return;
        }
        
        // Show order confirmation
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Place Order");
        confirmation.setHeaderText("Confirm Order");
        confirmation.setContentText(String.format("Total: %.2f $\nPlace this order?", cart.totalCost()));
        
        if (confirmation.showAndWait().get() == ButtonType.OK) {
            // Clear cart and show success message
            cart.getItemsOrdered().clear();
            
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Order Placed");
            success.setHeaderText("Order Successful");
            success.setContentText("Your order has been placed successfully! Thank you for your purchase.");
            success.showAndWait();
            
            // Update the display
            updateTotalCost();
        }
    }
    
    @FXML
    private void btnViewStorePressed() {
        try {
            final String STORE_FXML_FILE_PATH = "/hust/soict/hedspi/aims/screen/customer/view/Store.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(STORE_FXML_FILE_PATH));
            fxmlLoader.setController(new ViewStoreController(store, cart));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) tblMedia.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Store");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 