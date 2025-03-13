import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class QuoteParser {

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

    public void readFile(String fileName, boolean isContinuous) throws Exception {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (isContinuous) {
                    System.out.print(line);
                } else {
                    System.out.println(line);
                }
            }
        }

    }

}
