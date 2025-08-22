import java.util.Scanner;

public class Bill {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello! I'm Bill");
        System.out.println("What can I do for you?");
        String[] tasks = new String[100];
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
            } else {
                tasks[count] = input;
                count++;
                System.out.println("added: " + input);
            }
        }
        sc.close();
    }
}
