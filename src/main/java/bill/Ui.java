package bill;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user interface interactions, including reading input and displaying output.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a new Ui instance and initializes the scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next command from the user.
     *
     * @return The full command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the welcome message to the user.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm bill.Bill");
        System.out.println("What can I do for you?");
    }

    /**
     * Displays the goodbye message to the user.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Prints all tasks in the task list to the console.
     *
     * @param tasks The list of tasks to be printed.
     */
    public void printTaskList(ArrayList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Displays a confirmation message after a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Displays a confirmation message after a task has been marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Displays a confirmation message after a task has been added.
     *
     * @param task The task that was added.
     * @param newSize The new total number of tasks in the list.
     */
    public void showTaskAdded(Task task, int newSize) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + newSize + " tasks in the list.");
    }

    /**
     * Displays a confirmation message after a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param newSize The new total number of tasks in the list.
     */
    public void showTaskDeleted(Task task, int newSize) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + newSize + " tasks in the list.");
    }
}