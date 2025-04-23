package hust.soict.hedspi.aims.media;

public class DigitalVideoDisc extends Disc implements Playable {
    private static int nbDigitalVideoDiscs = 0;

    public DigitalVideoDisc(String title) {
        super(++nbDigitalVideoDiscs, title, null, 0, null, 0);
    }

    public DigitalVideoDisc(String category, String title, float cost) {
        super(++nbDigitalVideoDiscs, title, category, cost, null, 0);
    }

    public DigitalVideoDisc(String director, String category, String title, float cost) {
        super(++nbDigitalVideoDiscs, title, category, cost, director, 0);
    }

    public DigitalVideoDisc(String title, String category, String director, int length, float cost) {
        super(++nbDigitalVideoDiscs, title, category, cost, director, length);
    }

    public void printDetail() {
        System.out.println("DVD - " + getTitle() + " - " + getCategory() + " - " + getDirector() + " - " + getLength() + ": " + getCost() + " $");
    }

    @Override
    public void play() {
        System.out.println("Playing DVD: " + this.getTitle());
        System.out.println("DVD length: " + this.getLength());
    }

    // Method to check if a disc matches a given title
    @Override
    public boolean isMatch(String title) {
        return this.getTitle().toLowerCase().contains(title.toLowerCase());
    }

    @SuppressWarnings("UseSpecificCatch")
    public void setTitle(String title) {
        // Allow setting the title
        // Use reflection or protected access if needed, but here we assume Media's title is protected or has a setter
        // If not, you may need to add a setTitle method in Media
        try {
            java.lang.reflect.Field field = Media.class.getDeclaredField("title");
            field.setAccessible(true);
            field.set(this, title);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Cannot set title: " + e.getMessage());
        }
    }
}

