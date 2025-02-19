package bart.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import bart.TaskList;
import bart.task.Deadline;
import bart.task.Event;
import bart.task.Task;
import bart.task.Todo;

/**
 * The Storage class handles loading and saving tasks to a file.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The file path to load and save tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Creates a directory if it doesn't exist
     * @param path
     */
    private void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * Saves the tasks to the file.
     *
     * @param tasks The task list to save.
     */
    public void saveTasks(TaskList tasks) {
        try {
            createDirectory("./data");

            // Write tasks to the file
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks.getTasks()) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    /**
     * Loads the tasks from the file.
     *
     * @return The list of tasks loaded from the file.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // If the file doesn't exist, return an empty list
        if (!file.exists()) {
            return tasks;
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTaskFromFile(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Parses a line of task data from the file and returns the corresponding Task
     * object.
     *
     * @param line The line of task data.
     * @return The Task object corresponding to the task data, or null if the line
     *         is invalid.
     */
    private static Task parseTaskFromFile(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null; // Invalid line
        }
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task = null;

        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            try {
                LocalDate byDate = LocalDate.parse(parts[3]);
                task = new Deadline(description, byDate);
            } catch (Exception e) {
                System.out.println("Error: Invalid date format for bart.task.Deadline task.");
                return null;
            }
            break;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            try {
                LocalDate fromDate = LocalDate.parse(parts[3]);
                LocalDate toDate = LocalDate.parse(parts[4]);
                task = new Event(description, fromDate, toDate);
            } catch (Exception e) {
                System.out.println("Error: Invalid date format for bart.task.Event task.");
                return null;
            }
            break;
        default:
            return null;
        }
        // Mark as done if needed
        if (task != null && isDone) {
            task.markAsDone(true);
        }

        int minFields = (task instanceof Event) ? 5 : (task instanceof Deadline) ? 4 : 3;
        if (parts.length > minFields) {
            for (int i = minFields; i < parts.length; i++) {
                if (!parts[i].isBlank()) {
                    String tag = parts[i].trim();
                    if (!tag.startsWith("#")) {
                        tag = "#" + tag; // Ensure the tag is standardized
                    }
                    task.addTag(tag); // Add the standardized tag
                }
            }
        }
        return task;
    }
}
