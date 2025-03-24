public class DigitalVideoDisc {
    private String title;
    public String getTitle() {
        return title;
    }

    private String category;
    public String getCategory() {
        return category;
    }

    private String director;

    public String getDirector() {
        return director;
    }

    private int length;
    public int getLength() {
        return length;
    }

    private float cost;
    public float getCost() {
        return cost;
    }

    public DigitalVideoDisc(String title) {
        this.title = title;
    }
    public DigitalVideoDisc(String category, String title, float cost) {
        this.category = category;
        this.title = title;
        this.cost = cost;
    }
    public DigitalVideoDisc(String director, String category, String title, float cost) {
        this.director = director;
        this.category = category;
        this.title = title;
        this.cost = cost;
    }
    public DigitalVideoDisc(String title, String category, String director, int length, float cost) {
        this.title = title;
        this.category = category;
        this.director = director;
        this.length = length;
        this.cost = cost;
    }
}
