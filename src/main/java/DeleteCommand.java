public class DeleteCommand extends Command {
    private int taskNumber;

    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.tasks.isEmpty()) {
            ui.printMessage("Thy list is empty, noble one!");
            return;
        }
        try {
            Task t = tasks.deleteTask(taskNumber);
            ui.printDeletedTask(t, tasks.countTasks());
        } catch (NumberFormatException e) {
            ui.printError("Task number must be a valid integer.");
        } catch (IndexOutOfBoundsException e) {
            ui.printError("Task number number is out of range: " + e.getMessage());
        } finally {
            storage.saveTasks(tasks);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
