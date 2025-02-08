package bart;

import bart.command.Command;
import bart.command.CommandResult;
import bart.util.Storage;
import bart.util.Ui;


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
     * Executes the command and returns it's result.
     * @param c the command to be executed.
     * @return result of the command execution.
     */
    public CommandResult getResponse(Command c) {
        return c.execute(tasks, ui, storage);
    }

    public String getGreetingMessage() {
        return Ui.GREETING_MESSAGE;
    }
}
