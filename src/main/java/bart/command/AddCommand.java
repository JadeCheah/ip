package bart.command;

import bart.*;
import bart.task.Deadline;
import bart.task.Event;
import bart.task.Task;
import bart.task.Todo;
import bart.util.Storage;
import bart.util.Ui;

import java.time.LocalDate;

public class AddCommand extends Command {
    String fullCommand;

    public AddCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (fullCommand == null || fullCommand.isBlank()) {
            ui.printMessage("A description is required!");
            return;
        }

        String[] messageArray = fullCommand.split(" ", 2);
        String taskTypeStr = messageArray[0].toUpperCase();
        String taskDetails = (messageArray.length > 1) ? messageArray[1] : "";

        enum TaskType {
            TODO, DEADLINE, EVENT
        }
        Task newTask = null;
        try {
            TaskType type = TaskType.valueOf(taskTypeStr);
            switch (type) {
                case TODO:
                    if (taskDetails.isBlank()) {
                        throw new IllegalArgumentException("'todo' task requires a description.");
                    }
                    newTask = new Todo(taskDetails.trim());
                    break;
                case DEADLINE:
                    if (!taskDetails.contains("/by")) {
                        throw new IllegalArgumentException("'deadline' task must include '/by' followed by a date.");
                    }
                    String[] deadlineParts = taskDetails.split("/by", 2);
                    if (deadlineParts.length < 2 || deadlineParts[1].isBlank()) {
                        throw new IllegalArgumentException("'deadline' task must specify a valid date after '/by'.");
                    }

                    // Parse the date and handle invalid format
                    try {
                        LocalDate byDate = LocalDate.parse(deadlineParts[1].trim());
                        newTask = new Deadline(deadlineParts[0].trim(), byDate);
                    } catch (Exception e) {
                        ui.printError("Invalid date format. Please use the format yyyy-MM-dd for dates.");
                        return;
                    }
                    break;
                case EVENT:
                    if (!taskDetails.contains("/from") || !taskDetails.contains("/to")) {
                        throw new IllegalArgumentException("'event' task must include '/from' and '/to' with times.");
                    }
                    String[] eventParts = taskDetails.split("/from", 2);
                    if (eventParts.length < 2 || eventParts[1].isBlank()) {
                        throw new IllegalArgumentException("'event' task must specify a valid time after '/from'.");
                    }
                    String[] timeParts = eventParts[1].split("/to", 2);
                    if (timeParts.length < 2 || timeParts[1].isBlank()) {
                        throw new IllegalArgumentException("'event' task must specify a valid time after '/to'.");
                    }

                    // Parse the dates and handle invalid format
                    try {
                        LocalDate fromDate = LocalDate.parse(timeParts[0].trim());
                        LocalDate toDate = LocalDate.parse(timeParts[1].trim());
                        newTask = new Event(eventParts[0].trim(), fromDate, toDate);
                    } catch (Exception e) {
                        ui.printError("Invalid date format. Please use the format yyyy-MM-dd for dates.");
                        return;
                    }
                    break;
            }

            tasks.addTask(newTask);
            ui.showAddTask(newTask, tasks.countTasks());

        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("No enum constant")) {
                ui.printError(
                        "Apologies, I fear my understanding is lacking: " + taskTypeStr + " is not a valid task type.\n" +
                        " Please user one of the following: todo, deadline, event.");
            } else {
                // Handle other IllegalArgumentExceptions (e.g., missing task details)
                ui.printError("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            ui.printError("Error: Something went wrong while adding the task.");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}