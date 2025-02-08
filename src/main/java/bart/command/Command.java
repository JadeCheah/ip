package bart.command;

import bart.TaskList;
import bart.util.Storage;
import bart.util.Ui;


/**
 * Represents an abstract command that can be executed.
 */
public abstract class Command {
    /**
     * Executes the command.
     *
     * @param taskList The task list to operate on.
     * @param ui       The UI to interact with the user.
     * @param storage  The storage to save or load tasks.
     * @return result of CommandType
     */
    public abstract CommandResult execute(TaskList taskList, Ui ui, Storage storage);
}
