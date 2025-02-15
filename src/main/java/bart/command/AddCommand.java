package bart.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import bart.TaskList;
import bart.exception.InvalidCommandException;
import bart.task.Deadline;
import bart.task.Event;
import bart.task.Task;
import bart.task.Todo;
import bart.util.Parser;
import bart.util.Storage;
import bart.util.Ui;
/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {
    private String fullCommand;

    /**
     * Constructs an AddCommand with the specified full command string.
     *
     * @param fullCommand The full command string.
     */
    public AddCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Enum representing the different task types that can be added.
     */
    private enum TaskType {
        TODO, DEADLINE, EVENT
    }

    /**
     * Executes the add command, creating a new task and adding it to the task list.
     * The new task is automatically saved in storage.
     *
     * @param tasks   The task list to which the new task will be added.
     * @param ui      The UI handler for displaying messages.
     * @param storage The storage handler for saving the updated task list.
     * @return A {@code CommandResult} indicating success or failure.
     */
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (Parser.isEmptyCommand(fullCommand)) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    "A description is required!");
        }
        try {
            Task newTask = createTask(fullCommand);
            // Extract and add tags if present
            extractAndAddTags(newTask, fullCommand);
            tasks.addTask(newTask);
            String result = ui.getAddTaskString(newTask, tasks.countTasks());
            return new CommandResult(CommandResult.ResultType.SUCCESS, result);
        } catch (InvalidCommandException e) {
            return new CommandResult(CommandResult.ResultType.FAILURE, e.getMessage());
        } catch (Exception e) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    "Error: Something went wrong while adding the task.");
        }
    }

    /**
     * Parses the command string and creates the corresponding task.
     *
     * @param fullCommand The command string to be parsed.
     * @return The created {@code Task}.
     * @throws InvalidCommandException If the command format is invalid.
     */
    private Task createTask(String fullCommand) throws InvalidCommandException {
        try {
            String[] messageArray = fullCommand.split(" ", 2);
            String taskTypeStr = messageArray[0].toUpperCase();
            String taskDetails = (messageArray.length > 1) ? messageArray[1] : "";
            // Remove everything after the first '#'
            taskDetails = taskDetails.split("#", 2)[0].trim();

            return switch (TaskType.valueOf(taskTypeStr)) {
            case TODO -> createTodo(taskDetails);
            case DEADLINE -> createDeadline(taskDetails);
            case EVENT -> createEvent(taskDetails);
            };
        } catch (IllegalArgumentException e) { // Handles unknown task types
            throw new InvalidCommandException(Ui.INVALID_COMMAND);
        }
    }

    /**
     * Creates a {@code Todo} task.
     *
     * @param taskDetails The description of the task.
     * @return The created {@code Todo} task.
     * @throws InvalidCommandException If the description is missing.
     */
    private Todo createTodo(String taskDetails) throws InvalidCommandException {
        if (!Parser.isValidTodo(taskDetails)) {
            throw new InvalidCommandException(Ui.INVALID_TODO_FORMAT);
        }
        return new Todo(taskDetails.trim());
    }

    /**
     * Creates a {@code Deadline} task.
     *
     * @param taskDetails The description and deadline date of the task.
     * @return The created {@code Deadline} task.
     * @throws InvalidCommandException If the format is incorrect or the date is invalid.
     */
    private Deadline createDeadline(String taskDetails) throws InvalidCommandException {
        if (!Parser.isValidDeadline(taskDetails)) {
            throw new InvalidCommandException(Ui.INVALID_DEADLINE_FORMAT);
        }
        String[] deadlineParts = taskDetails.split("/by", 2);
        // Parse the date and handle invalid format
        try {
            LocalDate byDate = LocalDate.parse(deadlineParts[1].trim());
            return new Deadline(deadlineParts[0].trim(), byDate);
        } catch (DateTimeParseException e) {
            throw new InvalidCommandException(Ui.INVALID_DATE_FORMAT);
        }
    }

    /**
     * Creates an {@code Event} task.
     *
     * @param taskDetails The description, start date, and end date of the event.
     * @return The created {@code Event} task.
     * @throws InvalidCommandException If the format is incorrect or dates are invalid.
     */
    private Event createEvent(String taskDetails) throws InvalidCommandException {
        if (!Parser.isValidEvent(taskDetails)) {
            throw new InvalidCommandException(Ui.INVALID_EVENT_FORMAT);
        }
        String[] eventParts = taskDetails.split("/from", 2);
        String[] timeParts = eventParts[1].split("/to", 2);
        // Parse the dates and handle invalid format
        try {
            LocalDate fromDate = LocalDate.parse(timeParts[0].trim());
            LocalDate toDate = LocalDate.parse(timeParts[1].trim());
            return new Event(eventParts[0].trim(), fromDate, toDate);
        } catch (DateTimeParseException e) {
            throw new InvalidCommandException(Ui.INVALID_DATE_FORMAT);
        }
    }

    /**
     * Extracts tags from the command and adds them to the given task.
     *
     * @param task The task to which tags will be added.
     * @param fullCommand The full command from user input to be parsed.
     */
    private void extractAndAddTags(Task task, String fullCommand) {
        String[] words = fullCommand.split(" ");
        for (String word : words) {
            if (word.startsWith("#")) {
                task.addTag(word.substring(1)); // Remove '#' and add the tag
            }
        }
    }
}


