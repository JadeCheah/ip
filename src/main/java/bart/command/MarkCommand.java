package bart.command;

import bart.TaskList;
import bart.task.Task;
import bart.util.Storage;
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
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    Ui.EMPTY_LIST_MESSAGE);
        }
        try {
            Task t = tasks.getTask(taskNumber);
            t.markAsDone(isMark);
            String result = ui.getMarkTaskString(isMark, t.toString());
            return new CommandResult(CommandResult.ResultType.SUCCESS, result);
        } catch (NumberFormatException e) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    Ui.INVALID_TASK_NUMBER);
        } catch (IndexOutOfBoundsException e) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    Ui.TASK_NUMBER_OUT_OF_RANGE);
        } catch (Exception e) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    "Error: Unable to mark the task.");
        } finally {
            storage.saveTasks(tasks);
        }

    }
}
