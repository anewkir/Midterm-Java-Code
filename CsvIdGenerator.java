import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CsvIdGenerator {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "ID,Column1,Column2,Column3";

    private static final int ID_LENGTH = 8;
    private static final String ID_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int MAX_ATTEMPTS = 10000;

    public static void main(String[] args) {

        String inputFilePath = "input.csv";
        String outputFilePath = "output.csv";

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(inputFilePath));
            FileWriter fileWriter = new FileWriter(outputFilePath);

            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);

            String line;
            Set<String> idSet = new HashSet<String>();
            Random random = new Random();

            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(COMMA_DELIMITER);
                String id = generateUniqueId(idSet, random);
                idSet.add(id);

                fileWriter.append(id);
                for (int i = 0; i < tokens.length; i++) {
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(tokens[i]);
                }
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            fileWriter.close();
            fileReader.close();
            System.out.println("CSV file generated successfully.");

        } catch (IOException e) {
            System.err.println("Error in CSV file processing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String generateUniqueId(Set<String> existingIds, Random random) {
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ID_LENGTH; i++) {
                int index = random.nextInt(ID_CHARACTERS.length());
                sb.append(ID_CHARACTERS.charAt(index));
            }
            String id = sb.toString();
            if (!existingIds.contains(id)) {
                return id;
            }
            attempts++;
        }
        throw new RuntimeException("Could not generate a unique ID after " + MAX_ATTEMPTS + " attempts.");
    }
}
