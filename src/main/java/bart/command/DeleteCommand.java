package bart.command;

import bart.util.Storage;
import bart.task.Task;
import bart.TaskList;
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
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.tasks.isEmpty()) {
            ui.printMessage("Thy list is empty, noble one!");
            return;
        }
        try {
            Task t = tasks.deleteTask(taskNumber);
            ui.printDeletedTask(t, tasks.countTasks());
        } catch (NumberFormatException e) {
            ui.printError("bart.task.Task number must be a valid integer.");
        } catch (IndexOutOfBoundsException e) {
            ui.printError("bart.task.Task number number is out of range: " + e.getMessage());
        } finally {
            storage.saveTasks(tasks);
        }
    }

    /**
     * Indicates whether this command is an exit command.
     *
     * @return false as this is not an exit command.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
