import java.time.LocalDate;
import java.util.ArrayList;

public class TaskList {
    public ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public Task getTask(int taskNumber) {
        return this.tasks.get(taskNumber - 1);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int taskNumber) {
        Task t = tasks.get(taskNumber - 1);
        tasks.remove(taskNumber - 1);
        return t;
    }

    public void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            String output = " " + (i + 1) + "." + tasks.get(i).toString();
            System.out.println(output);
        }
    }

    public int countTasks() {
        return tasks.size();
    }
}
