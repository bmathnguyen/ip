package bill;

import java.io.IOException;
import java.util.ArrayList;

public class Bill {
    private final Storage storage;
    private TaskList tasks;

    public Bill(String filePath) {
        assert filePath != null && !filePath.isEmpty() : "File path cannot be null or empty";
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (IOException e) {
            this.tasks = new TaskList(new ArrayList<>());
        }
        assert this.storage != null;
        assert this.tasks != null;
    }

    public String getResponse(String input) {
        try {
            String response = Parser.parse(input, tasks);

            String commandWord = input.split(" ")[0].trim().toLowerCase();
            // Save the task list if a command modified it.
            if (commandWord.equals("mark") || commandWord.equals("unmark") || commandWord.equals("todo")
                    || commandWord.equals("deadline") || commandWord.equals("event")
                    || commandWord.equals("delete") || commandWord.equals("snooze")) {
                storage.save(tasks.getTasks());
            }
            return response;
        } catch (BillException | IOException e) {
            return e.getMessage();
        }
    }
}