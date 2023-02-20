import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvSender {

    public static void main(String[] args) {
        String inputFilePath = "output.csv";
        String outputFilePath = "selected.csv";

        try {
            // Read the CSV data from the input file
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            List<String> lines = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            // Create a new process to run the second program
            ProcessBuilder pb = new ProcessBuilder("java", "CsvRandomValuePicker");
            Process process = pb.start();

            // Write the CSV data to the standard input of the process
            OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream());
            for (String l : lines) {
                writer.write(l + "\n");
            }
            writer.close();

            // Read the output of the second program from its standard output
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            FileWriter outputWriter = new FileWriter(new File(outputFilePath));
            String outputLine;
            while ((outputLine = inputReader.readLine()) != null) {
                outputWriter.write(outputLine + "\n");
            }
            inputReader.close();
            outputWriter.close();

            System.out.println("CSV file processed successfully.");

        } catch (IOException e) {
            System.err.println("Error in CSV file processing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
