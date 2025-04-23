import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GarbageCreator {
    public static void main(String[] args) {
        String filePath = "path/to/large/file.txt"; // Replace with the path to a large text file
        String content = "";

        try {
            long startTime = System.currentTimeMillis();
            for (String line : Files.readAllLines(Paths.get(filePath))) {
                content += line + "\n"; // Concatenate using + operator
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken: " + (endTime - startTime) + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Content length: " + content.length());
    }
}