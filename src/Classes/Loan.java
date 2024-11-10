package Classes;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    private final UUID id;
    private final LocalDate loanDate;
    private final LocalDate expirationDate;
    private final Book book;
    private final Customer customer;

    public Loan(Book book, Customer customer, LocalDate expirationDate) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (expirationDate == null || expirationDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiration date cannot be null or in the past");
        }
        this.id = UUID.randomUUID();
        this.loanDate = LocalDate.now();
        this.expirationDate = expirationDate;
        this.book = book;
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Book getBook() {
        return book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }
}
