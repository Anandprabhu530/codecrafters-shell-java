import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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

            if (params.contains("2>>")) {
                String[] redirect = params.split("2>>");
                String[] inputFileArray = redirect[0].trim().split(" ");
                File Inputfile = new File(inputFileArray[inputFileArray.length - 1].trim());
                File file = new File(redirect[1].trim());
                StringBuilder output = new StringBuilder();

                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }

                if (command.equals("echo")) {
                    String temp = redirect[0].trim();
                    System.out.println(temp.substring(1, temp.length() - 1));
                    continue;

                } else if (command.equals("ls")) {
                    if (!Inputfile.exists()) {
                        output.append(
                                command + ": nonexistent: No such file or directory");
                    }
                } else if (command.equals("cat")) {
                    String[] files = redirect[0].trim().split(" ");
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].trim().equals("nonexistent")) {
                            output.delete(0, output.length());
                            output.append(command + ": " + files[i] + ": No such file or directory");
                            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                            if (file.length() > 0)
                                writer.write(output.toString());
                            writer.close();
                            continue;
                        }
                    }
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                if (file.length() > 0)
                    writer.write("\n");
                writer.write(output.toString());
                writer.close();
                continue;
            }

            if (params.contains("2>")) {
                String[] redirect = params.split("2>");
                File Inputfile = new File(redirect[0].trim());
                File file = new File(redirect[1].trim());
                StringBuilder output = new StringBuilder();

                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }

                if (command.equals("echo")) {
                    String temp = redirect[0].trim();
                    // output.append(temp.substring(1, temp.length() - 1));
                    System.out.println(temp.substring(1, temp.length() - 1));
                    output.delete(0, output.length());

                } else if (command.equals("ls")) {
                    if (!Inputfile.exists()) {
                        output.append(
                                command + ": nonexistent: No such file or directory");
                    }
                } else if (command.equals("cat")) {
                    String[] files = redirect[0].split(" ");
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].trim().equals("nonexistent")) {
                            output.delete(0, output.length());
                            output.append(command + ": " + files[i] + ": No such file or directory");
                            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                            writer.write(output.toString());
                            writer.close();
                            continue;
                        } else {
                            quoteParser.readFile(files[i], false);
                        }
                    }
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(output.toString());
                writer.close();
                continue;
            }

            if (params.contains(">>") || params.contains("1>>")) {
                String[] redirect = params.split("1?>>");
                String[] firstFile = redirect[0].split(" ");
                File file = new File(redirect[1].trim());
                StringBuilder output = new StringBuilder();

                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                if (command.equals("echo")) {
                    String temp = redirect[0].trim();
                    output.append(temp.substring(1, temp.length() - 1));
                } else if (command.equals("ls")) {
                    File inputFile = new File(firstFile[firstFile.length - 1].trim());
                    if (!inputFile.exists()) {
                        System.out.println(command + ": nonexistent: No such file or directory");
                        continue;
                    }
                    String[] shellCommand = { "ls", firstFile[firstFile.length - 1].trim() };
                    Process process = Runtime.getRuntime().exec(shellCommand);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                } else if (command.equals("cat")) {
                    for (int i = 0; i < firstFile.length; i++) {
                        if (firstFile[i].trim().equals("nonexistent")) {
                            System.out.println(command + ": " + firstFile[i] + ": No such file or directory");
                            continue;
                        }
                        String[] shellCommand = { "cat", firstFile[i].trim() };
                        Process process = Runtime.getRuntime().exec(shellCommand);
                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(process.getInputStream()));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            output.append(line).append("\n");
                        }
                    }
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                if (file.length() > 0)
                    writer.write("\n");
                writer.write(output.toString());
                writer.close();
                continue;
            }

            if (params.contains(">") || params.contains("1>")) {
                String[] redirect = params.split("1?>");
                String[] firstFile = redirect[0].split(" ");
                File file = new File(redirect[1].trim());
                StringBuilder output = new StringBuilder();

                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                if (command.equals("echo")) {
                    String temp = redirect[0].trim();
                    output.append(temp.substring(1, temp.length() - 1));
                } else if (command.equals("ls")) {
                    String[] shellCommand = { "ls", firstFile[firstFile.length - 1].trim() };
                    Process process = Runtime.getRuntime().exec(shellCommand);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                } else if (command.equals("cat")) {
                    for (int i = 0; i < firstFile.length; i++) {
                        if (firstFile[i].trim().equals("nonexistent")) {
                            System.out.println(command + ": " + firstFile[i] + ": No such file or directory");
                            continue;
                        }
                        String[] shellCommand = { "cat", firstFile[i].trim() };
                        Process process = Runtime.getRuntime().exec(shellCommand);
                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(process.getInputStream()));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            output.append(line).append("\n");
                        }
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        writer.write(output.toString());
                        writer.close();
                    }
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(output.toString());
                writer.close();
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
                            quoteParser.readFile(files[i], true);
                        }
                        System.out.println();

                    } else if (params.charAt(0) == '"') {
                        String[] files = params.split("\"");
                        for (int i = 1; i < files.length; i += 2) {
                            quoteParser.readFile(files[i], true);
                        }
                        System.out.println();
                    } else {
                        quoteParser.readFile(params, false);
                    }
                    break;

                case "pwd":
                    System.out.println(System.getProperty("user.dir"));
                    break;

                case "cd":
                    String checkIfPathExists = params.trim();
                    File checkIfExists = new File(checkIfPathExists);
                    if (checkIfExists.exists()) {
                        System.setProperty("user.dir", checkIfPathExists);
                    } else {
                        System.out.println(command + ": " + checkIfPathExists + ": No such file or directory");
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
                    quoteParser.readFile(temp[temp.length - 1].trim(), false);
                    break;

                case "\"exe":
                    String[] fileName = params.split("\"");
                    quoteParser.readFile(fileName[fileName.length - 1].trim(), false);
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
        set.add("pwd");
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
