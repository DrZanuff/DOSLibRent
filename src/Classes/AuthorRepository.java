package Classes;

import java.time.LocalDate;
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


    public boolean registerNewAuthor(String name, LocalDate birth) {
        if (name == null || name.isEmpty() || birth == null) {
            throw new IllegalArgumentException("Invalid author details provided");
        }

        Author newAuthor = new Author(name, birth, UUID.randomUUID());

        boolean added = authors.add(newAuthor);

        if (!added) {
            throw new IllegalStateException("Failed to add author to the registry");
        }

        return true;
    }
}
