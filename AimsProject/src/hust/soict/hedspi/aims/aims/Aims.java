package hust.soict.hedspi.aims.aims;
import java.util.Scanner;

public class Aims {
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
        System.out.println("1. See a media’s details");
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

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
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
                            }
                            case 2 -> {
                            }
                            case 3 -> {
                            }
                            case 4 -> {
                            }
                            case 0 -> {
                            }
                            default -> System.out.println("Invalid choice. Please try again.");
                        }
                    
                                            } while (storeChoice != 0);
                }
                case 2 -> {
                }
                case 3 -> {
                }
                case 0 -> System.out.println("Exiting AIMS. Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
                    } while (choice != 0);
        sc.close();
    }

}