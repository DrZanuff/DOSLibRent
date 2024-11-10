package Classes;

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
                authorData
        );
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
                    .filter(book -> book.id().equals(author.getId()))
                    .collect(Collectors.toCollection(ArrayList::new));

            bookDataList.addAll(bookRegister);
        }

        bookDataList.forEach(bookData -> {
            Optional<Book> bookById = findById(bookData.id());

            if (bookById.isPresent()){
                bookList.add(bookById.get());
            }
        });

        return bookList;
    }

    // filterByTitle
    // Adicionar genre
}
