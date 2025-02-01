package bart.command;

import bart.util.Storage;
import bart.TaskList;
import bart.util.Ui;

public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showExit();
        return;
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
