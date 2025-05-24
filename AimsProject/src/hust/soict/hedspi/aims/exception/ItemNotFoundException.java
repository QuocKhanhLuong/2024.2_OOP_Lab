package hust.soict.hedspi.aims.exception;

public class ItemNotFoundException extends Exception {
    
    public ItemNotFoundException() {
        super("ERROR: Item not found!");
    }
    
    public ItemNotFoundException(String message) {
        super(message);
    }
    
    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 