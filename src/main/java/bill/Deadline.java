package bill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime by; // Removed 'final' to allow modification

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

    @Override
    public String toString() {
        String formattedDate = by.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));
        return "[D]" + super.toString() + " (by: " + formattedDate + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + this.by;
    }
}