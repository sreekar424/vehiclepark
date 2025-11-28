import java.time.Duration;
import java.time.LocalDateTime;

public record ParkingTicket(
        String id,
        Vehicle vehicle,
        LocalDateTime entryTime,
        LocalDateTime exitTime
) {

    public ParkingTicket {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Ticket id cannot be blank");
        }
    }

    public Duration duration() {
        return Duration.between(entryTime, exitTime);
    }
}
