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
        Task newTask = new Task(message);
        tasks.add(newTask);
        String echo = DIVIDER + " added: " + message + "\n" + DIVIDER;
        System.out.println(echo);
    }

    public static void listTasks() {
        System.out.println(DIVIDER + " Hark! These be thy duties: \n");
        for (int i = 0; i < tasks.size(); i++) {
            String output = " " + (i + 1) + "." + tasks.get(i).getTaskString();
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
                    t.getTaskString() + "\n" + DIVIDER;
        } else {
            t.markAsDone(false);
            output = DIVIDER + " Alas, this task remains unfinished: \n   " +
                    t.getTaskString() + "\n" + DIVIDER;
        }
        System.out.println(output);
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
             String[] inputArray = input.split(" ");
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
