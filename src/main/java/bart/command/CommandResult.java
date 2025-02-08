package bart.command;

/**
 * Represents the result of executing a command.
 * It contains a message and a result type that indicates whether the command was successful,
 * failed, or is an exit command.
 */
public class CommandResult {
    /**
     * Enum representing the possible outcomes of a command execution
     */
    public static enum ResultType {
        SUCCESS,
        FAILURE,
        EXIT,
    }
    private ResultType resultType;
    private String message;

    /**
     * Constructs a {@code CommandResult} with the specified result type and message.
     * @param resultType The type of result (SUCCESS, FAILURE, or EXIT).
     * @param message The message describing the result.
     */
    public CommandResult(ResultType resultType, String message) {
        this.resultType = resultType;
        this.message = message;
    }

    /**
     * Gets the result type of this command.
     *
     * @return The {@code ResultType} indicating success, failure, or exit.
     */
    public ResultType getResultType() {
        return resultType;
    }

    /**
     * Gets the message associated with this command result.
     *
     * @return A string containing the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Checks if the result type indicates a successful execution.
     *
     * @return {@code true} if the result type is {@code SUCCESS}, otherwise {@code false}.
     */
    public boolean isSuccess() {
        return resultType == ResultType.SUCCESS;
    }

    /**
     * Checks if the result type indicates that the program should exit.
     *
     * @return {@code true} if the result type is {@code EXIT}, otherwise {@code false}.
     */
    public boolean isExit() {
        return resultType == ResultType.EXIT;
    }
}
