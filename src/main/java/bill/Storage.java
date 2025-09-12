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
     * Constructs a Storage instance.
     *
     * @param filePath The path to the file used for storage.
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Saves the current list of tasks to the file, overwriting its content.
     *
     * @param tasks The list of tasks to save.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        this.file.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(this.file);
        for (Task task : tasks) {
            fw.write(task.toFileFormat() + "\n");
        }
        fw.close();
    }

    /**
     * Loads tasks from the file. Each line in the file is parsed to re-create the task objects.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws FileNotFoundException If the file does not exist.
     */
    public ArrayList<Task> load() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner s = new Scanner(this.file);
        while (s.hasNext()) {
            String line = s.nextLine();
            String[] taskParts = line.split(" \\| ");
            Task task = null;
            switch (taskParts[0]) {
                case "T":
                    task = new Todo(taskParts[2]);
                    break;
                case "D":
                    task = new Deadline(taskParts[2], LocalDateTime.parse(taskParts[3]));
                    break;
                case "E":
                    task = new Event(taskParts[2], LocalDateTime.parse(taskParts[3]), LocalDateTime.parse(taskParts[4]));
                    break;
            }
            if (task != null) {
                if (taskParts[1].equals("1")) {
                    task.mark();
                }
                tasks.add(task);
            }
        }
        s.close();
        return tasks;
    }
}
