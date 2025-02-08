package bart.command;

import bart.TaskList;
import bart.util.Storage;
import bart.util.Ui;

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    /**
     * Executes the list command, displaying all tasks in the task list.
     *
     * @param tasks   The task list to display.
     * @param ui      The UI to interact with the user.
     * @param storage The storage (not used in this command).
     */
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        String result = ui.getListTasksString(tasks.getTasks());
        return new CommandResult(CommandResult.ResultType.SUCCESS, result);
    }
}
