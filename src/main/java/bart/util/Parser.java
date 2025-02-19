package bart.util;

import java.util.Arrays;

import bart.command.AddCommand;
import bart.command.Command;
import bart.command.DeleteCommand;
import bart.command.ExitCommand;
import bart.command.FindCommand;
import bart.command.ListCommand;
import bart.command.MarkCommand;
import bart.command.TagCommand;
import bart.exception.InvalidCommandException;

/**
 * The Parser class is responsible for parsing user input into commands.
 */
public class Parser {
    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param input The user input string.
     * @return The command corresponding to the user input.
     */
    public static Command parseCommand(String input) throws InvalidCommandException {
        String[] tokens = input.split(" ", 2);
        if (tokens[0].equals("mark") || tokens[0].equals("unmark")) {
            return createMarkCommand(tokens);
        } else if (tokens[0].equals("delete")) {
            return createDeleteCommand(tokens);
        } else if (tokens[0].equals("find")) {
            return new FindCommand(tokens[1].trim());
        } else if (tokens[0].equals("tag") || tokens[0].equals("untag")) {
            return createTagCommand(tokens[0].equals("tag"), tokens[1].trim());
        } else if (input.equalsIgnoreCase("bye")) {
            return new ExitCommand();
        } else if (input.equalsIgnoreCase("list")) {
            return new ListCommand();
        } else {
            return new AddCommand(input);
        }
    }

    /**
     * Creates a {@code MarkCommand} from the given input tokens.
     *
     * @param taskDetails The parsed input tokens.
     * @return A {@code MarkCommand} instance.
     * @throws InvalidCommandException If the format is incorrect or task number is missing.
     */
    public static MarkCommand createMarkCommand(String[] taskDetails) throws InvalidCommandException {
        boolean isMark = taskDetails[0].equals("mark");
        String taskNumStr = taskDetails.length > 1 ? taskDetails[1].trim() : "";

        if (taskNumStr.isEmpty() || !taskNumStr.matches("\\d+")) {
            throw new InvalidCommandException(Ui.INVALID_MARK_FORMAT);
        }

        int taskNumber = Integer.parseInt(taskNumStr);
        return new MarkCommand(isMark, taskNumber);
    }
    /**
     * Creates a {@code DeleteCommand} from the given input tokens.
     *
     * @param taskDetails The parsed input tokens.
     * @return A {@code DeleteCommand} instance.
     * @throws InvalidCommandException If the format is incorrect or task number is missing.
     */
    public static DeleteCommand createDeleteCommand(String[] taskDetails) throws InvalidCommandException {
        String taskNumStr = taskDetails.length > 1 ? taskDetails[1].trim() : "";

        if (taskNumStr.isEmpty() || !taskNumStr.matches("\\d+")) {
            throw new InvalidCommandException(Ui.INVALID_DELETE_FORMAT);
        }

        int taskNumber = Integer.parseInt(taskNumStr);
        return new DeleteCommand(taskNumber);
    }
    /**
     * Creates a {@code TagCommand} from the given input tokens.
     *
     * @param taskDetails The parsed input tokens.
     * @return A {@TagCommand} instance.
     * @throws InvalidCommandException If the format is incorrect or task number or tag is missing.
     */
    public static TagCommand createTagCommand(boolean isTag, String taskDetails) throws InvalidCommandException {
        String[] tokens = taskDetails.split(" ", 2);
        if (taskDetails.isBlank() || tokens.length < 2) {
            throw new InvalidCommandException("Invalid format! Use: `tag <taskNumber> #<tag1> #<tag2> ...`");
        }
        // Extract task number
        String taskNumStr = tokens[0].trim();
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(taskNumStr);
            if (taskNumber <= 0) {
                throw new InvalidCommandException("Task number must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Invalid task number: " + taskNumStr);
        }
        // Extract tags
        String tagDetails = tokens[1].trim();
        if (tagDetails.isBlank()) {
            throw new InvalidCommandException("Tag(s) cannot be empty.");
        }
        // Extract tags prefixed with '#'
        String[] tags = tagDetails.split(" #");
        tags[0] = tags[0].startsWith("#") ? tags[0] : "#" + tags[0]; // Ensure the first tag starts with '#'

        for (String tag : tags) {
            if (tag.isBlank() || !tag.startsWith("#")) {
                throw new InvalidCommandException("Each tag should start with '#'.");
            }
        }

        return new TagCommand(isTag, tags[0], taskNumber);
    }

    /**
     * Checks if the given command is invalid (null or empty).
     *
     * @param command The command string to validate.
     * @return {@code true} if the command is invalid, otherwise {@code false}.
     */
    public static boolean isEmptyCommand(String command) {
        return command == null || command.isBlank();
    }

    /**
     * Validates whether the given task details for a {@code Todo} task are valid.
     *
     * @param taskDetails The task description.
     * @return {@code true} if the task description is not blank, otherwise {@code false}.
     */
    public static boolean isValidTodo(String taskDetails) {
        return !taskDetails.isBlank();
    }
    /**
     * Validates whether the given task details for a {@code Deadline} task are correctly formatted.
     *
     * @param taskDetails The task description including the deadline.
     * @return {@code true} if the task description contains "/by" and a valid deadline, otherwise {@code false}.
     */
    public static boolean isValidDeadline(String taskDetails) {
        if (!taskDetails.contains("/by")) {
            return false;
        }
        String[] deadlineParts = taskDetails.split("/by", 2);
        if (deadlineParts.length < 2 || deadlineParts[1].isBlank()) {
            return false;
        }
        return true;
    }
    /**
     * Validates whether the given task details for an {@code Event} task are correctly formatted.
     *
     * @param taskDetails The task description including event start and end times.
     * @return {@code true} if the task description contains "/from" and "/to" with valid details, otherwise {@code false}.
     */
    public static boolean isValidEvent(String taskDetails) {
        if (!taskDetails.contains("/from") || !taskDetails.contains("/to")) {
            return false;
        }
        String[] eventParts = taskDetails.split("/from", 2);
        if (eventParts.length < 2 || eventParts[1].isBlank()) {
            return false;
        }
        String[] timeParts = eventParts[1].split("/to", 2);
        if (timeParts.length < 2 || timeParts[1].isBlank()) {
            return false;
        }
        return true;
    }
}
