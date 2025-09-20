package bill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific due date.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Constructs a new Deadline task.
     *
     * @param description The task's description.
     * @param by The due date and time.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        assert description != null && !description.trim().isEmpty() : "Description cannot be null or empty";
        assert by != null : "Deadline time 'by' cannot be null";
        this.by = by;
    }

    /**
     * Updates the deadline's due date.
     *
     * @param by The new due date and time.
     */
    public void setBy(LocalDateTime by) {
        assert by != null : "New deadline time 'by' cannot be null";
        this.by = by;
    }

    /**
     * Returns the string representation of the deadline.
     *
     * @return A formatted string for display.
     */
    @Override
    public String toString() {
        String formattedDate = by.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));
        return "[D]" + super.toString() + " (by: " + formattedDate + ")";
    }

    /**
     * Returns the string representation for file storage.
     *
     * @return A pipe-separated string for saving.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + this.by;
    }
}