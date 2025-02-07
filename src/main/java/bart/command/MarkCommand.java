package bart.command;

import bart.util.Storage;
import bart.task.Task;
import bart.TaskList;
import bart.util.Ui;

/**
 * Represents a command to mark or unmark a task in the task list.
 */
public class MarkCommand extends Command {
    private boolean isMark;
    private int taskNumber;

    /**
     * Constructs a MarkCommand with the specified mark status and task number.
     *
     * @param isMark     True to mark the task as done, false to unmark it.
     * @param taskNumber The task number to mark or unmark.
     */
    public MarkCommand(boolean isMark, int taskNumber) {
        this.isMark = isMark;
        this.taskNumber = taskNumber;
    }

     /**
     * Executes the mark command, marking or unmarking a task in the task list.
     *
     * @param tasks   The task list containing the task to mark or unmark.
     * @param ui      The UI to interact with the user.
     * @param storage The storage to save the tasks.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            ui.showListEmpty();
            return;
        }
        try {
            Task t = tasks.getTask(taskNumber);
            String output;
            if (isMark) {
                t.markAsDone(true);
                output = "Done and dusted! This chore is no more: \n   " + t.toString();
            } else {
                t.markAsDone(false);
                output = "Alas, this task remains unfinished: \n   " + t.toString();
            }
            ui.printMessage(output);
        } catch (NumberFormatException e) {
            ui.printError("Error: bart.task.Task number must be a valid integer.");
        } catch (IndexOutOfBoundsException e) {
            ui.printError("bart.task.Task number is out of range: " + e.getMessage());
        } catch (Exception e) {
            ui.printError("Error: Unable to mark the task.");
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
