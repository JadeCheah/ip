package bart.command;

import bart.TaskList;
import bart.task.Task;
import bart.util.Storage;
import bart.util.Ui;

/**
 * Represents a command to add a tag to a task.
 */
public class TagCommand extends Command {
    private String tag;
    private int taskNumber;
    private boolean isTag;

    /**
     * Constructs a TagCommand with the specified tag string.
     *
     * @param isTag indicates whether this TagCommand is a tag command or untag command.
     * @param tag The tag string.
     * @param taskNumber The number of the task to be tagged (according to list).
     */
    public TagCommand(boolean isTag, String tag, int taskNumber) {
        this.isTag = isTag;
        this.tag = tag;
        assert taskNumber > 0 : "taskNumber must be greater than 0";
        this.taskNumber = taskNumber;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (tag == null || tag.isBlank()) {
            return new CommandResult(CommandResult.ResultType.FAILURE,
                    "Thy must specify a tag.");
        }
        try {
            Task task = tasks.getTask(taskNumber);
            if (isTag) {
                task.addTag(tag);
            } else {
                task.removeTag(tag);
            }
            return new CommandResult(CommandResult.ResultType.SUCCESS, ui.getTagTaskString(isTag, tag, task));
        } catch (IndexOutOfBoundsException e) {
            return new CommandResult(CommandResult.ResultType.FAILURE, Ui.TASK_NUMBER_OUT_OF_RANGE);
        }
    }
}
