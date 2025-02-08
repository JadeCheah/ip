package bart.command;

import java.time.LocalDate;

import bart.TaskList;
import bart.task.Deadline;
import bart.task.Event;
import bart.task.Task;
import bart.task.Todo;
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
     * Executes the add command, adding a task to the task list, and saves the tasks to the storage
     * automatically.
     *
     * @param tasks   The task list to add the task to.
     * @param ui      The UI to interact with the user.
     * @param storage The storage to save the tasks.
     */
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (fullCommand == null || fullCommand.isBlank()) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    "A description is required!");
        }

        String[] messageArray = fullCommand.split(" ", 2);
        String taskTypeStr = messageArray[0].toUpperCase();
        String taskDetails = (messageArray.length > 1) ? messageArray[1] : "";

        enum TaskType {
            TODO, DEADLINE, EVENT
        }
        Task newTask = null;

        try {
            TaskType type = TaskType.valueOf(taskTypeStr);
            switch (type) {
            case TODO:
                if (taskDetails.isBlank()) {
                    return new CommandResult(CommandResult.ResultType.FAILURE,
                            "'todo' task requires a description.");
                }
                newTask = new Todo(taskDetails.trim());
                break;
            case DEADLINE:
                if (!taskDetails.contains("/by")) {
                    return new CommandResult(CommandResult.ResultType.FAILURE, Ui.INVALID_COMMAND);
                }
                String[] deadlineParts = taskDetails.split("/by", 2);
                if (deadlineParts.length < 2 || deadlineParts[1].isBlank()) {
                    return new CommandResult(CommandResult.ResultType.FAILURE, Ui.INVALID_COMMAND);
                }

                // Parse the date and handle invalid format
                try {
                    LocalDate byDate = LocalDate.parse(deadlineParts[1].trim());
                    newTask = new Deadline(deadlineParts[0].trim(), byDate);
                } catch (Exception e) {
                    return new CommandResult(CommandResult.ResultType.FAILURE, Ui.INVALID_DATE_FORMAT);
                }
                break;
            case EVENT:
                if (!taskDetails.contains("/from") || !taskDetails.contains("/to")) {
                    return new CommandResult(CommandResult.ResultType.FAILURE, Ui.INVALID_COMMAND);
                }
                String[] eventParts = taskDetails.split("/from", 2);
                if (eventParts.length < 2 || eventParts[1].isBlank()) {
                    return new CommandResult(CommandResult.ResultType.FAILURE, Ui.INVALID_COMMAND);
                }
                String[] timeParts = eventParts[1].split("/to", 2);
                if (timeParts.length < 2 || timeParts[1].isBlank()) {
                    return new CommandResult(CommandResult.ResultType.FAILURE, Ui.INVALID_COMMAND);
                }

                // Parse the dates and handle invalid format
                try {
                    LocalDate fromDate = LocalDate.parse(timeParts[0].trim());
                    LocalDate toDate = LocalDate.parse(timeParts[1].trim());
                    newTask = new Event(eventParts[0].trim(), fromDate, toDate);
                } catch (Exception e) {
                    return new CommandResult(CommandResult.ResultType.FAILURE, Ui.INVALID_DATE_FORMAT);
                }
                break;
            default:
                return new CommandResult(CommandResult.ResultType.FAILURE, Ui.INVALID_COMMAND);
            }

            tasks.addTask(newTask);
            String result = ui.getAddTaskString(newTask, tasks.countTasks());
            return new CommandResult(CommandResult.ResultType.SUCCESS, result);

        } catch (IllegalArgumentException e) {
            return new CommandResult(CommandResult.ResultType.FAILURE, Ui.INVALID_COMMAND);
        } catch (Exception e) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    "Error: Something went wrong while adding the task.");
        }
    }
}
