package bill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        this.file.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(this.file)) {
            for (Task task : tasks) {
                fw.write(task.toFileFormat() + "\n");
            }
        }
    }

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