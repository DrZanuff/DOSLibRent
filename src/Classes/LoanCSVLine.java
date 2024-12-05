package Classes;

import java.time.LocalDate;
import java.util.UUID;

public record LoanCSVLine(
        UUID id,
        LocalDate loanDate,
        LocalDate expirationDate,
        UUID bookId,
        UUID customerId
) {
}
