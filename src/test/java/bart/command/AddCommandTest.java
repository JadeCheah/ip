package bart.command;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import bart.task.Deadline;
import bart.task.Event;
import bart.task.Todo;
import bart.util.Storage;
import bart.util.Ui;
import bart.TaskList;

class AddCommandTest {

    /**
     * Tests the AddCommand for adding a Todo task.
     * Ensures that the task is added correctly to the task list.
     */
    @Test
    void testAddTodo() {
        // Arrange
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/bart.txt");
        String command = "todo Read book";
        AddCommand addCommand = new AddCommand(command);

        // Act
        addCommand.execute(taskList, ui, storage);

        // Assert
        assertEquals(1, taskList.countTasks(), "Task list should contain one task");
        assertInstanceOf(Todo.class, taskList.getTask(1), "Added task should be a Todo");
        assertEquals("Read book",
                taskList.getTask(1).getDescription(),
                "Todo description should match");
    }

    /**
     * Tests the AddCommand for adding a Deadline task.
     * Ensures that the task is added correctly to the task list.
     */
    @Test
    void testAddDeadline() {
        // Arrange
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/bart.txt");
        String command = "deadline Submit assignment /by 2025-12-01";
        AddCommand addCommand = new AddCommand(command);

        // Act
        addCommand.execute(taskList, ui, storage);

        // Assert
        assertEquals(1, taskList.countTasks(), "Task list should contain one task");
        assertInstanceOf(Deadline.class,
                taskList.getTask(1),
                "Added task should be a Deadline");
        assertEquals("Submit assignment",
                taskList.getTask(1).getDescription(),
                "Deadline description should match");
        assertEquals(LocalDate.of(2025, 12, 1),
                ((Deadline) taskList.getTask(1)).getByDate(),
                "Deadline date should match");
    }

    /**
     * Tests the AddCommand for adding an Event task.
     * Ensures that the task is added correctly to the task list.
     */
    @Test
    void testAddEvent() {
        // Arrange
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/bart.txt");
        String command = "event Team meeting /from 2025-12-01 /to 2025-12-02";
        AddCommand addCommand = new AddCommand(command);

        // Act
        addCommand.execute(taskList, ui, storage);

        // Assert
        assertEquals(1,
                taskList.countTasks(),
                "Task list should contain one task");
        assertInstanceOf(Event.class,
                taskList.getTask(1),
                "Added task should be an Event");
        assertEquals("Team meeting",
                taskList.getTask(1).getDescription(),
                "Event description should match");
        assertEquals(LocalDate.of(2025, 12, 1),
                ((Event) taskList.getTask(1)).getFromDate(),
                "Event start date should match");
        assertEquals(LocalDate.of(2025, 12, 2),
                ((Event) taskList.getTask(1)).getToDate(),
                "Event end date should match");
    }

    /**
     * Tests the AddCommand with an invalid command.
     * Ensures that no task is added to the task list.
     */
    @Test
    void testInvalidCommand() {
        // Arrange
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/bart.txt");
        String command = "invalidCommand";
        AddCommand addCommand = new AddCommand(command);

        // Act
        addCommand.execute(taskList, ui, storage);

        // Assert
        assertEquals(0,
                taskList.countTasks(),
                "Task list should remain empty for invalid command");
    }
}
