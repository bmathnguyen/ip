import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Bill {

    private final Storage storage;
    private ArrayList<Task> tasks;

    public Bill(String filePath) {
        this.storage = new Storage(filePath);
        try {
            this.tasks = storage.load();
        } catch (IOException e) {
//            System.out.println("No saved tasks found. Starting fresh!");
            this.tasks = new ArrayList<>();
        }
    }

    private void saveTasks() {
        try {
            storage.save(this.tasks);
        } catch (IOException e) {
//            System.out.println("Could not save tasks: " + e.getMessage());
        }
    }

    private static boolean isPositiveInteger(String s) {
        if (s == null || s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        // reject "0" as a valid index
        // edge case?
        return Integer.parseInt(s) > 0;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello! I'm Bill");
        System.out.println("What can I do for you?");

        while (true) {
            String input = sc.nextLine();
            try {
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                } else if (input.equals("list")) {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                } else if (input.startsWith("mark ")) {
                    String rest = input.substring(5);
                    if (!isPositiveInteger(rest)) {
                        throw new BillException("Please give me a valid task number after 'mark'.");
                    }
                    int idx = Integer.parseInt(rest);
                    if (idx < 1 || idx > tasks.size()) {
                        throw new BillException("That task number doesn’t exist yet.");
                    }
                    tasks.get(idx-1).mark();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(idx-1));
                    saveTasks();
                } else if (input.startsWith("unmark ")) {
                    String rest = input.substring(7).trim();
                    if (!isPositiveInteger(rest)) {
                        throw new BillException("Please give me a valid task number after 'unmark'.");
                    }
                    int idx = Integer.parseInt(rest);
                    if (idx < 1 || idx > tasks.size()) {
                        throw new BillException("That task number doesn’t exist yet.");
                    }
                    tasks.get(idx-1).unmark();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(idx-1));
                    saveTasks();
                } else if (input.startsWith("todo ")) {
                    if (input.equals("todo")) {
                        throw new BillException("The description of a todo cannot be empty.");
                    }
                    String desc = input.substring(5);
                    tasks.add(new Todo(desc));
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    saveTasks();
                } else if (input.startsWith("deadline ")) {
                    String body = input.substring(9);
                    int byPos = body.indexOf("/by");
                    if (byPos == -1) {
                        throw new BillException("Use: deadline <description> /by yyyy-MM-dd HHmm");
                    }
                    String desc = body.substring(0, byPos).trim();
                    String byString = body.substring(byPos + 4).trim();
                    if (desc.isEmpty() || byString.isEmpty()) {
                        throw new BillException("Deadline needs both a description and a time.");
                    }
                    try {
                        LocalDateTime by = LocalDateTime.parse(byString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                        tasks.add(new Deadline(desc, by));
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        saveTasks();
                    } catch (DateTimeParseException e) {
                        throw new BillException("Invalid date format! Please use yyyy-MM-dd HHmm");
                    }
                } else if (input.startsWith("event ")) {
                    String body = input.substring(6);
                    int fromPos = body.indexOf("/from");
                    int toPos = body.indexOf("/to");
                    if (fromPos == -1 || toPos == -1 || toPos < fromPos) {
                        throw new BillException("Use: event <desc> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
                    }
                    String desc = body.substring(0, fromPos).trim();
                    String fromString = body.substring(fromPos + 6, toPos).trim();
                    String toString = body.substring(toPos + 4).trim();
                    if (desc.isEmpty() || fromString.isEmpty() || toString.isEmpty()) {
                        throw new BillException("Event needs a description, a from-time, and a to-time.");
                    }
                    try {
                        LocalDateTime from = LocalDateTime.parse(fromString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                        LocalDateTime to = LocalDateTime.parse(toString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                        tasks.add(new Event(desc, from, to));
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1));
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        saveTasks();
                    } catch (DateTimeParseException e) {
                        throw new BillException("Invalid date format! Please use yyyy-MM-dd HHmm");
                    }
                } else if (input.startsWith("delete ")) {
                    String rest = input.substring(7);
                    if (!isPositiveInteger(rest)) {
                        throw new BillException("Please give me a valid task number after 'delete'.");
                    }
                    int idx = Integer.parseInt(rest);
                    if (idx < 1 || idx > tasks.size()) {
                        throw new BillException("That task number doesn’t exist yet.");
                    }

                    Task removed = tasks.remove(idx - 1); // Just one line to remove
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removed);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    saveTasks();
                } else {
                    throw new BillException("Sorry, I don't understand that command.");
                }
            } catch (BillException e) {
                System.out.println(e.getMessage());
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        new Bill("data/bill.txt").run();
    }
}
