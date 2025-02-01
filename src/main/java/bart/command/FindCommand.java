package bart.command;

import java.util.ArrayList;

import bart.TaskList;
import bart.util.Ui;
import bart.util.Storage;
import bart.task.Task;
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
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> filteredTasks = tasks.findTask(keyword);
        ui.printTasks(filteredTasks);
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
