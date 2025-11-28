import java.time.LocalDateTime;

public sealed interface Vehicle permits Car, Motorbike {


    String getRegistration();

    VehicleSize getSize();

    ParkingZone getPreferredZone();

    default String description() {
        return getClass().getSimpleName() + " (" + getRegistration() + ")";
    }

    default LocalDateTime defaultEntryTime() {
        return LocalDateTime.now();
    }
}
