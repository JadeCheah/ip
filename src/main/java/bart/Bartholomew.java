package bart;

import bart.command.Command;
import bart.util.Parser;
import bart.util.Storage;
import bart.util.Ui;

import java.util.Scanner;

/**
 * The Bartholomew class is the main entry point for the application.
 * It initializes the necessary components and runs the main event loop.
 */
public class Bartholomew {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Bartholomew object with the specified file path.
     *
     * @param filePath The file path to load tasks from.
     */
    public Bartholomew(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main event loop of the application.
     * It greets the user, reads commands, and executes them until the exit command is given.
     */
    public void run() {
        ui.greetUser();
        boolean isExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand(scanner);
                Command c = Parser.parseCommand(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (Exception e) {
                ui.printMessage(e.getMessage());
            }
        }
        scanner.close();
    }

    /**
     * The main method to start the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Bartholomew("./data/bart.txt").run();
    }
}
