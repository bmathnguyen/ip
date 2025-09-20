package bill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime from; // Removed 'final'
    private LocalDateTime to;   // Removed 'final'

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        assert description != null && !description.trim().isEmpty() : "Description cannot be null or empty";
        assert from != null : "Event 'from' time cannot be null";
        assert to != null : "Event 'to' time cannot be null";
        this.from = from;
        this.to = to;
    }

    /**
     * Updates the event's start time.
     *
     * @param from The new start date and time.
     */
    public void setFrom(LocalDateTime from) {
        assert from != null : "New event 'from' time cannot be null";
        this.from = from;
    }

    /**
     * Updates the event's end time.
     *
     * @param to The new end date and time.
     */
    public void setTo(LocalDateTime to) {
        assert to != null : "New event 'to' time cannot be null";
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