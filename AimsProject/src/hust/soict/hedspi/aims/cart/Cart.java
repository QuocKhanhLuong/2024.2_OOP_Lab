package hust.soict.hedspi.aims.cart;

import hust.soict.hedspi.aims.media.Media;
import hust.soict.hedspi.aims.exception.CartFullException;
import hust.soict.hedspi.aims.exception.ItemNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cart {
    public static final int MAX_NUMBERS_ORDERED = 20;
    @SuppressWarnings("FieldMayBeFinal")
    private ObservableList<Media> itemsOrdered = FXCollections.observableArrayList();

    public ObservableList<Media> getItemsOrdered() {
        return itemsOrdered;
    }

    public void addMedia(Media media) throws CartFullException {
        if (media == null) {
            throw new IllegalArgumentException("ERROR: Media cannot be null!");
        }
        
        if (itemsOrdered.size() >= MAX_NUMBERS_ORDERED) {
            throw new CartFullException("ERROR: The cart is full. Cannot add \"" + media.getTitle() + "\"!");
        }
        
        itemsOrdered.add(media);
        System.out.println("The media \"" + media.getTitle() + "\" has been added.");
        if (itemsOrdered.size() == MAX_NUMBERS_ORDERED) {
            System.out.println("The cart is now full.");
        }
    }

    public void removeMedia(Media media) throws ItemNotFoundException {
        if (media == null) {
            throw new IllegalArgumentException("ERROR: Media cannot be null!");
        }
        
        if (!itemsOrdered.remove(media)) {
            throw new ItemNotFoundException("ERROR: The media \"" + media.getTitle() + "\" was not found in the cart!");
        }
        
        System.out.println("The media \"" + media.getTitle() + "\" has been removed.");
    }

    // Safe versions of add/remove methods for GUI use
    public boolean addMediaSafe(Media media) {
        try {
            addMedia(media);
            return true;
        } catch (CartFullException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean removeMediaSafe(Media media) {
        try {
            removeMedia(media);
            return true;
        } catch (ItemNotFoundException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public float totalCost() {
        float total = 0;
        for (Media media : itemsOrdered) {
            total += media.getCost();
        }
        return total;
    }

    // Method to print the cart contents
    @SuppressWarnings("UseSpecificCatch")
    public void printCart() {
        System.out.println("***********************CART***********************");
        System.out.println("Ordered Items:");
        for (int i = 0; i < itemsOrdered.size(); i++) {
            Media m = itemsOrdered.get(i);
            System.out.print((i + 1) + ". ");
            try {
                m.getClass().getMethod("printDetail").invoke(m);
            } catch (Exception e) {
                System.out.println(m.toString());
            }
        }
        System.out.println("Total cost: " + totalCost() + " $");
        System.out.println("***************************************************");
    }

    // Method to search for media by ID
    @SuppressWarnings("UseSpecificCatch")
    public void searchById(int id) {
        boolean found = false;
        System.out.println("***********************CART***********************");
        System.out.println("Search results for ID: " + id);

        for (int i = 0; i < itemsOrdered.size(); i++) {
            if (itemsOrdered.get(i).getId() == id) {
                Media m = itemsOrdered.get(i);
                System.out.print((i + 1) + ". ");
                try {
                    m.getClass().getMethod("printDetail").invoke(m);
                } catch (Exception e) {
                    System.out.println(m.toString());
                }
                found = true;
                break;  // Assuming ID is unique
            }
        }

        if (!found) {
            System.out.println("No match found for ID: " + id);
        }

        System.out.println("***************************************************");
    }

    // Method to search for media by title
    @SuppressWarnings("UseSpecificCatch")
    public void searchByTitle(String title) {
        boolean found = false;
        System.out.println("***********************CART***********************");
        System.out.println("Search results for title: " + title);

        for (int i = 0; i < itemsOrdered.size(); i++) {
            if (itemsOrdered.get(i).isMatch(title)) {
                Media m = itemsOrdered.get(i);
                System.out.print((i + 1) + ". ");
                try {
                    m.getClass().getMethod("printDetail").invoke(m);
                } catch (Exception e) {
                    System.out.println(m.toString());
                }
                found = true;
            }
        }

        if (!found) {
            System.out.println("No match found for title: " + title);
        }

        System.out.println("***************************************************");
    }
}

