package Classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthorCSVData {
    private static final Path originFile = Paths.get("authors.csv");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String header = "id,name,birth";

    public ArrayList<Author> readAuthorsCSV() {
        ArrayList<Author> authors = new ArrayList<>();

        try {
            if (Files.notExists(originFile)) {
                Files.writeString(originFile, header + "\n");
                System.out.println("File not found: 'authors.csv'. New file created.");
                return authors;
            }

            List<String> lines = Files.readAllLines(originFile);

            for (String line : lines) {
                if (line.startsWith(header)) continue;

                String[] columns = line.split(",");

                if (columns.length != 3) {
                    System.out.println("Invalid Line (incorrect column numbers): " + line);
                    continue;
                }

                try {
                    UUID id = UUID.fromString(columns[0]);
                    String name = columns[1];
                    LocalDate birth = LocalDate.parse(columns[2].trim(), dateFormatter);

                    authors.add(new Author(name, birth, id));
                } catch (Exception e) {
                    System.out.println("Error processing line " + line + "Error: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return authors;
    }

    public boolean storeAuthorCSV(ArrayList<Author> authors) {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(header);

        for (Author author : authors) {
            String line = author.getId() + "," + author.getName() + "," + author.getBirth() + "\n";
            lines.add(line);
        }

        try {
            Files.write(originFile, lines);
            System.out.println("Authors saved to 'authors.csv' successfully.");
        } catch (IOException e) {
            System.out.println("Error saving authors to file: " + e.getMessage());
            return false;
        }

        return true;
    }
}
