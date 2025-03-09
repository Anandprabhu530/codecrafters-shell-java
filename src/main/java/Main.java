import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        HashSet<String> builtInCommands = new HashSet<>();
        builtInCommands.add("echo");
        builtInCommands.add("exit");
        builtInCommands.add("type");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            String[] commands = input.split(" ");

            String command = commands[0];
            String params = "";

            if (commands.length > 2) {
                for (int i = 1; i < commands.length; i++) {
                    if (i == commands.length - 1) {
                        params += commands[i];
                    } else {
                        params += commands[i] + " ";
                    }
                }

            } else if (commands.length > 1) {
                params += commands[1];
            }

            switch (command) {
                case "exit":
                    if (params.equals("0")) {
                        System.exit(0);
                    } else {
                        System.out.println(input + ": command not found");
                    }
                    break;

                case "echo":
                    System.out.println(params);
                    break;

                case "type":
                    if (builtInCommands.contains(params)) {
                        System.out.println(params + " is a shell builtin");
                    } else {
                        String path = getPath(params);
                        if (path != null) {
                            System.out.println(params + " is " + path);
                        } else {
                            System.out.println(params + ": not found");
                        }
                    }
                    break;

                default:
                    String path = getPath(command);
                    if (path != null) {
                        System.out.println("Program was passed 2 args (including program name).");
                        System.out.println("Arg #0 (program name): " + command);
                        System.out.println("Arg #1: " + commands[1]);
                        System.out.println("Program Signature: " + System.getProperty(command));
                    } else {
                        System.out.println(input + ": command not found");
                    }
                    break;
            }
        }
    }

    private static String getPath(String pathString) {
        for (String path : System.getenv("PATH").split(":")) {
            Path fullPath = Path.of(path, pathString);
            if (Files.isRegularFile(fullPath)) {
                return fullPath.toString();
            }
        }
        return null;
    }
}
