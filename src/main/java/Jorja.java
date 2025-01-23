import java.util.ArrayList;
import java.util.Scanner;

public class Jorja {
    private static final String DIVIDER = "____________________________________________________________\n";
    private static ArrayList<String> tasks;

    private static void greetUser() {
        String greeting = DIVIDER +
                " Hello! I'm Jorja\n" +
                " What can I do for you?\n" +
                DIVIDER;
        System.out.println(greeting);
    }

    public static void addTask(String message) {
        tasks.add(message);
        String echo = DIVIDER + "added: " + message + "\n" + DIVIDER;
        System.out.println(echo);
    }

    public static void exit() {
        String exitMessage = DIVIDER + "Bye until we meet again!\n" + DIVIDER;
        System.out.println(exitMessage);
    }

    public static void listTasks() {
        System.out.println(DIVIDER);
        for (int i = 0; i < tasks.size(); i++) {
            String output = (i + 1) + ". " + tasks.get(i);
            System.out.println(output);
        }
        System.out.println(DIVIDER);
    }

    public static void main(String[] args) {
        greetUser();
        tasks = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
             String input = scanner.nextLine();
             if (input.equalsIgnoreCase("bye")) {
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
