package bart.command;

import bart.util.Storage;
import bart.TaskList;
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
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.tasks.isEmpty()) {
            ui.printMessage("Thy list is empty, noble one!");
            return;
        }
        ui.listHeader();
        tasks.listTasks();
        ui.printDivider();
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
