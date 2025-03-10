import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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
                    if (params.charAt(0) == '\'') {
                        params = params.replaceAll("'+", "");
                        System.out.println(params);
                    } else {
                        params = params.replaceAll("\\s+", " ");
                        System.out.println(params);
                    }
                    break;
                case "cat":
                    String[] dm = params.split("'");
                    System.out.println(dm.length);
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
                        String[] fullPath = new String[] { command, params };
                        Process process = Runtime.getRuntime().exec(fullPath);
                        process.getInputStream().transferTo(System.out);
                    } else {
                        System.out.println(command + ": command not found");
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
