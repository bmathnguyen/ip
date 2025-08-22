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
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < count; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                String rest = input.substring(5);
                if (isPositiveInteger(rest)) {
                    int idx = Integer.parseInt(rest);
                    if (idx >= 1 && idx <= count) {
                        tasks[idx - 1].mark();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[idx - 1]);
                    } else {
                        tasks[count++] = new Task(input);
                        System.out.println(" added: " + input);
                    }
                } else {
                    tasks[count++] = new Task(input);
                    System.out.println(" added: " + input);
                }
            } else if (input.startsWith("unmark ")) {
                String rest = input.substring(7).trim();
                if (isPositiveInteger(rest)) {
                    int idx = Integer.parseInt(rest);
                    if (idx >= 1 && idx <= count) {
                        tasks[idx - 1].unmark();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks[idx - 1]);
                    } else {
                        tasks[count++] = new Task(input);
                        System.out.println(" added: " + input);
                    }
                } else {
                    tasks[count++] = new Task(input);
                    System.out.println(" added: " + input);
                }
            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5);
                tasks[count++] = new Todo(desc);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count - 1]);
                System.out.println("Now you have " + count + " tasks in the list.");
            } else if (input.startsWith("deadline ")) {
                String body = input.substring(9);
                int byPos = body.indexOf("/by");
                String desc = body.substring(0, byPos);
                String by = body.substring(byPos + 3);
                tasks[count++] = new Deadline(desc, by);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count - 1]);
                System.out.println("Now you have " + count + " tasks in the list.");
            } else if (input.startsWith("event ")) {
                String body = input.substring(6);
                int fromPos = body.indexOf("/from");
                int toPos = body.indexOf("/to");
                String desc = body.substring(0, fromPos);
                String from = body.substring(fromPos + 5, toPos);
                String to = body.substring(toPos + 3);
                tasks[count++] = new Event(desc, from, to);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count - 1]);
                System.out.println("Now you have " + count + " tasks in the list.");
            } else {
                tasks[count++] = new Task(input);
                System.out.println("added: " + input);
            }
        }
        sc.close();
    }
}
