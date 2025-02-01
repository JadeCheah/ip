package bart.util;

import bart.command.AddCommand;
import bart.command.Command;
import bart.command.DeleteCommand;
import bart.command.ListCommand;
import bart.command.MarkCommand;
import bart.command.ExitCommand;
import bart.command.FindCommand;

/**
 * The Parser class is responsible for parsing user input into commands.
 */
public class Parser {
    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param input The user input string.
     * @return The command corresponding to the user input.
     */
    public static Command parseCommand(String input) {
        String[] tokens = input.split(" ", 2);
        if (tokens[0].equals("mark") || tokens[0].equals("unmark")) {
            int taskNumber = Integer.parseInt(tokens[1].trim());
            return new MarkCommand(tokens[0].equals("mark"), taskNumber);
        } else if (tokens[0].equals("delete")) {
            int taskNumber = Integer.parseInt(tokens[1].trim());
            return new DeleteCommand(taskNumber);
        } else if (tokens[0].equals("find")) {
            return new FindCommand(tokens[1].trim());
        } else if (input.equalsIgnoreCase("bye")) {
            return new ExitCommand();
        } else if (input.equalsIgnoreCase("list")) {
            return new ListCommand();
        } else {
            return new AddCommand(input);
        }
    }
}
