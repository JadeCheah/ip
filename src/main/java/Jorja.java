import java.util.Scanner;
public class Jorja {
    private static final String DIVIDER = "____________________________________________________________\n";

    private static void greetUser() {
        String greeting = DIVIDER +
                " Hello! I'm Jorja\n" +
                " What can I do for you?\n" +
                DIVIDER;
        System.out.println(greeting);
    }

    public static void echo(String message) {
        String echo = DIVIDER + message + "\n" + DIVIDER;
        System.out.println(echo);
    }

    public static void exit() {
        String exitMessage = DIVIDER + "Bye until we meet again!\n" + DIVIDER;
        System.out.println(exitMessage);
    }

    public static void main(String[] args) {
        greetUser();
        Scanner scanner = new Scanner(System.in);
        while (true) {
             String input = scanner.nextLine();
             if (input.equalsIgnoreCase("bye")) {
                 exit();
                 break;
             } else {
                 echo(input);
             }
        }
        scanner.close();
    }
}
