import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class QuoteParser {
    public void catQuoteProcessor(String splitChar, String params) throws Exception {
        String[] files = params.split(splitChar);
        for (int i = 1; i < files.length; i += 2) {
            String fileName = files[i];
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.print(line);
                }
            }
        }
        System.out.println();
    }

    public void echoQuoteProcessor(char spliChar, String params) {
        StringBuilder str = new StringBuilder();
        int index = 0;
        boolean isOpen = false, hasSpace = false;
        while (index != params.length()) {
            char temp = params.charAt(index);
            if (temp == spliChar) {
                isOpen = !isOpen;
                if (!isOpen)
                    hasSpace = false;
                index++;
                continue;
            }
            if (temp == '\\' && index < params.length() - 1) {
                if (params.charAt(0) == '\'') {
                    str.append(params.charAt(index++));
                    continue;
                }
                str.append(params.charAt(index + 1));
                index += 2;
                continue;
            }

            if (isOpen) {
                str.append(temp);
                index++;
                continue;
            }
            if (!isOpen && temp == ' ' && !hasSpace) {
                hasSpace = true;
                str.append(' ');
            }
            if (!isOpen && temp != ' ' && temp != '"') {
                str.append(temp);
            }
            index++;

        }
        System.out.println(str);
        // String[] content = params.split(spliChar);
        // for (int i = 1; i < content.length; i += 2) {
        // if (content[i - 1].length() == 0) {
        // System.out.print(content[i]);
        // } else {
        // System.out.print(" " + content[i]);
        // }
        // }
        // System.out.println();
    }

    public void executionOfQuotedText(String splitString, String params) throws Exception {
        String[] temp = params.split(splitString);

        try (BufferedReader reader = new BufferedReader(new FileReader(temp[temp.length - 1].trim()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.print(line);
            }
            System.out.println();
        }
    }
}
