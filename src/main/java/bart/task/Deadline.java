package bart.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task{
    protected LocalDate by;

    /**
     * Constructs a Deadline task with the specified description and deadline date.
     *
     * @param description The description of the task.
     * @param by          The deadline date of the task.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Retrieves the deadline date of the task.
     *
     * @return The deadline date.
     */
    public LocalDate getByDate() {
        return by;
    }

    /**
     * Returns the string representation of the task, including its type and deadline date.
     *
     * @return The string representation of the task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + formatDateToString(by) + ")";
    }

    /**
     * Returns the file format representation of the task.
     *
     * @return The file format representation of the task.
     */
    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat() + " | " + by;
    }

    /**
     * Formats the date to a readable string format.
     *
     * @param date The date to format.
     * @return The formatted date string.
     */
    private String formatDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }
}
