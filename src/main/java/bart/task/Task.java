package bart.task;

import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a task with a description and a completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    private Set<String> tags;

    /**
     * Constructs a Task with the specified description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.tags = new HashSet<>();
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
        return (isDone ? "X" : " ");
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
        return "[" + getStatusIcon() + "] " + description + formatTags();
    }

    /**
     * Returns the file format representation of the task.
     *
     * @return The file format representation of the task.
     */
    public String toFileFormat() {
        String tagString = tags.isEmpty() ? "" : " | " + String.join(" ", tags);
        return (isDone ? "1" : "0") + " | " + description + tagString;
    }

    /**
     * Adds a tag to the task.
     *
     * @param tag The tag to be added (e.g., "#fun").
     */
    public void addTag(String tag) {
        if (!tag.startsWith("#")) {
            tag = "#" + tag;
        }
        tags.add(tag);
    }

    /**
     * Removes a tag from the task.
     *
     * @param tag The tag to be removed.
     */
    public void removeTag(String tag) {
        tags.remove(tag);
    }

    /**
     * Returns a set of all tags associated with this task.
     *
     * @return The set of tags.
     */
    public Set<String> getTags() {
        return tags;
    }

    private String formatTags() {
        return tags.isEmpty() ? "" : " " + String.join(" ", tags);
    }

    /**
     * Checks if this task is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the tasks are equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task task = (Task) obj;
        return Objects.equals(description, task.description);
    }
}
