package bart.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bart.TaskList;
import bart.task.Todo;
import bart.util.Storage;
import bart.util.Ui;

class AddCommandTest {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        ui = new Ui();
        storage = new Storage("./data/bart.txt");
    }

    /**
     * Tests the AddCommand for adding a Todo task.
     * Ensures that the Todo task is added correctly to the task list.
     */
    @Test
    void testExecute_validTodo() {
        String command = "todo Read book";
        AddCommand addCommand = new AddCommand(command);

        CommandResult result = addCommand.execute(taskList, ui, storage);

        assertEquals(CommandResult.ResultType.SUCCESS, result.getResultType(),
                "Adding a valid Todo should return SUCCESS");
        assertEquals(1, taskList.countTasks(), "Task list should contain one task");
        assertInstanceOf(Todo.class, taskList.getTask(1), "Added task should be a Todo");
        assertEquals("[T][ ] Read book", taskList.getTask(1).toString(),
                "String representation of the Todo task should match");
    }

    /**
     * Tests the AddCommand for an empty command.
     * Ensures that a FAILURE result is returned indicating that a description is required.
     */
    @Test
    void testExecute_emptyCommand() {
        String command = ""; // Empty command
        AddCommand addCommand = new AddCommand(command);

        CommandResult result = addCommand.execute(taskList, ui, storage);

        assertEquals(CommandResult.ResultType.FAILURE, result.getResultType(),
                "An empty command should return FAILURE");
        assertEquals("A description is required!", result.getMessage(),
                "Error message should indicate that a description is required for the task");
        assertEquals(0, taskList.countTasks(),
                "Task list should remain empty for empty command");
    }

    /**
     * Tests the AddCommand for a Todo task with no description.
     * Ensures that a FAILURE result is returned stating that a description is required.
     */
    @Test
    void testExecute_todoNoDescription() {
        String command = "todo"; // No description provided
        AddCommand addCommand = new AddCommand(command);

        CommandResult result = addCommand.execute(taskList, ui, storage);

        assertEquals(CommandResult.ResultType.FAILURE, result.getResultType(),
                "A Todo without a description should return FAILURE");
        assertEquals("Please use format: `todo <taskDescription>`", result.getMessage(),
                "Error message should match the expected format message for Todo");
        assertEquals(0, taskList.countTasks(),
                "Task list should remain empty for missing description");
    }

    /**
     * Tests the AddCommand for a Deadline task with missing date format.
     * Ensures that a FAILURE result is returned indicating invalid date format.
     */
    @Test
    void testExecute_deadlineInvalidDate() {
        String command = "deadline Submit report /by invalid-date"; // Invalid date format
        AddCommand addCommand = new AddCommand(command);

        CommandResult result = addCommand.execute(taskList, ui, storage);

        assertEquals(CommandResult.ResultType.FAILURE, result.getResultType(),
                "A Deadline with an invalid date should return FAILURE");
        assertEquals("Invalid date format. Please use format yyyy-MM-dd for dates.", result.getMessage(),
                "Should return error message for invalid date format");
        assertEquals(0, taskList.countTasks(),
                "Task list should remain empty for invalid date format");
    }

    /**
     * Tests the AddCommand for an Event task with incorrect date range format.
     * Ensures that a FAILURE result is returned indicating invalid date format.
     */
    @Test
    void testExecute_eventInvalidDateRange() {
        String command = "event Workshop /from 2025-12-01 /to invalid-date"; // Invalid date format
        AddCommand addCommand = new AddCommand(command);

        CommandResult result = addCommand.execute(taskList, ui, storage);

        assertEquals(CommandResult.ResultType.FAILURE, result.getResultType(),
                "An Event with an invalid date range should return FAILURE");
        assertEquals("Invalid date format. Please use format yyyy-MM-dd for dates.", result.getMessage(),
                "Should return error message for invalid date range format");
        assertEquals(0, taskList.countTasks(),
                "Task list should remain empty for invalid date range");
    }

    /**
     * Tests the AddCommand with an invalid command format.
     * Ensures that a FAILURE result is returned.
     */
    @Test
    void testExecute_invalidCommand() {
        String command = "invalidCommand";
        AddCommand addCommand = new AddCommand(command);

        CommandResult result = addCommand.execute(taskList, ui, storage);

        assertEquals(CommandResult.ResultType.FAILURE, result.getResultType(),
                "An invalid command should return FAILURE");
        assertEquals(0, taskList.countTasks(),
                "Task list should remain empty for invalid command");
    }
}
