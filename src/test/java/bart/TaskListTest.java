package bart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import bart.task.Task;

public class TaskListTest {

    /**
     * Tests the deleteTask method of TaskList.
     * Ensures that the correct task is deleted and the task list is updated accordingly.
     */
    @Test
    public void testDeleteTask() {
        TaskList taskList = new TaskList();
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        Task task3 = new Task("Task 3");

        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);

        // Act: Delete a task
        Task deletedTask = taskList.deleteTask(2); // Delete the second task (Task 2)

        // Assert: Verify the deleted task and the remaining list
        assertEquals(task2, deletedTask, "The deleted task should be Task 2");
        assertEquals(2, taskList.countTasks(), "Task list should contain 2 tasks after deletion");
        assertEquals(task1, taskList.getTask(1), "The first task should remain unchanged");
        assertEquals(task3, taskList.getTask(2), "The third task should now be the second task");
    }

    /**
     * Tests the addTask method of TaskList.
     * Ensures that a task is correctly added to the task list.
     */
    @Test
    public void testAddTask() {
        TaskList taskList = new TaskList();
        Task task1 = new Task("Task 1");

        taskList.addTask(task1);

        // Assert: Verify that the task was added and countTasks reflects the correct number of tasks
        assertEquals(1, taskList.countTasks(), "Task list should contain 1 task after adding Task 1");
        assertEquals(task1, taskList.getTask(1), "The first task should be Task 1");
    }

    /**
     * Tests the findTasks method of TaskList.
     * Ensures that tasks containing the specified keyword are returned correctly.
     */
    @Test
    public void testFindTasks() {
        TaskList taskList = new TaskList();
        Task task1 = new Task("Study Java");
        Task task2 = new Task("Complete homework");
        Task task3 = new Task("Attend Java class");

        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);

        // Act: Find tasks containing the word "Java"
        var foundTasks = taskList.findTasks("Java");

        // Assert: Verify the correct tasks are found
        assertEquals(2, foundTasks.size(), "There should be 2 tasks containing the keyword 'Java'");
        assertTrue(foundTasks.contains(task1), "Task 1 should be found");
        assertTrue(foundTasks.contains(task3), "Task 3 should be found");
        assertFalse(foundTasks.contains(task2), "Task 2 should not be found");
    }
}
