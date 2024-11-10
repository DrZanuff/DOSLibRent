package App;

import Classes.Author;
import Classes.AuthorCSVData;
import Classes.AuthorRepository;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AuthorCSVData authorCSVData = new AuthorCSVData();
        ArrayList<Author> authors = authorCSVData.readAuthorsCSV();
        AuthorRepository authorsRepository = new AuthorRepository(authors);



    }
}
