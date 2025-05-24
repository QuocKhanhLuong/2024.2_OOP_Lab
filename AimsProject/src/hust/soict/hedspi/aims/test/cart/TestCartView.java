package hust.soict.hedspi.aims.test.cart;

import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.media.*;
import hust.soict.hedspi.aims.store.Store;
import hust.soict.hedspi.aims.screen.customer.controller.CartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestCartView extends Application {
    private static Store store;
    private static Cart cart;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final String CART_FXML_FILE_PATH = "/hust/soict/hedspi/aims/screen/customer/view/Cart.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CART_FXML_FILE_PATH));
        Parent root = fxmlLoader.load();
        
        CartController cartController = fxmlLoader.getController();
        cartController.setCart(cart);
        cartController.setStore(store);

        primaryStage.setTitle("Cart - AIMS");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        store = new Store();
        cart = new Cart();
        
        // Create sample media items
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", "Animation", "Roger Allers", 87, 19.95f);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", "Science Fiction", "George Lucas", 124, 24.95f);
        
        CompactDisc cd1 = new CompactDisc(3, "Greatest Hits", "Music", 15.99f, "Various", 60, "Queen");
        cd1.addTrack(new Track("Bohemian Rhapsody", 355));
        cd1.addTrack(new Track("We Will Rock You", 122));
        
        Book book1 = new Book(4, "The Great Gatsby", "Fiction", 10.99f);
        book1.addAuthor("F. Scott Fitzgerald");
        
        // Add items to store
        store.addMedia(dvd1);
        store.addMedia(dvd2);
        store.addMedia(cd1);
        store.addMedia(book1);
        
        // Add some items to cart for testing
        cart.addMedia(dvd1);
        cart.addMedia(cd1);
        cart.addMedia(book1);
        
        launch(args);
    }
} 