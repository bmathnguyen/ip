package bill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Parser {

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

    public static boolean parse(String input, Ui ui, TaskList tasks) throws BillException {
        if (input.equals("bye")) {
            ui.showGoodbye();
            return true; // Signal to exit
        } else if (input.equals("list")) {
            ui.printTaskList(tasks.getTasks());
        } else if (input.startsWith("mark ")) {
            String rest = input.substring(5).trim();
            if (!isPositiveInteger(rest)) {
                throw new BillException("This is not a valid task number.");
            }
            int idx = Integer.parseInt(rest);
            if (idx <= 0 || idx > tasks.getSize()) {
                throw new BillException("This is not a valid task number.");
            }
            tasks.markTask(idx - 1);
            ui.showTaskMarked(tasks.getTask(idx - 1));
        } else if (input.startsWith("unmark ")) {
            String rest = input.substring(7).trim();
            if (!isPositiveInteger(rest)) {
                throw new BillException("This is not a valid task number.");
            }
            int idx = Integer.parseInt(rest);
            if (idx <= 0 || idx > tasks.getSize()) {
                throw new BillException("That task number doesn’t exist yet.");
            }
            tasks.unmarkTask(idx - 1);
            ui.showTaskUnmarked(tasks.getTask(idx - 1));
        } else if (input.startsWith("todo ")) {
            String desc = input.substring(5).trim();
            if (desc.isEmpty()) {
                throw new BillException("The description of a todo cannot be empty.");
            }
            Todo newTodo = new Todo(desc);
            tasks.addTask(newTodo);
            ui.showTaskAdded(newTodo, tasks.getSize());
        } else if (input.startsWith("deadline ")) {
            String body = input.substring(9).trim();
            int byPos = body.indexOf("/by");
            if (byPos == -1) {
                throw new BillException("Use: deadline <description> /by yyyy-MM-dd HHmm");
            }
            String desc = body.substring(0, byPos).trim();
            String byString = body.substring(byPos + 3).trim();
            if (desc.isEmpty() || byString.isEmpty()) {
                throw new BillException("Deadline needs both a description and a time.");
            }
            try {
                LocalDateTime by = LocalDateTime.parse(byString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                Deadline newDeadline = new Deadline(desc, by);
                tasks.addTask(newDeadline);
                ui.showTaskAdded(newDeadline, tasks.getSize());
            } catch (DateTimeParseException e) {
                throw new BillException("Invalid date format! Please use yyyy-MM-dd HHmm");
            }
        } else if (input.startsWith("event ")) {
            String body = input.substring(6).trim();
            int fromPos = body.indexOf("/from");
            int toPos = body.indexOf("/to");
            if (fromPos == -1 || toPos == -1 || toPos < fromPos) {
                throw new BillException("Use: event <desc> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
            }
            String desc = body.substring(0, fromPos).trim();
            String fromString = body.substring(fromPos + 5, toPos).trim();
            String toString = body.substring(toPos + 3).trim();
            if (desc.isEmpty() || fromString.isEmpty() || toString.isEmpty()) {
                throw new BillException("Event needs a description, a from-time, and a to-time.");
            }
            try {
                LocalDateTime from = LocalDateTime.parse(fromString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                LocalDateTime to = LocalDateTime.parse(toString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                Event newEvent = new Event(desc, from, to);
                tasks.addTask(newEvent);
                ui.showTaskAdded(newEvent, tasks.getSize());
            } catch (DateTimeParseException e) {
                throw new BillException("Invalid date format! Please use yyyy-MM-dd HHmm");
            }
        } else if (input.startsWith("delete ")) {
            String rest = input.substring(7).trim();
            if (!isPositiveInteger(rest)) {
                throw new BillException("This is not a valid task number.");
            }
            int idx = Integer.parseInt(rest);
            if (idx <= 0 || idx > tasks.getSize()) {
                throw new BillException("This is not a valid task number.");
            }
            Task removed = tasks.deleteTask(idx - 1);
            ui.showTaskDeleted(removed, tasks.getSize());
        } else if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
            ui.showFoundTasks(matchingTasks);
        } else {
            throw new BillException("Sorry, I don't understand that command.");
        }
        return false; // Not exit yet
    }
}