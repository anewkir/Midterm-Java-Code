import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CsvRandomValuePicker {

    private static final String COMMA_DELIMITER = ",";
    private static final String FILE_PATH = "input.csv";

    public static void main(String[] args) {

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(FILE_PATH));

            ArrayList<String> values = new ArrayList<String>();
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length == 1 && tokens[0].matches("\\d{8}")) {
                    values.add(tokens[0]);
                }
            }
            fileReader.close();

            if (values.isEmpty()) {
                System.out.println("No valid values found in CSV file.");
                return;
            }

            Random random = new Random();
            int index = random.nextInt(values.size());
            String selectedValue = values.get(index);

            System.out.println("Selected value: " + selectedValue);

        } catch (IOException e) {
            System.err.println("Error in CSV file processing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
