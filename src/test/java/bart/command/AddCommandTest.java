package bart.command;

import bart.task.Deadline;
import bart.task.Event;
import bart.task.Todo;
import bart.util.Storage;
import bart.util.Ui;
import org.junit.jupiter.api.Test;
import bart.TaskList;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AddCommandTest {

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
        assertEquals("Read book", taskList.getTask(1).getDescription(), "Todo description should match");
    }

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
        assertInstanceOf(Deadline.class, taskList.getTask(1), "Added task should be a Deadline");
        assertEquals("Submit assignment", taskList.getTask(1).getDescription(), "Deadline description should match");
        assertEquals(LocalDate.of(2025, 12, 1), ((Deadline) taskList.getTask(1)).getByDate(), "Deadline date should match");
    }

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
        assertEquals(1, taskList.countTasks(), "Task list should contain one task");
        assertInstanceOf(Event.class, taskList.getTask(1), "Added task should be an Event");
        assertEquals("Team meeting", taskList.getTask(1).getDescription(), "Event description should match");
        assertEquals(LocalDate.of(2025, 12, 1), ((Event) taskList.getTask(1)).getFromDate(), "Event start date should match");
        assertEquals(LocalDate.of(2025, 12, 2), ((Event) taskList.getTask(1)).getToDate(), "Event end date should match");
    }

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
        assertEquals(0, taskList.countTasks(), "Task list should remain empty for invalid command");
    }
}
