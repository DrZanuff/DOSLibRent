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

public class BookCSVData {
    private static final Path originFile = Paths.get("books.csv");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String header = "id,title,sku,isAvailable,registerDate,lastUpdate,authorId";

    public ArrayList<BookCSVLine> readBooksCSV() {
        ArrayList<BookCSVLine> books = new ArrayList<>();

        try {
            if (Files.notExists(originFile)) {
                Files.writeString(originFile, header + "\n");
                System.out.println("File not found: 'books.csv'. New file created.");
                return books;
            }

            List<String> lines = Files.readAllLines(originFile);

            for (String line : lines) {
                if (line.startsWith(header)) continue;

                String[] columns = line.split(",");

                if (columns.length != 7) {
                    System.out.println("Invalid Line (incorrect column numbers): " + line);
                    continue;
                }

                try {
                    UUID id = UUID.fromString(columns[0]);
                    String title = columns[1];
                    String sku = columns[2];
                    boolean isAvailable = Boolean.parseBoolean(columns[3]);
                    LocalDate registerDate = LocalDate.parse(columns[4].trim(), dateFormatter);
                    LocalDate lastUpdate = LocalDate.parse(columns[5].trim(), dateFormatter);
                    UUID authorId = UUID.fromString(columns[6]);

                    LocalDate birth = LocalDate.parse(columns[2].trim(), dateFormatter);

                    books.add(new BookCSVLine(id, title, sku, isAvailable, registerDate, lastUpdate, authorId));
                } catch (Exception e) {
                    System.out.println("Error processing line " + line + "Error: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return books;
    }

    public boolean storeBooksCSV(ArrayList<Book> books) {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(header);

        for (Book book : books) {
            String line = book.getID() + "," + book.getTitle() + "," + book.getSku() + "," + book.isAvailable() + "," + book.getRegisterDate() + "," + book.getLastUpdate() + "," + book.getAuthor() + "\n";
            lines.add(line);
        }

        try {
            Files.write(originFile, lines);
            System.out.println("Books saved to 'authors.csv' successfully.");
        } catch (IOException e) {
            System.out.println("Error saving books to file: " + e.getMessage());
            return false;
        }

        return true;
    }

}
