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
        try {
            return Integer.parseInt(s) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String parse(String input, TaskList tasks) throws BillException {
        if (input.equals("bye")) {
            return "Bye. Hope to see you again soon!";

        } else if (input.equals("list")) {
            if (tasks.getSize() == 0) {
                return "Your task list is empty.";
            }
            StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.getSize(); i++) {
                sb.append(i + 1).append(". ").append(tasks.getTask(i).toString()).append("\n");
            }
            return sb.toString().trim();

        } else if (input.startsWith("mark ")) {
            String commandArgs = input.substring(5).trim();
            if (!isPositiveInteger(commandArgs)) {
                throw new BillException("This is not a valid task number.");
            }
            int taskIndex = Integer.parseInt(commandArgs);
            if (taskIndex <= 0 || taskIndex > tasks.getSize()) {
                throw new BillException("This task number does not exist.");
            }
            tasks.markTask(taskIndex - 1);
            return "Nice! I've marked this task as done:\n  " + tasks.getTask(taskIndex - 1);

        } else if (input.startsWith("unmark ")) {
            String commandArgs = input.substring(7).trim();
            if (!isPositiveInteger(commandArgs)) {
                throw new BillException("This is not a valid task number.");
            }
            int taskIndex = Integer.parseInt(commandArgs);
            if (taskIndex <= 0 || taskIndex > tasks.getSize()) {
                throw new BillException("This task number does not exist.");
            }
            tasks.unmarkTask(taskIndex - 1);
            return "OK, I've marked this task as not done yet:\n  " + tasks.getTask(taskIndex - 1);

        } else if (input.startsWith("todo ")) {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new BillException("The description of a todo cannot be empty.");
            }
            Todo newTodo = new Todo(description);
            tasks.addTask(newTodo);
            return "Got it. I've added this task:\n  " + newTodo
                    + "\nNow you have " + tasks.getSize() + " tasks in the list.";

        } else if (input.startsWith("deadline ")) {
            String commandBody = input.substring(9).trim();
            int byPos = commandBody.indexOf("/by");
            if (byPos == -1) {
                throw new BillException("Use: deadline <description> /by yyyy-MM-dd HHmm");
            }
            String description = commandBody.substring(0, byPos).trim();
            String byString = commandBody.substring(byPos + 3).trim();
            if (description.isEmpty() || byString.isEmpty()) {
                throw new BillException("Deadline needs both a description and a time.");
            }
            try {
                LocalDateTime by = LocalDateTime.parse(byString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                Deadline newDeadline = new Deadline(description, by);
                tasks.addTask(newDeadline);
                return "Got it. I've added this task:\n  " + newDeadline
                        + "\nNow you have " + tasks.getSize() + " tasks in the list.";
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
                throw new BillException("Event needs a description, a from-time, and a to-time.");
            }
            try {
                LocalDateTime from = LocalDateTime.parse(fromString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                LocalDateTime to = LocalDateTime.parse(toString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                Event newEvent = new Event(description, from, to);
                tasks.addTask(newEvent);
                return "Got it. I've added this task:\n  " + newEvent
                        + "\nNow you have " + tasks.getSize() + " tasks in the list.";
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
                throw new BillException("This task number does not exist.");
            }
            Task removed = tasks.deleteTask(taskIndex - 1);
            return "Noted. I've removed this task:\n  " + removed
                    + "\nNow you have " + tasks.getSize() + " tasks in the list.";

        } else if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            if (keyword.isEmpty()) {
                throw new BillException("Please provide a keyword to search for.");
            }
            ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
            if (matchingTasks.isEmpty()) {
                return "No tasks found with the keyword: " + keyword;
            }
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append(i + 1).append(". ").append(matchingTasks.get(i).toString()).append("\n");
            }
            return sb.toString().trim();

        } else {
            throw new BillException("Sorry, I don't understand that command.");
        }
    }
}