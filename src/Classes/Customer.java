package Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Customer extends Person {
    private final UUID id;
    private final ArrayList<Loan> loans;
    private final String email;

    public Customer(String name, LocalDate birth, String email) {
        super(name, birth);
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.loans = new ArrayList<>();
        this.email = email;
        this.id = UUID.randomUUID();
    }

    public ArrayList<Loan> registerNewLoan(Loan loan) {
        loans.add(loan);

        return new ArrayList<>(loans);
    }

    public ArrayList<Loan> getLoans() {
        return new ArrayList<>(loans);
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
