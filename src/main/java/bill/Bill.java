package bill;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The main class for the Bill chatbot application.
 * It coordinates the user interface, storage, and task list.
 */
public class Bill {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a new Bill instance.
     * Initializes UI, storage, and loads tasks from the specified file path.
     *
     * @param filePath The path to the file where tasks are saved.
     */
    public Bill(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (IOException e) {
            this.tasks = new TaskList(new ArrayList<>());
        }
    }

    /**
     * Runs the main application loop, processing user commands until the exit command is given.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                isExit = Parser.parse(fullCommand, ui, tasks);
                if (!isExit) {
                    storage.save(tasks.getTasks());
                }
            } catch (BillException | IOException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * The main entry point of the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Bill("data/bill.txt").run();
    }
}