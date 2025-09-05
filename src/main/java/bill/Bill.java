package bill;

import java.io.IOException;
import java.util.ArrayList;

public class Bill {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    public Bill(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (IOException e) {
            this.tasks = new TaskList(new ArrayList<>());
        }
    }

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

    public static void main(String[] args) {
        new Bill("data/bill.txt").run();
    }
}