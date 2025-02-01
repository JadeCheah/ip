package bart.command;

import java.util.ArrayList;

import bart.TaskList;
import bart.util.Ui;
import bart.util.Storage;
import bart.task.Task;

public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> filteredTasks = tasks.findTask(keyword);
        ui.printTasks(filteredTasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
