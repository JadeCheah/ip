package bart.util;

import java.util.ArrayList;

import bart.task.Task;


/**
 * Handles user interactions, including displaying messages and formatting output.
 * Provides helper methods to generate responses for various commands.
 */
public class Ui {
    /**
     * The offset used for task numbering in the UI.
     * Task numbers are 1-based for display purposes,
     * while internal list indices are 0-based.
     */
    private static final int TASK_INDEX_OFFSET = 1;


    /** The initial greeting message displayed when the application starts.*/
    public static final String GREETING_MESSAGE =
            """
            Greetings! I am Bartholomew.
            What service may I offer thee this day?
            """;

    /** Message displayed when the user provides an invalid command. */
    public static final String INVALID_COMMAND =
            """
            Invalid command! Please enter one of the following commands:
            
            General Commands:
              - list → Shows all tasks.
              - bye → Exits the program.
              
            Task Management Commands:
              - todo <task_description> → Adds a to-do task.
              - deadline <task_description> /by <yyyy-MM-dd> → Adds a deadline.
              - event <task_description> /from <yyyy-MM-dd> /to <yyyy-MM-dd> → Adds an event.
              [Tip: Thy can add tags upon creating new tasks by appending with #<tag_string>
                    e.g. todo <task_description> #<tag_string>]
                    
            Task Modification Commands:
              - mark <task_number> → Marks a task as done.
              - unmark <task_number> → Unmarks a completed task.
              - tag <task_number> #<tag_string> → Tags a task with given tag string.
              - untag <task_number> #<tag_string> → Removes a given tag string from task.
              - delete <task_number> → Deletes a task from the list.
              
            Search Command:
              - find <keyword> → Returns a list of tasks containing the specified keyword.
              
            [Tip: Task numbers correspond to the numbers in the list command.
            Ensure proper command format and valid task numbers!]
            """;
    /** Message displayed when invalid mark format, displays correct mark command format */
    public static final String INVALID_MARK_FORMAT =
            "Please use format: `mark <task_number>` or `unmark <task_number>`";
    /** Message displayed when invalid delete format, displays correct delete command format */
    public static final String INVALID_DELETE_FORMAT =
            "Please use format: `delete <task_number>`";
    /** Message displayed when invalid todo format, displays correct task command format */
    public static final String INVALID_TODO_FORMAT =
            "Please use format: `todo <task_description>`";
    /** Message displayed when invalid deadline format, displays correct deadline command format */
    public static final String INVALID_DEADLINE_FORMAT =
            "Please use format: `deadline <task_description> /by <yyyy-MM-dd>`";
    /** Message displayed when invalid event format, displays correct event command format */
    public static final String INVALID_EVENT_FORMAT =
            "Please use format: `event <task_description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>`";
    /** Message displayed when an invalid date format is provided. */
    public static final String INVALID_DATE_FORMAT = "Invalid date format. Please use format yyyy-MM-dd for dates.";

    /** Message displayed upon program exit. */
    public static final String EXIT_MESSAGE = "Farewell! May the winds of fate bring us together again.\n";

    /** Message displayed when the task list is empty. */
    public static final String EMPTY_LIST_MESSAGE = "Thy list is empty, noble one!\n";

    /** Message displayed when a task is marked as completed. */
    public static final String TASK_MARKED_MESSAGE = "Done and dusted! This chore is no more:\n  ";

    /** Message displayed when a task is unmarked as incomplete. */
    public static final String TASK_UNMARKED_MESSAGE = "Alas, this task remains unfinished:\n   ";

    /** Message displayed when an invalid task number is provided. */
    public static final String INVALID_TASK_NUMBER = "Task number must be a valid integer.";

    /** Message displayed when a task number is out of range. */
    public static final String TASK_NUMBER_OUT_OF_RANGE = "Task number is out of range.";

    /**
     * Generates a response when a task is added.
     *
     * @param task            The task that was added.
     * @param numberOfTasksLeft The number of tasks remaining in the task list.
     * @return A formatted string confirming the addition of the task.
     */
    public String getAddTaskString(Task task, int numberOfTasksLeft) {
        String output = "Noted! This task shall be remembered: \n  "
                + task.toString() + "\n"
                + " Thy list of labors now containeth " + numberOfTasksLeft + " undertakings.\n";
        return output;
    }

    /**
     * Generates a response when a task is deleted.
     *
     * @param task             The task that was deleted.
     * @param numberOfTasksLeft The number of remaining tasks in the task list.
     * @return A formatted string confirming the deletion of the task.
     */
    public String getDeletedTaskString(Task task, int numberOfTasksLeft) {
        String output = "This task is vanquished permanently! It shall trouble thee no further:\n   "
                + task.toString() + "\n"
                + " Thy list of labors now containeth " + numberOfTasksLeft + " undertakings.\n";
        return output;
    }

    /**
     * Generates a response listing tasks that match a search keyword.
     *
     * @param tasks The list of tasks matching the keyword.
     * @return A formatted string of matching tasks or a message if no tasks are found.
     */
    public String getFindTasksString(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "No record of such tasks doth exist.";
        }
        StringBuilder result = new StringBuilder();
        result.append("To thee, good sir/lady, I present the quests thou dost pursue:\n");

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            result.append(" ").append(i + TASK_INDEX_OFFSET).append(". ").append(t.toString()).append("\n");
        }
        return result.toString();
    }

    /**
     * Generates a response listing all tasks in the task list.
     *
     * @param tasks The list of tasks.
     * @return A formatted string containing all tasks or a message if the list is empty.
     */
    public String getListTasksString(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return EMPTY_LIST_MESSAGE;
        }

        StringBuilder result = new StringBuilder();
        result.append("Hark! These be thy duties:\n");

        for (int i = 0; i < tasks.size(); i++) {
            result.append(" ").append(i + TASK_INDEX_OFFSET).append(". ").append(tasks.get(i).toString()).append("\n");
        }
        return result.toString();
    }

    /**
     * Generates a response when a task is marked or unmarked.
     *
     * @param isMark      Whether the task is being marked as complete ({@code true})
     *                    or unmarked as incomplete ({@code false}).
     * @param taskString  The string representation of the task being marked or unmarked.
     * @return A formatted string confirming the task's status.
     */
    public String getMarkTaskString(boolean isMark, String taskString) {
        if (isMark) {
            return TASK_MARKED_MESSAGE + taskString;
        } else {
            return TASK_UNMARKED_MESSAGE + taskString;
        }
    }

    /**
     * Generates a response when a tag is added to a task.
     *
     * @param tag  The tag string tagged to task.
     * @param task The task to tag.
     * @return A formatted string confirming the adding of a tag to a task.
     */
    public String getTagTaskString(boolean isTag, String tag, Task task) {
        if (isTag) {
            return "Added tag " + tag + " to task:\n  " + task.toString() + ".";
        } else {
            return "Removed tag " + tag + " from task:\n  " + task.toString() + ".";
        }
    }
}


