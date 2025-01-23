public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return ( isDone ? "X" : " ");
    }

    public void markAsDone(Boolean done) {
        isDone = done;
    }

    public String getTaskString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
