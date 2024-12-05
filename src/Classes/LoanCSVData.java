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

public class LoanCSVData {
    private static final Path originFile = Paths.get("loans.csv");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String header = "id,loanDate,expirationDate,bookId,customerId";

    public ArrayList<LoanCSVLine> readLoandsCSV() {
        ArrayList<LoanCSVLine> loans = new ArrayList<>();

        try {
            if (Files.notExists(originFile)) {
                Files.writeString(originFile, header + "\n");
                System.out.println("File not found: 'loans.csv'. New file created;");
                return loans;
            }

            List<String> lines = Files.readAllLines(originFile);

            for (String line : lines) {
                if (line.startsWith(header)) continue;
                ;

                String[] columns = line.split(",");

                if (columns.length != 5) {
                    System.out.println("Invalid Line (incorrect column numbers): " + line);
                    continue;
                }

                try {
                    UUID id = UUID.fromString(columns[0]);
                    LocalDate loanDate = LocalDate.parse(columns[1], dateFormatter);
                    LocalDate expirationDate = LocalDate.parse(columns[2], dateFormatter);
                    UUID bookId = UUID.fromString(columns[3]);
                    UUID loanId = UUID.fromString(columns[4]);

                    loans.add(new LoanCSVLine(id, loanDate, expirationDate, bookId, loanId));
                } catch (Exception e) {
                    System.out.println("Error processing line " + line + "Error: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return loans;
    }

    public boolean storeLoansCSV(ArrayList<Loan> loans) {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(header);

        for (Loan loan : loans) {
            String line =
                    loan.getId() + "," + loan.getLoanDate() + "," + loan.getExpirationDate() + "," +
                            loan.getBook().getID() + "," + loan.getCustomer().getId() + "\n";
            lines.add(line);
        }

        try {
            Files.write(originFile, lines);
            System.out.println("Loans saved to 'loans.csv' successfully.");
        } catch (IOException e) {
            System.out.println("Error saving loans to file: " + e.getMessage());
            return false;
        }

        return true;
    }
}
