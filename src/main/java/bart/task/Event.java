package bart.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that spans a period of time.
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    /**
     * Constructs an Event task with the specified description, start date, and end date.
     *
     * @param description The description of the task.
     * @param from        The start date of the event.
     * @param to          The end date of the event.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Retrieves the start date of the event.
     *
     * @return The start date.
     */
    public LocalDate getFromDate() {
        return from;
    }

    /**
     * Retrieves the end date of the event.
     *
     * @return The end date.
     */
    public LocalDate getToDate() {
        return to;
    }

    /**
     * Returns the string representation of the task, including its type and date range.
     *
     * @return The string representation of the task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + formatDateToString(from)
                + " to: " + formatDateToString(to) + ")";
    }

    /**
     * Returns the file format representation of the task.
     *
     * @return The file format representation of the task.
     */
    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " + from + " | " + to;
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
