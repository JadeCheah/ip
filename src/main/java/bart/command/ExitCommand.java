package bart.command;

import bart.util.Storage;
import bart.TaskList;
import bart.util.Ui;

/**
 * Represents a command to exit the application.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command, showing the exit message.
     *
     * @param tasks   The task list (not used in this command).
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
     * @return true as this is an exit command.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
