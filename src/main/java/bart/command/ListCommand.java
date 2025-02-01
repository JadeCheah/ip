package bart.command;

import bart.util.Storage;
import bart.TaskList;
import bart.util.Ui;

public class ListCommand extends Command {
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

    @Override
    public boolean isExit() {
        return false;
    }
}
