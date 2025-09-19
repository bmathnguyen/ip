package bill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        assert description != null && !description.trim().isEmpty() : "Description cannot be null or empty";
        assert from != null : "Event 'from' time cannot be null";
        assert to != null : "Event 'to' time cannot be null";
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        String formattedFrom = from.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));
        String formattedTo = to.format(DateTimeFormatter.ofPattern("h:mm a"));
        return "[E]" + super.toString() + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + this.from + " | " + this.to;
    }
}
