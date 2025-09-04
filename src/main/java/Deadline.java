import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
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
