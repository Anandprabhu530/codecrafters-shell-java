import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        HashSet<String> builtInCommands = builtinCommandSet();
        QuoteParser quoteParser = new QuoteParser();
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
            if (params.contains(">") || params.contains("1>")) {
                String testString01 = "";
                String testString02 = "";
                String[] redirect = params.split(">");
                String[] firstFile = redirect[0].split(" ");
                // fileName in firstFile[firstFile.length-1].trim();
                // secondFile in redirect[1].trim();
                // ProcessBuilder processBuilder = new ProcessBuilder();
                File file = new File(redirect[1].trim());
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                if (params.startsWith("ls")) {
                    System.out.println("Inside ls");
                    String[] shellCommand = { "ls", firstFile[firstFile.length - 1].trim() };
                    Process process = Runtime.getRuntime().exec(shellCommand);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    StringBuilder output = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                    System.out.println(output);
                }
                continue;
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
                        quoteParser.echoQuoteProcessor('\'', params);
                    } else if (params.charAt(0) == '"') {
                        quoteParser.echoQuoteProcessor('"', params);
                    } else {
                        params = params.replaceAll("\\s+", " ");
                        for (int i = 0; i < params.length(); i++) {
                            if (params.charAt(i) == '\\')
                                continue;
                            System.out.print(params.charAt(i));
                        }
                        System.out.println();
                    }
                    break;

                case "cat":
                    if (params.charAt(0) == '\'') {
                        String[] files = params.split("'");
                        for (int i = 1; i < files.length; i += 2) {
                            quoteParser.readFile(files[i]);
                        }

                    } else if (params.charAt(0) == '"') {
                        String[] files = params.split("\"");
                        for (int i = 1; i < files.length; i += 2) {
                            quoteParser.readFile(files[i]);
                        }
                    }
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

                case "'exe":
                    String[] temp = params.split("'");
                    quoteParser.readFile(temp[temp.length - 1].trim());
                    break;

                case "\"exe":
                    String[] fileName = params.split("\"");
                    quoteParser.readFile(fileName[fileName.length - 1].trim());
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

    private static HashSet<String> builtinCommandSet() {
        HashSet<String> set = new HashSet<>();
        set.add("echo");
        set.add("exit");
        set.add("type");
        return set;
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
