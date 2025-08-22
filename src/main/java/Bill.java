import java.util.Scanner;

public class Bill {

    private static boolean isPositiveInteger(String s) {
        if (s == null || s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        // reject "0" as a valid index
        // edge case?
        return Integer.parseInt(s) > 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello! I'm Bill");
        System.out.println("What can I do for you?");
        Task[] tasks = new Task[100];
        int count = 0;

        while (true) {
            String input = sc.nextLine();
            try {
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                } else if (input.equals("list")) {
                    for (int i = 0; i < count; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                } else if (input.startsWith("mark ")) {
                    String rest = input.substring(5);
                    if (!isPositiveInteger(rest)) {
                        throw new BillException("Please give me a valid task number after 'mark'.");
                    }
                    int idx = Integer.parseInt(rest);
                    if (idx < 1 || idx > count) {
                        throw new BillException("That task number doesn’t exist yet.");
                    }
                    tasks[idx - 1].mark();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[idx - 1]);
                } else if (input.startsWith("unmark ")) {
                    String rest = input.substring(7).trim();
                    if (!isPositiveInteger(rest)) {
                        throw new BillException("Please give me a valid task number after 'unmark'.");
                    }
                    int idx = Integer.parseInt(rest);
                    if (idx < 1 || idx > count) {
                        throw new BillException("That task number doesn’t exist yet.");
                    }
                    tasks[idx - 1].unmark();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[idx - 1]);
                } else if (input.startsWith("todo ")) {
                    if (input.equals("todo")) {
                        throw new BillException("The description of a todo cannot be empty.");
                    }
                    String desc = input.substring(5);
                    tasks[count++] = new Todo(desc);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[count - 1]);
                    System.out.println("Now you have " + count + " tasks in the list.");
                } else if (input.startsWith("deadline ")) {
                    String body = input.substring(9);
                    int byPos = body.indexOf("/by");
                    if (byPos == -1) {
                        throw new BillException("Use: deadline <description> /by <when>");
                    }
                    String desc = body.substring(0, byPos);
                    String by = body.substring(byPos + 3);
                    if (desc.isEmpty() || by.isEmpty()) {
                        throw new BillException("Deadline needs both description and /by time.");
                    }
                    tasks[count++] = new Deadline(desc, by);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[count - 1]);
                    System.out.println("Now you have " + count + " tasks in the list.");
                } else if (input.startsWith("event ")) {
                    String body = input.substring(6);
                    int fromPos = body.indexOf("/from");
                    int toPos = body.indexOf("/to");
                    if (fromPos == -1 || toPos == -1 || toPos < fromPos) {
                        throw new BillException("Use: event <description> /from <start> /to <end>");
                    }
                    String desc = body.substring(0, fromPos);
                    String from = body.substring(fromPos + 5, toPos);
                    String to = body.substring(toPos + 3);
                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        throw new BillException("Event needs description, /from, and /to.");
                    }
                    tasks[count++] = new Event(desc, from, to);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[count - 1]);
                    System.out.println("Now you have " + count + " tasks in the list.");
                } else {
                    throw new BillException("Sorry, I don't understand that command.");
                }
            } catch (BillException e) {
                System.out.println(e.getMessage());
            }
        }
        sc.close();
    }
}
