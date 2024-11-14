package Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookRepository {
    private final ArrayList<BookCSVLine> bookRegistries;
    private final ArrayList<Author> authors;

    public BookRepository(ArrayList<BookCSVLine> bookRegistries, ArrayList<Author> authors) {
        this.bookRegistries = bookRegistries;
        this.authors = authors;
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
        return authors.stream().filter(author -> author.getId().equals(book.authorId())).findFirst();
    }

    public Optional<Book> findById(UUID id) {
        Optional<BookCSVLine> bookRegister;
        Optional<Author> bookAuthor;

        bookRegister = bookRegistries.stream().filter(book -> book.id().equals(id)).findFirst();
        if (bookRegister.isEmpty()) {
            return Optional.empty();
        }

        bookAuthor = authors.stream().filter(author -> author.getId().equals(bookRegister.get().id())).findFirst();
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

        return bookMatches.stream().map(book->{
            Optional<Author> bookAuthor = getBookAuthor(book);

            return getBookData(book, bookAuthor.orElseGet(Book::noAuthor));
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
