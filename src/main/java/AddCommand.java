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
                        System.out.println(Ui.DIVIDER + " Error: Invalid date format. Please use the format yyyy-MM-dd for dates. \n" + Ui.DIVIDER);
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
                        System.out.println(Ui.DIVIDER + " Error: Invalid date format. Please use the format yyyy-MM-dd for dates. \n" + Ui.DIVIDER);
                        return;
                    }
                    break;
            }

            tasks.addTask(newTask);
            String output = Ui.DIVIDER +
                    " Noted! This task shall be remembered: \n   " +
                    newTask.toString() + "\n" +
                    " Thy list of labors now containeth " + tasks.countTasks() + " undertakings.\n" +
                    Ui.DIVIDER;
            System.out.println(output);

        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("No enum constant")) {
                System.out.println(Ui.DIVIDER +
                        " Apologies, I fear my understanding is lacking: " + taskTypeStr + " is not a valid task type.\n" +
                        " Please use one of the following: todo, deadline, event.\n" +
                        Ui.DIVIDER);
            } else {
                // Handle other IllegalArgumentExceptions (e.g., missing task details)
                System.out.println(Ui.DIVIDER + " Error: " + e.getMessage() + "\n" + Ui.DIVIDER);
            }
        } catch (Exception e) {
            System.out.println(Ui.DIVIDER + " Error: Something went wrong while adding the task.\n" + Ui.DIVIDER);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}