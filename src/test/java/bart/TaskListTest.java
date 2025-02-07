package bart;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
