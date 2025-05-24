package hust.soict.hedspi.aims.aims;

import java.util.Scanner;
import javax.swing.JOptionPane;
import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.store.Store;
import hust.soict.hedspi.aims.media.*;
import hust.soict.hedspi.aims.exception.PlayerException;
import hust.soict.hedspi.aims.exception.CartFullException;
import hust.soict.hedspi.aims.exception.ItemNotFoundException;

public class Aims {
    private static Store store = new Store();
    private static Cart cart = new Cart();
    
    static {
        initializeStoreData();
    }
    
    private static void initializeStoreData() {
        // Initialize store with sample data for testing
        try {
            DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", "Animation", "Roger Allers", 87, 19.95f);
            DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", "Science Fiction", "George Lucas", 124, 24.95f);
            DigitalVideoDisc dvd3 = new DigitalVideoDisc("Invalid DVD", "Test", "Test Director", 0, 19.99f); // Invalid for testing
            
            CompactDisc cd1 = new CompactDisc(4, "Greatest Hits", "Music", 15.99f, "Various", 60, "Queen");
            cd1.addTrack(new Track("Bohemian Rhapsody", 355));
            cd1.addTrack(new Track("We Will Rock You", 122));
            cd1.addTrack(new Track("Invalid Track", 0)); // Invalid track for testing
            
            CompactDisc cd2 = new CompactDisc(5, "Thriller", "Music", 12.99f, "Quincy Jones", 42, "Michael Jackson");
            cd2.addTrack(new Track("Billie Jean", 294));
            cd2.addTrack(new Track("Beat It", 258));
            
            Book book1 = new Book(6, "The Great Gatsby", "Fiction", 10.99f);
            book1.addAuthor("F. Scott Fitzgerald");
            
            store.addMedia(dvd1);
            store.addMedia(dvd2);
            store.addMedia(dvd3);
            store.addMedia(cd1);
            store.addMedia(cd2);
            store.addMedia(book1);
            
        } catch (Exception e) {
            System.err.println("Error initializing store data: " + e.getMessage());
        }
    }

    public static void showMenu() {
        System.out.println("AIMS: ");
        System.out.println("--------------------------------");
        System.out.println("1. View store");
        System.out.println("2. Update store");
        System.out.println("3. See current cart");
        System.out.println("0. Exit");
        System.out.println("--------------------------------");
        System.out.println("Please choose a number: 0-1-2-3");
    }

    public static void storeMenu() {
        System.out.println("Options: ");
        System.out.println("--------------------------------");
        System.out.println("1. See a media's details");
        System.out.println("2. Add a media to cart");
        System.out.println("3. Play a media");
        System.out.println("4. See current cart");
        System.out.println("0. Back");
        System.out.println("--------------------------------");
        System.out.println("Please choose a number: 0-1-2-3-4");
    }

    public static void mediaDetailsMenu() {
        System.out.println("Options: ");
        System.out.println("--------------------------------");
        System.out.println("1. Add to cart");
        System.out.println("2. Play");
        System.out.println("0. Back");
        System.out.println("--------------------------------");
        System.out.println("Please choose a number: 0-1-2");
    }

    public static void cartMenu() {
        System.out.println("Options: ");
        System.out.println("--------------------------------");
        System.out.println("1. Filter media in cart");
        System.out.println("2. Sort media in cart");
        System.out.println("3. Remove media from cart");
        System.out.println("4. Play a media");
        System.out.println("5. Place order");
        System.out.println("0. Back");
        System.out.println("--------------------------------");
        System.out.println("Please choose a number: 0-1-2-3-4-5");
    }
    
    private static void displayStore() {
        System.out.println("********************STORE********************");
        for (int i = 0; i < store.getItemsInStore().size(); i++) {
            Media media = store.getItemsInStore().get(i);
            System.out.println((i + 1) + ". " + media.toString());
        }
        System.out.println("*********************************************");
    }
    
    private static void playMediaFromStore(Scanner sc) {
        displayStore();
        System.out.print("Enter the number of the media to play: ");
        try {
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            if (choice < 1 || choice > store.getItemsInStore().size()) {
                System.out.println("Invalid selection!");
                return;
            }
            
            Media selectedMedia = store.getItemsInStore().get(choice - 1);
            playMedia(selectedMedia);
            
        } catch (Exception e) {
            System.err.println("Error selecting media: " + e.getMessage());
            sc.nextLine(); // consume invalid input
        }
    }
    
    private static void playMediaFromCart(Scanner sc) {
        if (cart.getItemsOrdered().isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }
        
        System.out.println("********************CART********************");
        for (int i = 0; i < cart.getItemsOrdered().size(); i++) {
            Media media = cart.getItemsOrdered().get(i);
            System.out.println((i + 1) + ". " + media.toString());
        }
        System.out.println("*******************************************");
        
        System.out.print("Enter the number of the media to play: ");
        try {
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            if (choice < 1 || choice > cart.getItemsOrdered().size()) {
                System.out.println("Invalid selection!");
                return;
            }
            
            Media selectedMedia = cart.getItemsOrdered().get(choice - 1);
            playMedia(selectedMedia);
            
        } catch (Exception e) {
            System.err.println("Error selecting media: " + e.getMessage());
            sc.nextLine(); // consume invalid input
        }
    }
    
    /**
     * Central method to play media with comprehensive exception handling
     */
    private static void playMedia(Media media) {
        if (!(media instanceof Playable)) {
            String errorMessage = "ERROR: The media \"" + media.getTitle() + "\" cannot be played!";
            System.err.println(errorMessage);
            
            // Show Swing dialog for error
            JOptionPane.showMessageDialog(null, 
                errorMessage,
                "Playback Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            System.out.println("Playing: " + media.getTitle());
            ((Playable) media).play();
            
            // Show success dialog
            JOptionPane.showMessageDialog(null,
                "Successfully played: " + media.getTitle(),
                "Playback Success",
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (PlayerException e) {
            // Print all exception information as required
            System.err.println("PlayerException occurred:");
            System.err.println("getMessage(): " + e.getMessage());
            System.err.println("toString(): " + e.toString());
            System.err.print("printStackTrace(): ");
            e.printStackTrace();
            
            // Show Swing dialog with exception details
            String dialogMessage = String.format(
                "Playback Error Details:\n\n" +
                "Message: %s\n\n" +
                "Exception Type: %s\n\n" +
                "Media: %s",
                e.getMessage(),
                e.getClass().getSimpleName(),
                media.getTitle()
            );
            
            JOptionPane.showMessageDialog(null,
                dialogMessage,
                "PlayerException - Playback Error",
                JOptionPane.ERROR_MESSAGE);
                
        } catch (ClassCastException e) {
            // Handle ClassCastException as mentioned in the requirements
            System.err.println("ClassCastException occurred:");
            System.err.println("getMessage(): " + e.getMessage());
            System.err.println("toString(): " + e.toString());
            System.err.print("printStackTrace(): ");
            e.printStackTrace();
            
            String errorMessage = String.format(
                "Class Cast Error:\n\n" +
                "Cannot cast media to Playable interface\n" +
                "Media: %s\n" +
                "Type: %s\n\n" +
                "Details: %s",
                media.getTitle(),
                media.getClass().getSimpleName(),
                e.getMessage()
            );
            
            JOptionPane.showMessageDialog(null,
                errorMessage,
                "ClassCastException - Type Error",
                JOptionPane.ERROR_MESSAGE);
                
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            System.err.println("Unexpected exception occurred:");
            System.err.println("getMessage(): " + e.getMessage());
            System.err.println("toString(): " + e.toString());
            System.err.print("printStackTrace(): ");
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(null,
                "An unexpected error occurred while playing media:\n\n" + e.getMessage(),
                "Unexpected Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static void addMediaToCart(Scanner sc) {
        displayStore();
        System.out.print("Enter the number of the media to add to cart: ");
        try {
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            if (choice < 1 || choice > store.getItemsInStore().size()) {
                System.out.println("Invalid selection!");
                return;
            }
            
            Media selectedMedia = store.getItemsInStore().get(choice - 1);
            cart.addMediaSafe(selectedMedia);
            
        } catch (Exception e) {
            System.err.println("Error adding media to cart: " + e.getMessage());
            sc.nextLine(); // consume invalid input
        }
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        
        System.out.println("=== AIMS Application with Exception Handling ===");
        System.out.println("This application demonstrates comprehensive exception handling for media playback.");
        System.out.println();
        
        do {
            showMenu();
            choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            switch (choice) {
                case 1 -> {
                    // View store
                    int storeChoice;
                    do {
                        storeMenu();
                        storeChoice = sc.nextInt();
                        sc.nextLine(); // consume newline
                        
                        switch (storeChoice) {
                            case 1 -> {
                                // See media details (simplified)
                                displayStore();
                            }
                            case 2 -> {
                                // Add media to cart
                                addMediaToCart(sc);
                            }
                            case 3 -> {
                                // Play a media - THIS IS WHERE EXCEPTION HANDLING IS DEMONSTRATED
                                playMediaFromStore(sc);
                            }
                            case 4 -> {
                                // See current cart
                                cart.printCart();
                            }
                            case 0 -> {
                                // Back to main menu
                            }
                            default -> System.out.println("Invalid choice. Please try again.");
                        }
                    } while (storeChoice != 0);
                }
                case 2 -> {
                    System.out.println("Store update functionality not implemented in this demo.");
                }
                case 3 -> {
                    // See current cart with play option
                    int cartChoice;
                    do {
                        cartMenu();
                        cartChoice = sc.nextInt();
                        sc.nextLine(); // consume newline
                        
                        switch (cartChoice) {
                            case 1, 2, 3, 5 -> {
                                System.out.println("Feature not implemented in this demo.");
                            }
                            case 4 -> {
                                // Play media from cart - EXCEPTION HANDLING DEMONSTRATION
                                playMediaFromCart(sc);
                            }
                            case 0 -> {
                                // Back to main menu
                            }
                            default -> System.out.println("Invalid choice. Please try again.");
                        }
                    } while (cartChoice != 0);
                }
                case 0 -> System.out.println("Exiting AIMS. Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        
        sc.close();
    }
}