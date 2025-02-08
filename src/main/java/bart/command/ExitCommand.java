package bart.command;

import bart.TaskList;
import bart.util.Storage;
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
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        return new CommandResult(CommandResult.ResultType.EXIT, Ui.EXIT_MESSAGE);
    }
}
