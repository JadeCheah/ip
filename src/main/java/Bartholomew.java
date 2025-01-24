import java.util.ArrayList;
import java.util.Scanner;


public class Bartholomew {
    private static final String DIVIDER = "__________________________________________________________________________\n";
    private static ArrayList<Task> tasks;

    public static void greetUser() {
        String greeting = DIVIDER +
                " Greetings! I am Bartholomew.\n" +
                " What service may I offer thee this day?\n" +
                DIVIDER;
        System.out.println(greeting);
    }

    public static void addTask(String message) {
        if (message == null || message.isBlank()) {
            System.out.println(DIVIDER + "A description is required! \n" + DIVIDER);
            return;
        }

        String[] messageArray = message.split(" ", 2);
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
                    newTask = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
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

                    newTask = new Event(eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
                    break;
            }

            tasks.add(newTask);
            String output = DIVIDER +
                    " Noted! This task shall be remembered: \n   " +
                    newTask.toString() + "\n" +
                    " Thy list of labors now containeth " + countTasks() + " undertakings.\n" +
                    DIVIDER;
            System.out.println(output);

        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("No enum constant")) {
                System.out.println(DIVIDER +
                        " Apologies, I fear my understanding is lacking: " + taskTypeStr + " is not a valid task type.\n" +
                        " Please use one of the following: todo, deadline, event.\n" +
                        DIVIDER);
            } else {
                // Handle other IllegalArgumentExceptions (e.g., missing task details)
                System.out.println(DIVIDER + " Error: " + e.getMessage() + "\n" + DIVIDER);
            }
        } catch (Exception e) {
            System.out.println(DIVIDER + " Error: Something went wrong while adding the task.\n" + DIVIDER);
        }
    }

    public static void markTask(String mark, String taskString) {
        if (tasks.isEmpty()) {
            System.out.println(DIVIDER + " Thy list is empty, noble one!\n" + DIVIDER);
            return;
        }
        try {
            int taskNumber = Integer.parseInt(taskString);
            Task t = tasks.get(taskNumber - 1);
            String output;
            if (mark.equals("mark")) {
                t.markAsDone(true);
                output = DIVIDER + " Done and dusted! This chore is no more: \n   " +
                        t.toString() + "\n" + DIVIDER;
            } else {
                t.markAsDone(false);
                output = DIVIDER + " Alas, this task remains unfinished: \n   " +
                        t.toString() + "\n" + DIVIDER;
            }
            System.out.println(output);

        } catch (NumberFormatException e) {
            System.out.println(DIVIDER + " Error: Task number must be a valid integer.\n" + DIVIDER);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(DIVIDER + " Task number is out of range: " + e.getMessage() + "\n" + DIVIDER);
        } catch (Exception e) {
            System.out.println(DIVIDER + " Error: Unable to mark the task.\n" + DIVIDER);
        }
    }

    public static void deleteTask(String taskString) {
        if (tasks.isEmpty()) {
            System.out.println(DIVIDER + " Thy list is empty, noble one!\n" + DIVIDER);
            return;
        }
        try {
            int taskNumber = Integer.parseInt(taskString);
            Task t = tasks.get(taskNumber - 1);
            tasks.remove(taskNumber - 1);
            String output = DIVIDER +
                    "This task is vanquished permanently! It shall trouble thee no further:\n   " +
                    t.toString() + "\n" +
                    " Thy list of labors now containeth " + countTasks() + " undertakings.\n" +
                    DIVIDER;
            System.out.println(output);
        } catch (NumberFormatException e) {
            System.out.println(DIVIDER + " Task number must be a valid integer.\n" + DIVIDER);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(DIVIDER + " Task number is out of range: " + e.getMessage() + "\n" + DIVIDER);
        }
    }

    public static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println(DIVIDER + " Thy list is empty, noble one!\n" + DIVIDER);
            return;
        }
        System.out.println(DIVIDER + " Hark! These be thy duties: \n");
        for (int i = 0; i < tasks.size(); i++) {
            String output = " " + (i + 1) + "." + tasks.get(i).toString();
            System.out.println(output);
        }
        System.out.println(DIVIDER);
    }

    public static int countTasks() {
        return tasks.size();
    }

    public static void exit() {
        String exitMessage = DIVIDER + " Farewell! May the winds of fate bring us together again.\n" + DIVIDER;
        System.out.println(exitMessage);
    }

    public static void main(String[] args) {
        greetUser();
        tasks = new ArrayList<Task>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String input = scanner.nextLine();
                String[] inputArray = input.split(" ", 2);
                if (inputArray[0].equals("mark") || inputArray[0].equals("unmark")) {
                    markTask(inputArray[0], inputArray[1]);
                } else if (inputArray[0].equals("delete")) {
                    deleteTask(inputArray[1].trim());
                } else if (input.equalsIgnoreCase("bye")) {
                    exit();
                    break;
                } else if (input.equalsIgnoreCase("list")) {
                    listTasks();
                } else {
                    addTask(input);
                }
            } catch (Exception e) {
                System.out.println(DIVIDER + " Error: Invalid command. Please try again.\n" + DIVIDER);
            }
        }
        scanner.close();
    }
}
