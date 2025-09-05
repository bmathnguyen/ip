package bill;

import java.time.LocalDateTime;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        this.file.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(this.file);
        for (Task task : tasks) {
            fw.write(task.toFileFormat() + "\n");
        }
        fw.close();
    }

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