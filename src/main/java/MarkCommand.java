public class MarkCommand extends Command {
    private boolean isMark;
    private int taskNumber;

    public MarkCommand(boolean isMark, int taskNumber) {
        this.isMark = isMark;
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.tasks.isEmpty()) {
            ui.showListEmpty();
            return;
        }
        try {
            Task t = tasks.getTask(taskNumber);
            String output;
            if (isMark) {
                t.markAsDone(true);
                output = "Done and dusted! This chore is no more: \n   " + t.toString();
            } else {
                t.markAsDone(false);
                output = "Alas, this task remains unfinished: \n   " + t.toString();
            }
            ui.printMessage(output);
        } catch (NumberFormatException e) {
            ui.printError("Error: Task number must be a valid integer.");
        } catch (IndexOutOfBoundsException e) {
            ui.printError("Task number is out of range: " + e.getMessage());
        } catch (Exception e) {
            ui.printError("Error: Unable to mark the task.");
        } finally {
            storage.saveTasks(tasks);
        }

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
