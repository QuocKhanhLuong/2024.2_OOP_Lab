package hust.soict.hedspi.aims.exception;

public class CartFullException extends Exception {
    
    public CartFullException() {
        super("ERROR: The cart is full!");
    }
    
    public CartFullException(String message) {
        super(message);
    }
    
    public CartFullException(String message, Throwable cause) {
        super(message, cause);
    }
} 