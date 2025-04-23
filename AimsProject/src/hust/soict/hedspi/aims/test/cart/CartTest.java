package hust.soict.hedspi.aims.test.cart;

import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.media.DigitalVideoDisc;

public class CartTest {
    public static void main(String[] args) {
        // Create a new cart
        Cart cart = new Cart();
        
        // Create new DVD objects
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", 
                "Animation", "Roger Allers", 87, 19.95f);
        
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", 
                "Science Fiction", "George Lucas", 124, 24.95f);
        
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Aladin", 
                "Animation", "John Musker", 90, 18.99f);
        
        // Test the addMedia() methods
        System.out.println("Testing add methods:");
        cart.addMedia(dvd1);
        
        // Test adding an array of DVDs
        DigitalVideoDisc[] dvdList = {dvd2, dvd3};
        for (DigitalVideoDisc dvd : dvdList) {
            cart.addMedia(dvd);
        }
        
        // Test printCart() method
        System.out.println("\nTesting printCart() method:");
        cart.printCart();
        
        // Test searchById() method
        System.out.println("\nTesting searchById() method:");
        cart.searchById(dvd2.getId());  // Should find Star Wars
        cart.searchById(999);  // Should not find anything
        
        // Test searchByTitle() method
        System.out.println("\nTesting searchByTitle() method:");
        cart.searchByTitle("Lion");  // Should find The Lion King
        cart.searchByTitle("Star");  // Should find Star Wars
        cart.searchByTitle("Batman");  // Should not find anything
        
        // Test removeMedia() method
        System.out.println("\nTesting remove method:");
        cart.removeMedia(dvd1);
        
        // Print cart again to verify removal
        System.out.println("\nCart after removing The Lion King:");
        cart.printCart();
        
        // Test total cost calculation
        System.out.println("\nTotal cost: " + cart.totalCost() + " $");
    }
}
