package hust.soict.hedspi.aims.test.exception;

import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.media.*;
import hust.soict.hedspi.aims.exception.PlayerException;
import hust.soict.hedspi.aims.exception.CartFullException;
import hust.soict.hedspi.aims.exception.ItemNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class TestExceptionHandling {
    
    public static void main(String[] args) {
        System.out.println("=== AIMS Exception Handling Test ===\n");
        
        // Test PlayerException with invalid media lengths
        testPlayerExceptions();
        
        // Test Cart exceptions
        testCartExceptions();
        
        // Test Media comparison methods
        testMediaComparison();
        
        System.out.println("\n=== All Exception Tests Completed ===");
    }
    
    private static void testPlayerExceptions() {
        System.out.println("1. Testing PlayerException handling:\n");
        
        // Test DVD with invalid length
        System.out.println("a) Testing DVD with non-positive length:");
        try {
            DigitalVideoDisc invalidDvd = new DigitalVideoDisc("Invalid DVD", "Test", "Test Director", 0, 19.99f);
            invalidDvd.play();
        } catch (PlayerException e) {
            System.out.println("Caught PlayerException: " + e.getMessage());
            System.out.println("Exception toString(): " + e.toString());
            System.out.print("printStackTrace(): ");
            e.printStackTrace();
        }
        
        // Test Track with invalid length
        System.out.println("\nb) Testing Track with non-positive length:");
        try {
            Track invalidTrack = new Track("Invalid Track", -5);
            invalidTrack.play();
        } catch (PlayerException e) {
            System.out.println("Caught PlayerException: " + e.getMessage());
            System.out.println("Exception toString(): " + e.toString());
        }
        
        // Test CD with valid length but invalid track
        System.out.println("\nc) Testing CD with invalid track:");
        try {
            CompactDisc cd = new CompactDisc(1, "Test CD", "Music", 15.99f, "Test Director", 0, "Test Artist");
            cd.addTrack(new Track("Valid Track", 180));
            cd.addTrack(new Track("Invalid Track", 0));
            cd.play();
        } catch (PlayerException e) {
            System.out.println("Caught PlayerException from CD: " + e.getMessage());
        }
        
        // Test valid playback
        System.out.println("\nd) Testing valid playback:");
        try {
            DigitalVideoDisc validDvd = new DigitalVideoDisc("Valid DVD", "Action", "Director", 120, 24.99f);
            validDvd.play();
            System.out.println("DVD played successfully!");
        } catch (PlayerException e) {
            System.out.println("Unexpected PlayerException: " + e.getMessage());
        }
    }
    
    private static void testCartExceptions() {
        System.out.println("\n2. Testing Cart exception handling:\n");
        
        Cart cart = new Cart();
        
        // Test adding null media
        System.out.println("a) Testing adding null media:");
        try {
            cart.addMedia(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught IllegalArgumentException: " + e.getMessage());
        } catch (CartFullException e) {
            System.out.println("Unexpected CartFullException: " + e.getMessage());
        }
        
        // Test filling cart to capacity
        System.out.println("\nb) Testing cart capacity limit:");
        try {
            for (int i = 1; i <= Cart.MAX_NUMBERS_ORDERED + 2; i++) {
                DigitalVideoDisc dvd = new DigitalVideoDisc("DVD " + i, "Category", "Director", 90, 19.99f);
                cart.addMedia(dvd);
                if (i == Cart.MAX_NUMBERS_ORDERED) {
                    System.out.println("Cart is now full with " + cart.getItemsOrdered().size() + " items");
                }
            }
        } catch (CartFullException e) {
            System.out.println("Caught CartFullException: " + e.getMessage());
        }
        
        // Test removing non-existent item
        System.out.println("\nc) Testing removal of non-existent item:");
        try {
            DigitalVideoDisc nonExistentDvd = new DigitalVideoDisc("Non-existent DVD", "Test", "Director", 90, 19.99f);
            cart.removeMedia(nonExistentDvd);
        } catch (ItemNotFoundException e) {
            System.out.println("Caught ItemNotFoundException: " + e.getMessage());
        }
        
        // Test removing null media
        System.out.println("\nd) Testing removal of null media:");
        try {
            cart.removeMedia(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught IllegalArgumentException: " + e.getMessage());
        } catch (ItemNotFoundException e) {
            System.out.println("Unexpected ItemNotFoundException: " + e.getMessage());
        }
        
        // Test safe methods
        System.out.println("\ne) Testing safe cart operations:");
        DigitalVideoDisc testDvd = new DigitalVideoDisc("Test DVD", "Test", "Director", 90, 19.99f);
        
        boolean addResult = cart.addMediaSafe(testDvd);
        System.out.println("Safe add result (should be false - cart full): " + addResult);
        
        // Clear cart and try again
        cart.getItemsOrdered().clear();
        addResult = cart.addMediaSafe(testDvd);
        System.out.println("Safe add result (should be true - cart empty): " + addResult);
        
        boolean removeResult = cart.removeMediaSafe(testDvd);
        System.out.println("Safe remove result (should be true - item exists): " + removeResult);
        
        removeResult = cart.removeMediaSafe(testDvd);
        System.out.println("Safe remove result (should be false - item doesn't exist): " + removeResult);
    }
    
    private static void testMediaComparison() {
        System.out.println("\n3. Testing Media comparison methods (equals() and compareTo()):\n");
        
        // Create test media objects
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("Avatar", "Action", "James Cameron", 162, 25.99f);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Avatar", "Action", "James Cameron", 162, 25.99f); // Same title and cost
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Avatar", "Action", "James Cameron", 162, 19.99f); // Same title, different cost
        DigitalVideoDisc dvd4 = new DigitalVideoDisc("Titanic", "Drama", "James Cameron", 195, 25.99f); // Different title, same cost
        
        CompactDisc cd1 = new CompactDisc(5, "Thriller", "Music", 25.99f, "Quincy Jones", 42, "Michael Jackson");
        
        System.out.println("a) Testing equals() method:");
        System.out.println("dvd1.equals(dvd2) [same title and cost]: " + dvd1.equals(dvd2));
        System.out.println("dvd1.equals(dvd3) [same title, different cost]: " + dvd1.equals(dvd3));
        System.out.println("dvd1.equals(dvd4) [different title, same cost]: " + dvd1.equals(dvd4));
        System.out.println("dvd1.equals(cd1) [different types, same cost]: " + dvd1.equals(cd1));
        System.out.println("dvd1.equals(null): " + dvd1.equals(null));
        
        System.out.println("\nb) Testing compareTo() method:");
        System.out.println("dvd1.compareTo(dvd2) [same title and cost]: " + dvd1.compareTo(dvd2));
        System.out.println("dvd1.compareTo(dvd4) [different titles]: " + dvd1.compareTo(dvd4));
        System.out.println("dvd4.compareTo(dvd1) [reverse comparison]: " + dvd4.compareTo(dvd1));
        
        // Test null comparison
        System.out.println("\nc) Testing null comparison:");
        try {
            dvd1.compareTo(null);
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException: " + e.getMessage());
        }
        
        System.out.println("\nd) Testing ArrayList sorting with Comparable:");
        ArrayList<Media> mediaList = new ArrayList<>();
        mediaList.add(dvd4); // Titanic
        mediaList.add(dvd1); // Avatar
        mediaList.add(cd1);  // Thriller
        mediaList.add(dvd3); // Avatar (lower cost)
        
        System.out.println("Before sorting:");
        for (Media media : mediaList) {
            System.out.println("  " + media.toString());
        }
        
        Collections.sort(mediaList);
        
        System.out.println("After sorting (by title, then cost):");
        for (Media media : mediaList) {
            System.out.println("  " + media.toString());
        }
        
        System.out.println("\ne) Testing custom comparators:");
        System.out.println("Sorting by COMPARE_BY_COST_TITLE:");
        Collections.sort(mediaList, Media.COMPARE_BY_COST_TITLE);
        for (Media media : mediaList) {
            System.out.println("  " + media.toString());
        }
    }
} 