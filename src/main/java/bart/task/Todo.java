package bart.task;

/**
 * Represents a task without any date/time attached to it.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the task, including its type.
     *
     * @return The string representation of the task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString() + super.getTagsToString();
    }

    /**
     * Returns the file format representation of the task.
     *
     * @return The file format representation of the task.
     */
    @Override
    public String toFileFormat() {
        return "T | " + super.toFileFormat()
                + super.getTagsToFileFormat();
    }
}
