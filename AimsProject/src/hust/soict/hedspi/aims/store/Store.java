package hust.soict.hedspi.aims.store;

import hust.soict.hedspi.aims.media.Media;
import java.util.ArrayList;

public class Store {
    private static final int MAX_ITEMS = 100;

    public ArrayList<Media> getItemsInStore() {
        return itemsInStore;
    }

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Media> itemsInStore = new ArrayList<>();

    public void addMedia(Media media) {
        if (itemsInStore.size() < MAX_ITEMS) {
            itemsInStore.add(media);
            System.out.println("Media \"" + media.getTitle() + "\" has been added to the store.");
        } else {
            System.out.println("The store is full. Cannot add more items.");
        }
    }

    public void addMedia(Media[] mediaList) {
        for (Media media : mediaList) {
            addMedia(media);
        }
    }

    public void removeMedia(Media media) {
        if (itemsInStore.remove(media)) {
            System.out.println("Media \"" + media.getTitle() + "\" has been removed from the store.");
        } else {
            System.out.println("Media \"" + media.getTitle() + "\" was not found in the store.");
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    public void printStore() {
        System.out.println("***********************STORE***********************");
        System.out.println("Items in store:");
        for (int i = 0; i < itemsInStore.size(); i++) {
            Media m = itemsInStore.get(i);
            System.out.print((i + 1) + ". ");
            try {
                m.getClass().getMethod("printDetail").invoke(m);
            } catch (Exception e) {
                System.out.println(m.toString());
            }
        }
        System.out.println("***************************************************");
    }

    public int getItemCount() {
        return itemsInStore.size();
    }


}