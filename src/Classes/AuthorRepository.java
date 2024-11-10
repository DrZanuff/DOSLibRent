package Classes;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class AuthorRepository {
    private final ArrayList<Author> authors;

    public AuthorRepository(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public Optional<Author> findByID(UUID id) {
        return authors.stream()
                .filter(author -> author.getId().equals(id))
                .findFirst();
    }

    public ArrayList<Author> findManyByName(String authorName) {
        return authors.stream().filter(author -> author.getName().contains(authorName))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
