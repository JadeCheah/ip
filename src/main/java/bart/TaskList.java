package bart;

import java.util.ArrayList;
import java.util.stream.Collectors;

import bart.task.Task;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private static final int INDEX_OFFSET = 1;
    private ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with the specified list of tasks.
     *
     * @param tasks The list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Retrieves a specified task from the list.
     *
     * @param taskNumber The task number to retrieve.
     * @return The task at the specified position.
     */
    public Task getTask(int taskNumber) {
        return this.tasks.get(taskNumber - INDEX_OFFSET);
    }

    /**
     * Retrieves the tasks of the task list
     *
     * @return The tasks field
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list.
     *
     * @param taskNumber The task number to delete.
     * @return The deleted task.
     */
    public Task deleteTask(int taskNumber) {
        assert taskNumber > 0 && taskNumber <= tasks.size() : "Invalid task number for deletion: " + taskNumber;
        Task t = tasks.get(taskNumber - INDEX_OFFSET);
        tasks.remove(taskNumber - INDEX_OFFSET);
        return t;
    }
    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int countTasks() {
        return tasks.size();
    }

    /**
     * Returns true if tasks is empty.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Finds and retrieves all tasks that contain a specific keyword in their description.
     *
     * @param keyword The keyword to search for in task descriptions.
     * @return An {@code ArrayList} of tasks containing the keyword.
     */
    public ArrayList<Task> findTasks(String keyword) {
        return tasks.stream()
                .filter(task -> task.getDescription().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
