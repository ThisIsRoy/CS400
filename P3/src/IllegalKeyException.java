/**
 * Checked exception thrown when a user attempts to insert or get a null key.
 */
@SuppressWarnings("serial")
public class IllegalKeyException extends Exception {
    /**
     * constructor that requires no arguments
     */
    public IllegalKeyException() {

    }

    /**
     * constructor that takes in an error message
     * @param message error message
     */
    public IllegalKeyException(String message) {
        System.out.println(message);
    }
}
