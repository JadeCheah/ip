package bart.command;

import bart.util.Storage;
import bart.TaskList;
import bart.util.Ui;

/**
 * Represents a command to find a task by searching for a keyword in the task description.
 */
public class ExitCommand extends Command {

    /**
     * Executes the find command, displaying tasks with description that fits the keyword
     *
     * @param tasks   The task list to find from
     * @param ui      The UI to interact with the user.
     * @param storage The storage (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showExit();
        return;
    }

    /**
     * Indicates whether this command is an exit command.
     *
     * @return false as this is not an exit command.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
