import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String test = args[0];
        System.out.println(test);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            if (input.startsWith("exit")) {
                System.exit(0);
            } else if (input.startsWith("echo")) {
                System.out.println(input.substring(5));
            } else if (input.startsWith("type")) {
                if (input.substring(5).equals("echo")) {
                    System.out.println("echo is a shell builtin");
                } else if (input.substring(5).equals("exit")) {
                    System.out.println("exit is a shell builtin");
                } else if (input.substring(5).equals("type")) {
                    System.out.println("type is a shell builtin");
                } else {
                    System.out.println(input.substring(5) + ": not found");
                }
            } else {
                System.out.println(input + ": command not found");
            }
        }
    }
}
