package hust.soict.hedspi.aims.screen.customer;

import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.media.*;
import hust.soict.hedspi.aims.store.Store;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomerMain extends Application {
    
    private static Store store;
    private static Cart cart;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize store with sample data
        initializeStore();
        
        // Initialize empty cart
        cart = new Cart();
        
        // Load the store view FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Store.fxml"));
        loader.setController(new ViewStoreController(store, cart));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("AIMS - Customer Store");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void initializeStore() {
        store = new Store();
        
        // Add sample DVDs
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", "Animation", "Roger Allers", 87, 19.95f);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", "Science Fiction", "George Lucas", 124, 24.95f);
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Aladdin", "Animation", "John Musker", 90, 18.99f);
        
        // Add sample CDs
        CompactDisc cd1 = new CompactDisc(4, "Greatest Hits", "Music", 15.99f, "Various", 60, "Queen");
        cd1.addTrack(new Track("Bohemian Rhapsody", 355));
        cd1.addTrack(new Track("We Will Rock You", 122));
        cd1.addTrack(new Track("Don't Stop Me Now", 208));
        
        CompactDisc cd2 = new CompactDisc(5, "Thriller", "Music", 12.99f, "Quincy Jones", 42, "Michael Jackson");
        cd2.addTrack(new Track("Billie Jean", 294));
        cd2.addTrack(new Track("Beat It", 258));
        cd2.addTrack(new Track("Thriller", 357));
        
        // Add sample Books
        Book book1 = new Book(6, "The Great Gatsby", "Fiction", 10.99f);
        book1.addAuthor("F. Scott Fitzgerald");
        
        Book book2 = new Book(7, "Java Programming", "Education", 45.99f);
        book2.addAuthor("Oracle Press");
        book2.addAuthor("Herbert Schildt");
        
        // Add all items to store
        store.addMedia(dvd1);
        store.addMedia(dvd2);
        store.addMedia(dvd3);
        store.addMedia(cd1);
        store.addMedia(cd2);
        store.addMedia(book1);
        store.addMedia(book2);
    }
    
    public static Store getStore() {
        return store;
    }
    
    public static Cart getCart() {
        return cart;
    }

    public static void main(String[] args) {
        launch(args);
    }
} 