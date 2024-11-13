package Classes;

import java.time.LocalDate;
import java.util.UUID;

public record BookCSVLine(
        UUID id,
        String title,
        String sku,
        boolean available,
        LocalDate registerDate,
        LocalDate lastUpdate,
        UUID authorId,
        String genre) {
}