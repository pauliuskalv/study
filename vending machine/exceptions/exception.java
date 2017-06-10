package exceptions;

/**
 * Used as an exception for a specific scenario:
 * <p>
 * If changing an object's price the same price is
 * detected, throw this specific exception
 * </p>
 */
public class exception extends Exception {

    /**
     * Allocates new exception object
     * @param message
     * The message to be written, when this
     * specific exception is thrown
     */
    public exception(String message ) {
        super ( message ) ;
    }
}