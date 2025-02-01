package bart.command;

import bart.util.Storage;
import bart.TaskList;
import bart.util.Ui;

public abstract class Command {
    public abstract void execute(TaskList taskList, Ui ui, Storage storage);
    public abstract boolean isExit();
}
