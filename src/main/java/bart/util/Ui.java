package bart.util;

import bart.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    public static final String DIVIDER = "__________________________________________________________________________\n";

    public void greetUser() {
        String greeting = DIVIDER
                + " Greetings! I am Bartholomew.\n"
                + " What service may I offer thee this day?\n" +
                DIVIDER;
        System.out.println(greeting);
    }

    public String readCommand(Scanner scanner) {
        return scanner.nextLine();
    }

    public void showExit() {
        String exitMessage = DIVIDER + " Farewell! May the winds of fate bring us together again.\n" + DIVIDER;
        System.out.println(exitMessage);
    }

    public void printMessage(String message) {
        System.out.println(DIVIDER + " " + message + " \n" + DIVIDER);
    }

    public void listHeader() {
        System.out.println(DIVIDER + " Hark! These be thy duties: \n");
    }

    public void printDivider() {
        System.out.println(DIVIDER);
    }

    public void printDeletedTask(Task task, int numberOfTasksLeft) {
        String output = DIVIDER
                + "This task is vanquished permanently! It shall trouble thee no further:\n   "
                + task.toString() + "\n"
                + " Thy list of labors now containeth " + numberOfTasksLeft + " undertakings.\n" +
                DIVIDER;
        System.out.println(output);
    }

    public void printError(String errorMessage) {
        System.out.println(DIVIDER + " " + errorMessage + "\n" + DIVIDER);
    }

    public void showListEmpty() {
        System.out.println(Ui.DIVIDER + " Thy list is empty, noble one!\n" + Ui.DIVIDER);
    }

    public void showAddTask(Task task, int numberOfTasksLeft) {
        String output = DIVIDER
                + " Noted! This task shall be remembered: \n   "
                + task.toString() + "\n"
                + " Thy list of labors now containeth " + numberOfTasksLeft + " undertakings.\n" +
                DIVIDER;
        System.out.println(output);
    }

    public void printTasks(ArrayList<Task> tasks) {
        System.out.println(DIVIDER + " To thee, good sir/lady, I present the quests thou dost pursue: " + "\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            System.out.println(" " + (i + 1) + "." + t.toString());
        }
        System.out.println(DIVIDER);
    }
}
