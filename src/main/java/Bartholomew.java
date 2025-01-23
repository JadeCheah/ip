import java.util.ArrayList;
import java.util.Scanner;


public class Bartholomew {
    private static final String DIVIDER = "____________________________________________________________\n";
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
            return;
        }
        String[] messageArray = message.split(" ", 2);
        String taskTypeStr = messageArray[0].toUpperCase();
        String taskDetails = (messageArray.length > 1) ? messageArray[1] : "";
        enum TaskType {
            TODO, DEADLINE, EVENT
        }
        TaskType type = TaskType.valueOf(messageArray[0].toUpperCase());
        Task newTask = null;
        switch (type) {
            case TODO :
                newTask = new Todo(taskDetails.trim());
                break;
            case DEADLINE :
                String[] deadlineParts = taskDetails.split("/by", 2);
                newTask = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                break;
            case EVENT :
                String[] eventParts = taskDetails.split("/from", 2);
                String[] timeParts = eventParts[1].split("/to", 2);
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
    }

    public static void listTasks() {
        System.out.println(DIVIDER + " Hark! These be thy duties: \n");
        for (int i = 0; i < tasks.size(); i++) {
            String output = " " + (i + 1) + "." + tasks.get(i).toString();
            System.out.println(output);
        }
        System.out.println(DIVIDER);
    }

    public static void markTask(String mark, int taskNumber) {
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
             String input = scanner.nextLine();
             String[] inputArray = input.split(" ", 2);
             if (inputArray[0].equals("mark") || inputArray[0].equals("unmark")) {
                 markTask(inputArray[0], Integer.parseInt(inputArray[1]));
             } else if (input.equalsIgnoreCase("bye")) {
                 exit();
                 break;
             } else if (input.equalsIgnoreCase("list")) {
                 listTasks();
             } else {
                 addTask(input);
             }
        }
        scanner.close();
    }
}
