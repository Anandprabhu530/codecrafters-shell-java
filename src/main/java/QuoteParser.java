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

    public void echoQuoteProcessor(String spliChar, String params) {
        String[] content = params.split(spliChar);
        for (int i = 1; i < content.length; i += 2) {
            if (content[i - 1].length() == 0) {
                System.out.print(content[i]);
            } else {
                System.out.print(" " + content[i]);
            }
        }
        System.out.println();
    }
}
