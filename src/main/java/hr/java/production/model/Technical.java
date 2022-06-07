package hr.java.production.model;

/**
 * Sealed interface which has a method that returns the length of warranty
 */
public sealed interface Technical permits Laptop {
    /**
     * Method that returns the warranty
     * @return returns the length of the warranty
     */
    public int garancija();
}
