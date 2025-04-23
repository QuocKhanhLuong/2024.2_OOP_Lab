package hust.soict.hedspi.aims.media;

import java.util.Comparator;

public abstract class Media {
    private int id;
    private String title;
    private String category;
    private float cost;

    public Media() {
        super();
    }

    public Media(int id, String title, String category, float cost) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getCost() {
        return cost;
    }

    @SuppressWarnings("Convert2Lambda")
    public static final Comparator<Media> COMPARE_BY_TITLE_COST = new Comparator<Media>() {
        @Override
        public int compare(Media m1, Media m2) {
            int titleCompare = m1.getTitle().compareToIgnoreCase(m2.getTitle());
            if (titleCompare != 0) return titleCompare;
            // If titles are the same, higher cost first
            return Float.compare(m2.getCost(), m1.getCost());
        }
    };

    @SuppressWarnings("Convert2Lambda")
    public static final Comparator<Media> COMPARE_BY_COST_TITLE = new Comparator<Media>() {
        @Override
        public int compare(Media m1, Media m2) {
            int costCompare = Float.compare(m2.getCost(), m1.getCost()); // Descending cost
            if (costCompare != 0) return costCompare;
            // If costs are the same, sort by title alphabetically
            return m1.getTitle().compareToIgnoreCase(m2.getTitle());
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Media)) return false;
        Media other = (Media) obj;
        if (this.title == null && other.title == null) return true;
        if (this.title == null || other.title == null) return false;
        return this.title.equals(other.title);
    }

    @Override
    public int hashCode() {
        return (title == null) ? 0 : title.hashCode();
    }

    // Add isMatch for searching by title
    public boolean isMatch(String title) {
        return this.title != null && this.title.toLowerCase().contains(title.toLowerCase());
    }
}
