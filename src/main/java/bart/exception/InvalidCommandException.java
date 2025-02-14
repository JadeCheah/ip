package bart.exception;
/**
 * Exception thrown when an invalid command is entered.
 */
public class InvalidCommandException extends Exception {
    public InvalidCommandException(String message) {
        super(message);
    }
}
