package hust.soict.hedspi.aims.cart;

import hust.soict.hedspi.aims.media.Media;
import java.util.ArrayList;

public class Cart {
    public static final int MAX_NUMBERS_ORDERED = 20;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Media> itemsOrdered = new ArrayList<>();

    public void addMedia(Media media) {
        if (itemsOrdered.size() < MAX_NUMBERS_ORDERED) {
            itemsOrdered.add(media);
            System.out.println("The media \"" + media.getTitle() + "\" has been added.");
            if (itemsOrdered.size() == MAX_NUMBERS_ORDERED) {
                System.out.println("The cart is almost full.");
            }
        } else {
            System.out.println("Cannot add. The cart is full!");
        }
    }

    public void removeMedia(Media media) {
        if (itemsOrdered.remove(media)) {
            System.out.println("The media \"" + media.getTitle() + "\" has been removed.");
        } else {
            System.out.println("The media \"" + media.getTitle() + "\" was not found in the cart.");
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

