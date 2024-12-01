package Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookRepository {
    private final ArrayList<BookCSVLine> bookRegistries;
    private final AuthorRepository authorRepository;

    public BookRepository(ArrayList<BookCSVLine> bookRegistries, AuthorRepository authorRepository) {
        this.bookRegistries = bookRegistries;
        this.authorRepository = authorRepository;
    }

    private Book getBookData(BookCSVLine bookData, Author authorData) {
        return new Book(
                bookData.title(),
                bookData.id(),
                bookData.sku(),
                bookData.available(),
                bookData.lastUpdate(),
                bookData.registerDate(),
                authorData,
                bookData.genre()
        );
    }

    private Optional<Author> getBookAuthor(BookCSVLine book) {
        return authorRepository.findByID(book.authorId());
    }

    public Optional<Book> findById(UUID id) {
        Optional<BookCSVLine> bookRegister;
        Optional<Author> bookAuthor;

        bookRegister = bookRegistries.stream().filter(book -> book.id().equals(id)).findFirst();
        if (bookRegister.isEmpty()) {
            return Optional.empty();
        }

        bookAuthor = getBookAuthor(bookRegister.get());
        if (bookAuthor.isEmpty()) {
            return Optional.empty();
        }

        BookCSVLine bookData = bookRegister.get();
        Author authorData = bookAuthor.get();

        return Optional.of(getBookData(bookData, authorData));
    }

    public ArrayList<Book> filterByAuthors(ArrayList<Author> authorList) {
        ArrayList<BookCSVLine> bookDataList = new ArrayList<>();
        ArrayList<Book> bookList = new ArrayList<>();

        for (Author author : authorList) {
            ArrayList<BookCSVLine> bookRegister = bookRegistries.stream()
                    .filter(book -> book.authorId().equals(author.getId()))
                    .collect(Collectors.toCollection(ArrayList::new));

            bookDataList.addAll(bookRegister);
        }

        bookDataList.forEach(bookData -> {
            Optional<Book> bookById = findById(bookData.id());

            if (bookById.isPresent()) {
                bookList.add(bookById.get());
            }
        });

        return bookList;
    }

    public ArrayList<Book> filterBooksByTitle(String title) {
        ArrayList<BookCSVLine> bookMatches = bookRegistries
                .stream()
                .filter(bookData -> bookData.title().contains(title))
                .collect(Collectors.toCollection(ArrayList::new));

        return bookMatches.stream().map(book -> {
            Optional<Author> bookAuthor = getBookAuthor(book);

            return getBookData(book, bookAuthor.orElseGet(Book::noAuthor));

        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Book> filterBooksByGenre(String genre) {
        ArrayList<BookCSVLine> bookMatches = bookRegistries
                .stream()
                .filter(bookData -> bookData.genre().contains(genre))
                .collect(Collectors.toCollection(ArrayList::new));

        return bookMatches.stream().map(book -> {
            Optional<Author> bookAuthor = getBookAuthor(book);

            return getBookData(book, bookAuthor.orElseGet(Book::noAuthor));
        }).collect(Collectors.toCollection((ArrayList::new)));
    }

    public ArrayList<Book> filterBooksByMostRecent(int days) {
        LocalDate dateThreshold = LocalDate.now().minusDays(days);

        ArrayList<BookCSVLine> bookMatches = bookRegistries
                .stream()
                .filter(bookData -> bookData.registerDate().isAfter(dateThreshold))
                .collect(Collectors.toCollection(ArrayList::new));

        return bookMatches.stream().map(book -> {
            Optional<Author> bookAuthor = getBookAuthor(book);

            return getBookData(book, bookAuthor.orElseGet(Book::noAuthor));
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Book> filterByAvailability() {
        ArrayList<BookCSVLine> bookMatches = bookRegistries
                .stream()
                .filter(BookCSVLine::available)
                .collect(Collectors.toCollection(ArrayList::new));

        return bookMatches.stream().map(book -> {
            Optional<Author> bookAuthor = getBookAuthor(book);

            return getBookData(book, bookAuthor.orElseGet(Book::noAuthor));
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public enum RegistrationStatus {
        SUCCESS,
        AUTHOR_NOT_FOUND
    }

    public RegistrationStatus registerNewBook(String title, String sku, UUID authorID, String genre) {
        Optional<Author> author = authorRepository.findByID(authorID);

        if (title == null || title.isBlank() || sku == null || sku.isBlank() || genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("Invalid book details provided");
        }

        if (author.isEmpty()) {
            return RegistrationStatus.AUTHOR_NOT_FOUND;
        }

        LocalDate registerDate = LocalDate.now();
        BookCSVLine bookData = new BookCSVLine(
                UUID.randomUUID(),
                title,
                sku,
                true,
                registerDate,
                registerDate,
                authorID,
                genre);

        boolean added = bookRegistries.add(bookData);
        if (!added) {
            throw new IllegalStateException("Failed to add book to the registry");
        }

        return RegistrationStatus.SUCCESS;
    }

    // Create Loan Repository and LoanCSVData
    // Main.Java will be BookLibrary class with repositories as dep
}
