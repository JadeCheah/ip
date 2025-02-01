package bart.task;

import java.util.Objects;

/**
 * Represents a task with a description and a completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the specified description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Retrieves the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the status icon of the task.
     *
     * @return "X" if the task is done, otherwise a space.
     */
    public String getStatusIcon() {
        return ( isDone ? "X" : " ");
    }

    /**
     * Marks the task as done or not done.
     *
     * @param done True to mark the task as done, false to mark it as not done.
     */
    public void markAsDone(Boolean done) {
        isDone = done;
    }

    /**
     * Returns the string representation of the task, including its status icon and description.
     *
     * @return The string representation of the task.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

     /**
     * Returns the file format representation of the task.
     *
     * @return The file format representation of the task.
     */
    public String toFileFormat() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Checks if this task is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the tasks are equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return Objects.equals(description, task.description);
    }
}
