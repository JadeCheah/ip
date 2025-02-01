package bart.util;

import bart.TaskList;
import bart.task.Deadline;
import bart.task.Event;
import bart.task.Task;
import bart.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasks(TaskList tasks) {
        try {
            // Create the data directory if it doesn't exist
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdir();
            }

            // Write tasks to the file
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks.tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

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

    private static Task parseTaskFromFile(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null; // Invalid line
        }
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
            case "T":
                Todo todo = new Todo(description);
                if (isDone) todo.markAsDone(true);
                return todo;
            case "D":
                if (parts.length < 4) return null;
                try {
                    LocalDate byDate = LocalDate.parse(parts[3]);
                    Deadline deadline = new Deadline(description, byDate);
                    if (isDone) deadline.markAsDone(true);
                    return deadline;
                } catch (Exception e) {
                    System.out.println("Error: Invalid date format for bart.task.Deadline task.");
                    return null;
                }
            case "E":
                if (parts.length < 5) return null;
                try {
                    LocalDate fromDate = LocalDate.parse(parts[3]);
                    LocalDate toDate = LocalDate.parse(parts[4]);
                    Event event = new Event(description, fromDate, toDate);
                    if (isDone) event.markAsDone(true);
                    return event;
                } catch (Exception e) {
                    System.out.println("Error: Invalid date format for bart.task.Event task.");
                    return null;
                }
            default:
                return null;
        }
    }
}
