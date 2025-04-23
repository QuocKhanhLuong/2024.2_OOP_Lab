package hust.soict.hedspi.aims.store;

import hust.soict.hedspi.aims.media.DigitalVideoDisc;

public class StoreTest {
    public static void main(String[] args) {
        // Create a new store
        Store store = new Store();
        
        // Create some DVDs
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", 
                "Animation", "Roger Allers", 87, 19.95f);
        
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", 
                "Science Fiction", "George Lucas", 124, 24.95f);
        
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Aladin", 
                "Animation", "John Musker", 90, 18.99f);
        
        // Test the addMedia method
        System.out.println("Testing adding DVDs to store:");
        store.addMedia(dvd1);
        store.addMedia(dvd2);
        
        // Test adding multiple DVDs
        DigitalVideoDisc[] dvdList = {dvd3};
        store.addMedia(dvdList);
        
        // Print store contents
        System.out.println("\nStore after adding DVDs:");
        store.printStore();
        
        // Test the removeMedia method
        System.out.println("\nTesting removing DVD from store:");
        store.removeMedia(dvd2);  // Remove Star Wars
        
        // Try removing a DVD not in the store
        DigitalVideoDisc dvd4 = new DigitalVideoDisc("Inception", 
                "Science Fiction", "Christopher Nolan", 148, 22.99f);
        store.removeMedia(dvd4);
        
        // Print store contents again
        System.out.println("\nStore after removing DVDs:");
        store.printStore();
        
        // Print item count
        System.out.println("\nNumber of items in store: " + store.getItemCount());
    }
}
