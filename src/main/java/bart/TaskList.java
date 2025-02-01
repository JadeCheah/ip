package bart;

import bart.task.Task;

import java.util.ArrayList;


/**
 * Represents a list of tasks.
 */
public class TaskList {
    public ArrayList<Task> tasks;

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
     * Retrieves a task from the list.
     *
     * @param taskNumber The task number to retrieve.
     * @return The task at the specified position.
     */
    public Task getTask(int taskNumber) {
        return this.tasks.get(taskNumber - 1);
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
        Task t = tasks.get(taskNumber - 1);
        tasks.remove(taskNumber - 1);
        return t;
    }

    /**
     * Prints all tasks in the list.
     */
    public void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            String output = " " + (i + 1) + "." + tasks.get(i).toString();
            System.out.println(output);
        }
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int countTasks() {
        return tasks.size();
    }

    public ArrayList<Task> findTask(String keyword) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().contains(keyword)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }
}
