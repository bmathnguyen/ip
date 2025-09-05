package bill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses user input and executes the corresponding command.
 */
public class Parser {

    /**
     * Checks if the given string represents a positive integer.
     * This helper method is used to validate task numbers from user input.
     *
     * @param s The string to be checked.
     * @return true if the string is a positive integer (e.g., "1", "25"), false otherwise.
     */
    private static boolean isPositiveInteger(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return Integer.parseInt(s) > 0;
    }

    /**
     * Parses the full user command and executes it.
     *
     * @param input The full command string entered by the user.
     * @param ui The Ui instance for user interaction.
     * @param tasks The TaskList instance to be modified.
     * @return true if the command is an exit command, false otherwise.
     * @throws BillException if the command is invalid or has incorrect parameters.
     */
    public static boolean parse(String input, Ui ui, TaskList tasks) throws BillException {
        if (input.equals("bye")) {
            ui.showGoodbye();
            return true; // Signal to exit
        } else if (input.equals("list")) {
            ui.printTaskList(tasks.getTasks());
        } else if (input.startsWith("mark ")) {
            String commandArgs = input.substring(5).trim();
            if (!isPositiveInteger(commandArgs)) {
                throw new BillException("This is not a valid task number.");
            }
            int taskIndex = Integer.parseInt(commandArgs);
            if (taskIndex <= 0 || taskIndex > tasks.getSize()) {
                throw new BillException("This is not a valid task number.");
            }
            tasks.markTask(taskIndex - 1);
            ui.showTaskMarked(tasks.getTask(taskIndex - 1));
        } else if (input.startsWith("unmark ")) {
            String commandArgs = input.substring(7).trim();
            if (!isPositiveInteger(commandArgs)) {
                throw new BillException("This is not a valid task number.");
            }
            int taskIndex = Integer.parseInt(commandArgs);
            if (taskIndex <= 0 || taskIndex > tasks.getSize()) {
                throw new BillException("That task number doesn’t exist yet.");
            }
            tasks.unmarkTask(taskIndex - 1);
            ui.showTaskUnmarked(tasks.getTask(taskIndex - 1));
        } else if (input.startsWith("todo ")) {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new BillException("The descriptionription of a todo cannot be empty.");
            }
            Todo newTodo = new Todo(description);
            tasks.addTask(newTodo);
            ui.showTaskAdded(newTodo, tasks.getSize());
        } else if (input.startsWith("deadline ")) {
            String commandBody = input.substring(9).trim();
            int byPos = commandBody.indexOf("/by");
            if (byPos == -1) {
                throw new BillException("Use: deadline <descriptionription> /by yyyy-MM-dd HHmm");
            }
            String description = commandBody.substring(0, byPos).trim();
            String byString = commandBody.substring(byPos + 3).trim();
            if (description.isEmpty() || byString.isEmpty()) {
                throw new BillException("Deadline needs both a descriptionription and a time.");
            }
            try {
                LocalDateTime by = LocalDateTime.parse(byString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                Deadline newDeadline = new Deadline(description, by);
                tasks.addTask(newDeadline);
                ui.showTaskAdded(newDeadline, tasks.getSize());
            } catch (DateTimeParseException e) {
                throw new BillException("Invalid date format! Please use yyyy-MM-dd HHmm");
            }
        } else if (input.startsWith("event ")) {
            String commandBody = input.substring(6).trim();
            int fromPos = commandBody.indexOf("/from");
            int toPos = commandBody.indexOf("/to");
            if (fromPos == -1 || toPos == -1 || toPos < fromPos) {
                throw new BillException("Use: event <description> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
            }
            String description = commandBody.substring(0, fromPos).trim();
            String fromString = commandBody.substring(fromPos + 5, toPos).trim();
            String toString = commandBody.substring(toPos + 3).trim();
            if (description.isEmpty() || fromString.isEmpty() || toString.isEmpty()) {
                throw new BillException("Event needs a descriptionription, a from-time, and a to-time.");
            }
            try {
                LocalDateTime from = LocalDateTime.parse(fromString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                LocalDateTime to = LocalDateTime.parse(toString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                Event newEvent = new Event(description, from, to);
                tasks.addTask(newEvent);
                ui.showTaskAdded(newEvent, tasks.getSize());
            } catch (DateTimeParseException e) {
                throw new BillException("Invalid date format! Please use yyyy-MM-dd HHmm");
            }
        } else if (input.startsWith("delete ")) {
            String commandArgs = input.substring(7).trim();
            if (!isPositiveInteger(commandArgs)) {
                throw new BillException("This is not a valid task number.");
            }
            int taskIndex = Integer.parseInt(commandArgs);
            if (taskIndex <= 0 || taskIndex > tasks.getSize()) {
                throw new BillException("This is not a valid task number.");
            }
            Task removed = tasks.deleteTask(taskIndex - 1);
            ui.showTaskDeleted(removed, tasks.getSize());
        } else {
            throw new BillException("Sorry, I don't understand that command.");
        }
        return false; // Not exit yet
    }
}