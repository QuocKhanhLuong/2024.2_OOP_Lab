package hust.soict.hedspi.aims.screen.customer.controller;

import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.media.*;
import hust.soict.hedspi.aims.store.Store;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StoreController implements Initializable {

    @FXML
    private GridPane mediaGrid;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Label cartItemsLabel;
    
    @FXML
    private Button cartButton;
    
    private Store store;
    private Cart cart;
    
    public void setStore(Store store) {
        this.store = store;
        displayMedia(store.getItemsInStore());
    }
    
    public void setCart(Cart cart) {
        this.cart = cart;
        updateCartDisplay();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize any components if needed
    }
    
    private void displayMedia(ArrayList<Media> mediaList) {
        mediaGrid.getChildren().clear();
        
        int columns = 3;
        int row = 0;
        int col = 0;
        
        for (Media media : mediaList) {
            VBox mediaCard = createMediaCard(media);
            mediaGrid.add(mediaCard, col, row);
            
            col++;
            if (col >= columns) {
                col = 0;
                row++;
            }
        }
    }
    
    private VBox createMediaCard(Media media) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        card.setPrefWidth(200);
        
        // Title
        Label titleLabel = new Label(media.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        titleLabel.setWrapText(true);
        
        // Category
        Label categoryLabel = new Label("Category: " + media.getCategory());
        categoryLabel.setStyle("-fx-font-size: 12px;");
        
        // Cost
        Label costLabel = new Label("Price: $" + String.format("%.2f", media.getCost()));
        costLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #007acc;");
        
        // Media type specific info
        Label typeLabel = new Label();
        if (media instanceof DigitalVideoDisc) {
            DigitalVideoDisc dvd = (DigitalVideoDisc) media;
            typeLabel.setText("DVD - Length: " + dvd.getLength() + " min");
        } else if (media instanceof CompactDisc) {
            CompactDisc cd = (CompactDisc) media;
            typeLabel.setText("CD - Artist: " + cd.getArtist());
        } else if (media instanceof Book) {
            typeLabel.setText("Book");
        }
        typeLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");
        
        // Buttons
        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addToCartBtn.setOnAction(e -> addToCart(media));
        
        card.getChildren().addAll(titleLabel, categoryLabel, costLabel, typeLabel, addToCartBtn);
        
        // Add play button for DVDs and CDs
        if (media instanceof Playable) {
            Button playBtn = new Button("Play");
            playBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
            playBtn.setOnAction(e -> playMedia(media));
            card.getChildren().add(playBtn);
        }
        
        return card;
    }
    
    private void addToCart(Media media) {
        cart.addMedia(media);
        updateCartDisplay();
        
        // Show confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Item Added");
        alert.setHeaderText(null);
        alert.setContentText(media.getTitle() + " has been added to your cart!");
        alert.showAndWait();
    }
    
    private void playMedia(Media media) {
        if (media instanceof Playable) {
            // Show play dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Playing Media");
            alert.setHeaderText("Now Playing: " + media.getTitle());
            
            String content = "";
            if (media instanceof DigitalVideoDisc) {
                DigitalVideoDisc dvd = (DigitalVideoDisc) media;
                content = "Playing DVD: " + dvd.getTitle() + "\nLength: " + dvd.getLength() + " minutes";
            } else if (media instanceof CompactDisc) {
                CompactDisc cd = (CompactDisc) media;
                content = "Playing CD: " + cd.getTitle() + "\nArtist: " + cd.getArtist() + "\nLength: " + cd.getLength() + " minutes";
            }
            
            alert.setContentText(content);
            alert.showAndWait();
        }
    }
    
    private void updateCartDisplay() {
        if (cart != null) {
            int itemCount = cart.getItemsOrdered().size();
            cartItemsLabel.setText(itemCount + " items in cart");
        }
    }
    
    @FXML
    private void searchMedia() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            displayMedia(store.getItemsInStore());
        } else {
            ArrayList<Media> searchResults = new ArrayList<>();
            for (Media media : store.getItemsInStore()) {
                if (media.isMatch(searchTerm)) {
                    searchResults.add(media);
                }
            }
            displayMedia(searchResults);
        }
    }
    
    @FXML
    private void clearSearch() {
        searchField.clear();
        displayMedia(store.getItemsInStore());
    }
    
    @FXML
    private void viewStore() {
        // Already in store view, just refresh
        displayMedia(store.getItemsInStore());
    }
    
    @FXML
    private void viewCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Cart.fxml"));
            Parent root = loader.load();
            
            CartController cartController = loader.getController();
            cartController.setCart(cart);
            cartController.setStore(store);
            
            Stage stage = (Stage) cartButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("AIMS - Shopping Cart");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 