package bill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading tasks from and saving tasks to a file.
 */
public class Storage {
    private final File file;

    /**
     * Constructs a Storage object.
     *
     * @param filePath The path of the file used for storage.
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Saves the list of tasks to the file.
     *
     * @param tasks The list of tasks to save.
     * @throws IOException If an error occurs during writing.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        this.file.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(this.file)) {
            for (Task task : tasks) {
                fw.write(task.toFileFormat() + "\n");
            }
        }
    }

    /**
     * Loads the list of tasks from the file.
     *
     * @return An ArrayList of tasks.
     * @throws FileNotFoundException If the file is not found.
     */
    public ArrayList<Task> load() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!file.exists()) {
            return tasks; // Return empty list if file doesn't exist
        }
        try (Scanner s = new Scanner(this.file)) {
            while (s.hasNext()) {
                String line = s.nextLine();
                Task task = parseLineToTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    /**
     * Parses a single line from the file into a Task object.
     *
     * @param line The line of text from the file.
     * @return The corresponding Task object.
     */
    private Task parseLineToTask(String line) {
        String[] parts = line.split(" \\| ");
        Task task = null;
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                LocalDateTime by = LocalDateTime.parse(parts[3]);
                task = new Deadline(description, by);
                break;
            case "E":
                LocalDateTime from = LocalDateTime.parse(parts[3]);
                LocalDateTime to = LocalDateTime.parse(parts[4]);
                task = new Event(description, from, to);
                break;
        }

        if (task != null && isDone) {
            task.mark();
        }
        return task;
    }
}