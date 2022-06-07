package hr.java.production.exception;

/**
 * Exception for duplicate articles
 */
public class ArtiklDuplicateException extends Exception{
    /**
     * Constructor for the exception with only message
     * @param message message
     */
    public ArtiklDuplicateException(String message) {
        super(message);
    }

    /**
     * Constructor for the exception with only cause
     * @param uzrok cause
     */
    public ArtiklDuplicateException(Throwable uzrok) {
        super(uzrok);
    }

    /**
     * Constructor for the exception with message and cause
     * @param poruka message
     * @param uzrok cause
     */
    public ArtiklDuplicateException(String poruka, Throwable uzrok) {
        super(poruka, uzrok);
    }

}
