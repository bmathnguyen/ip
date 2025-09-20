package bill;

/**
 * Represents a generic task with a description and a completion status.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Constructs a new Task.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets the task's description.
     *
     * @return The description string.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Checks if the task is done.
     *
     * @return True if the task is done, false otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Gets the status icon for the task.
     *
     * @return "X" for done, or a space for not done.
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the string representation of the task.
     *
     * @return A formatted string for display.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns the string representation for file storage.
     *
     * @return A pipe-separated string for saving.
     */
    public String toFileFormat() {
        return (isDone ? "1" : "0") + " | " + description;
    }
}