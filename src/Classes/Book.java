package Classes;

import java.util.UUID;
import java.time.LocalDate;

public class Book {
    private String title;
    private final UUID id;
    private String sku;
    private boolean available;
    private final LocalDate registerDate;
    private LocalDate lastUpdate;
    private Author author;
    private final String genre;

    public Book(
            String title,
            UUID id,
            String sku,
            boolean available,
            LocalDate registerDate,
            LocalDate lastUpdate,
            Author author,
            String genre
    ) {
        this.title = title;
        this.id = id == null ? UUID.randomUUID() : id;
        this.sku = sku;
        this.available = available;
        this.registerDate = registerDate == null ? LocalDate.now() : registerDate;
        this.lastUpdate = lastUpdate == null ? LocalDate.now() : lastUpdate;
        this.author = author;
        this.genre = genre;
    }
    
    public static Author noAuthor() {
        return new Author(null,null,null);
    }

    public UUID getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
        this.lastUpdate = LocalDate.now();

    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }
}
