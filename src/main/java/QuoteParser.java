import java.io.BufferedReader;
import java.io.FileReader;

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
        String[] dm = params.split(spliChar);
        for (int i = 1; i < dm.length; i += 2) {
            if (i == dm.length - 1 || i == dm.length - 2) {
                System.out.print(dm[i]);
            } else {
                System.out.print(dm[i] + " ");
            }
        }
        System.out.println();
    }
}
