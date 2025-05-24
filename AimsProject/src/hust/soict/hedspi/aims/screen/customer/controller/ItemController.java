package hust.soict.hedspi.aims.screen.customer.controller;

import java.net.URL;
import java.util.ResourceBundle;

import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.media.*;
import hust.soict.hedspi.aims.exception.PlayerException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ItemController implements Initializable {

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblCost;

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnPlay;

    private Media media;
    private Cart cart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Media media) {
        this.media = media;
        lblTitle.setText(media.getTitle());
        lblCost.setText(media.getCost() + " $");
        if (media instanceof Playable) {
            btnPlay.setVisible(true);
        } else {
            btnPlay.setVisible(false);
        }
        HBox.setMargin(btnAddToCart, new Insets(0, 0, 0, 60));
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @FXML
    void btnAddToCartClicked() {
        if (cart != null && media != null) {
            boolean success = cart.addMediaSafe(media);
            
            Alert alert;
            if (success) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Item Added");
                alert.setHeaderText("Added to Cart");
                alert.setContentText(media.getTitle() + " has been added to your cart!");
            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cannot Add Item");
                alert.setHeaderText("Cart is Full");
                alert.setContentText("Cannot add " + media.getTitle() + " - the cart is full!");
            }
            alert.showAndWait();
        }
    }

    @FXML
    void btnPlayClicked() {
        if (media instanceof Playable) {
            try {
                ((Playable) media).play();
                
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
                
            } catch (PlayerException e) {
                // Show error dialog
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Playback Error");
                errorAlert.setHeaderText("Cannot Play Media");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }
} 