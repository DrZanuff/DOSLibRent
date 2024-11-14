package App;

import Classes.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AuthorCSVData authorCSVData = new AuthorCSVData();
        ArrayList<Author> authors = authorCSVData.readAuthorsCSV();
        AuthorRepository authorsRepository = new AuthorRepository(authors);

        BookCSVData bookCSVData = new BookCSVData();
        ArrayList<BookCSVLine> books = bookCSVData.readBooksCSV();
        BookRepository bookRepository = new BookRepository(books,authorsRepository);
    }
}
