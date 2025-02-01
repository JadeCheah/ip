package bart;

import bart.command.Command;
import bart.util.Parser;
import bart.util.Storage;
import bart.util.Ui;

import java.util.Scanner;


public class Bartholomew {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public static void main(String[] args) {
        new Bartholomew("./data/bart.txt").run();
    }
}
