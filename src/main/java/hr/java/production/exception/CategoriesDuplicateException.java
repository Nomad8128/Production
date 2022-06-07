package hr.java.production.exception;

/**
 * Exception for duplicate categories
 */
public class CategoriesDuplicateException extends RuntimeException{
    String message;
    Throwable cause;

    /**
     * Constructor for the exception with only message
     * @param message message
     */
    public CategoriesDuplicateException(String message){
        super(message);
    }

    /**
     * Constructor for the exception with only cause
     * @param cause cause
     */
    public CategoriesDuplicateException(Throwable cause){
        super(cause);
    }

    /**
     * Constructor for the exception with both message and cause
     * @param message message
     * @param cause cause
     */
    public CategoriesDuplicateException(String message, Throwable cause){
        super(message,cause);
    }
}
