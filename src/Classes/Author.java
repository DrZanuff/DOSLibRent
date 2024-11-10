package Classes;

import java.time.LocalDate;
import java.util.UUID;

public final class Author extends Person {
    private final UUID id;

    public Author(String name, LocalDate birth, UUID id) {
        super(name, birth);
        this.id = id == null ? UUID.randomUUID() : id;
    }

    public UUID getId() {
        return id;
    }
}
