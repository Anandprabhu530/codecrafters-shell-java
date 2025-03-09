import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String path = System.getenv("PATH");
            System.out.println(path);
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
