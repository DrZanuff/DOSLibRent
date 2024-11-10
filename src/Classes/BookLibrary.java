package Classes;

import java.util.ArrayList;

public class BookLibrary {
    private final ArrayList<Book> books;
    private final AuthorRepository authors;
    private final ArrayList<Customer> customers;
    private final ArrayList<Loan> loans;

    public BookLibrary(
            ArrayList<Book> books,
            AuthorRepository authors,
            ArrayList<Customer> customers,
            ArrayList<Loan> loans
    ) {
        if (books == null) {
            throw new IllegalArgumentException("Books cannot be null");
        }
        if (authors == null) {
            throw new IllegalArgumentException("Authors cannot be null");
        }
        if (customers == null) {
            throw new IllegalArgumentException("Customers cannot be null");
        }
        if (loans == null) {
            throw new IllegalArgumentException("Loans cannot be null");
        }

        this.books = books;
        this.authors = authors;
        this.customers = customers;
        this.loans = loans;
    }


    public ArrayList<Book> getBooks() {
        return new ArrayList<Book>(books);
    }

    public ArrayList<Author> getAuthors() {
        return new ArrayList<Author>(authors.getAuthors());
    }

    public ArrayList<Customer> getCustomers() {
        return new ArrayList<Customer>(customers);
    }

    public ArrayList<Loan> getLoans() {
        return new ArrayList<Loan>(loans);
    }
}
