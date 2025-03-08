import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            String[] tempArray = input.split(" ");
            if (tempArray[0].equals("exit")) {
                System.exit(0);
            } else if (tempArray[0].equals("echo")) {
                for (int i = 1; i < tempArray.length; i++) {
                    if (i == tempArray.length - 1) {
                        System.out.print(tempArray[i]);
                    } else {
                        System.out.print(tempArray[i] + " ");
                    }
                }
                System.out.println();
            } else {
                System.out.println(input + ": command not found");
            }
        }
    }
}
