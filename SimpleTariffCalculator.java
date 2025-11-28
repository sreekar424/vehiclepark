import java.time.Duration;

public class SimpleTariffCalculator implements Billable {

    @Override
    public Money calculateCharge(ParkingTicket ticket) {
        Duration d = ticket.duration();
        long minutes = Math.max(d.toMinutes(), 1);

        final double baseRatePerHour = 2.0;
        double hours = minutes / 60.0;

        double multiplier = switch (ticket.vehicle()) {
            case ElectricCar ev -> 0.8;
            case Motorbike mb -> 0.5;
            case Car car -> 1.0;
        };

        double price = hours * baseRatePerHour * multiplier;
        return new Money(price, "EUR");
    }
}
