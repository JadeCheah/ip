package bart.command;

import java.util.ArrayList;

import bart.TaskList;
import bart.task.Task;
import bart.util.Storage;
import bart.util.Ui;

/**
 * Represents a command to find a task by searching for a keyword in the task description.
 */
public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command, displaying tasks with description that fits the keyword
     *
     * @param tasks   The task list to find from
     * @param ui      The UI to interact with the user.
     * @param storage The storage (not used in this command).
     */
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            ArrayList<Task> filteredTasks = tasks.findTasks(keyword);
            String result = ui.getFindTasksString(filteredTasks);
            return new CommandResult(CommandResult.ResultType.SUCCESS, result);
        } catch (IllegalArgumentException e) {
            return new CommandResult(CommandResult.ResultType.FAILURE, e.getMessage());
        }
    }
}
