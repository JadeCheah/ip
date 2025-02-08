package bart.command;

import bart.TaskList;
import bart.task.Task;
import bart.util.Storage;
import bart.util.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private int taskNumber;

    /**
     * Constructs a DeleteCommand with the specified task number.
     *
     * @param taskNumber The task number to delete.
     */
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the delete command, removing a task from the task list, and saves the tasks to storage
     * automatically.
     *
     * @param tasks   The task list to delete the task from.
     * @param ui      The UI to interact with the user.
     * @param storage The storage to save the tasks.
     */
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    "Thy list is empty, noble one!");
        }
        try {
            Task t = tasks.deleteTask(taskNumber);
            String result = ui.getDeletedTaskString(t, tasks.countTasks());
            return new CommandResult(CommandResult.ResultType.SUCCESS, result);
        } catch (NumberFormatException e) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    "Task number must be a valid integer.");
        } catch (IndexOutOfBoundsException e) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    "Task number number is out of range: ");
        } finally {
            storage.saveTasks(tasks);
        }
    }

}
