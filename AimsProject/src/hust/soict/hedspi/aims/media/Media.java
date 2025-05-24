package hust.soict.hedspi.aims.media;

import java.util.Comparator;

public abstract class Media implements Comparable<Media> {
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
            // Check for null objects
            if (m1 == null && m2 == null) return 0;
            if (m1 == null) return 1;
            if (m2 == null) return -1;
            
            // Check for null titles
            String title1 = m1.getTitle();
            String title2 = m2.getTitle();
            
            if (title1 == null && title2 == null) {
                return Float.compare(m2.getCost(), m1.getCost());
            }
            if (title1 == null) return 1;
            if (title2 == null) return -1;
            
            int titleCompare = title1.compareToIgnoreCase(title2);
            if (titleCompare != 0) return titleCompare;
            // If titles are the same, higher cost first
            return Float.compare(m2.getCost(), m1.getCost());
        }
    };

    @SuppressWarnings("Convert2Lambda")
    public static final Comparator<Media> COMPARE_BY_COST_TITLE = new Comparator<Media>() {
        @Override
        public int compare(Media m1, Media m2) {
            // Check for null objects
            if (m1 == null && m2 == null) return 0;
            if (m1 == null) return 1;
            if (m2 == null) return -1;
            
            int costCompare = Float.compare(m2.getCost(), m1.getCost()); // Descending cost
            if (costCompare != 0) return costCompare;
            
            // If costs are the same, sort by title alphabetically
            String title1 = m1.getTitle();
            String title2 = m2.getTitle();
            
            if (title1 == null && title2 == null) return 0;
            if (title1 == null) return 1;
            if (title2 == null) return -1;
            
            return title1.compareToIgnoreCase(title2);
        }
    };

    /**
     * Two medias are equal if they have the same title and cost
     * Uses instanceof operator to check object type and handle NullPointerException
     */
    @Override
    public boolean equals(Object obj) {
        // Check for same reference
        if (this == obj) return true;
        
        // Check for null and use instanceof to prevent ClassCastException
        if (obj == null || !(obj instanceof Media)) return false;
        
        Media other = (Media) obj;
        
        // Check title equality with null safety
        boolean titleEquals;
        if (this.title == null && other.title == null) {
            titleEquals = true;
        } else if (this.title == null || other.title == null) {
            titleEquals = false;
        } else {
            titleEquals = this.title.equals(other.title);
        }
        
        // Check cost equality (using Float.compare for precision)
        boolean costEquals = Float.compare(this.cost, other.cost) == 0;
        
        return titleEquals && costEquals;
    }

    /**
     * Hash code based on title and cost for consistency with equals()
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (title == null ? 0 : title.hashCode());
        result = 31 * result + Float.floatToIntBits(cost);
        return result;
    }

    /**
     * Implements Comparable interface for natural ordering
     * Compares by title first, then by cost
     */
    @Override
    public int compareTo(Media other) {
        // Check for null to prevent NullPointerException
        if (other == null) {
            throw new NullPointerException("Cannot compare to null Media object");
        }
        
        // Compare by title first
        String thisTitle = this.title;
        String otherTitle = other.title;
        
        if (thisTitle == null && otherTitle == null) {
            // If both titles are null, compare by cost
            return Float.compare(this.cost, other.cost);
        } else if (thisTitle == null) {
            // Null title comes after non-null title
            return 1;
        } else if (otherTitle == null) {
            // Non-null title comes before null title
            return -1;
        } else {
            // Both titles are non-null, compare them
            int titleComparison = thisTitle.compareToIgnoreCase(otherTitle);
            if (titleComparison != 0) {
                return titleComparison;
            }
            
            // If titles are equal, compare by cost
            return Float.compare(this.cost, other.cost);
        }
    }

    /**
     * Add isMatch for searching by title with null safety
     */
    public boolean isMatch(String title) {
        if (title == null) return false;
        return this.title != null && this.title.toLowerCase().contains(title.toLowerCase());
    }
    
    /**
     * Enhanced toString method for better display
     */
    @Override
    public String toString() {
        return String.format("%s - %s - %.2f $", 
            title != null ? title : "No Title", 
            category != null ? category : "No Category", 
            cost);
    }
}
