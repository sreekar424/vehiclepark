import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class ParkingLot {

    private final String name;
    private final Vehicle[] bays;
    private final List<ParkingTicket> activeTickets;
    private final Billable billingStrategy;

    public ParkingLot(String name, int capacity, Billable billingStrategy) {
        this.name = name;
        this.bays = new Vehicle[capacity];
        this.billingStrategy = billingStrategy;
        this.activeTickets = new ArrayList<>();
    }

    public List<Vehicle> getOccupiedVehicles() {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : bays) {
            if (v != null) {
                result.add(v);
            }
        }
        return new ArrayList<>(result);
    }

    public ParkingTicket parkVehicle(Vehicle vehicle) throws ParkingFullException {
        var now = vehicle.defaultEntryTime();
        return parkVehicle(vehicle, now);
    }

    public ParkingTicket parkVehicle(Vehicle vehicle, LocalDateTime entryTime) throws ParkingFullException {
        int index = findFreeBay();
        if (index == -1) {
            throw new ParkingFullException("No free bay for " + vehicle.getRegistration());
        }
        bays[index] = vehicle;
        String id = UUID.randomUUID().toString();
        ParkingTicket ticket = new ParkingTicket(id, vehicle, entryTime, entryTime);
        activeTickets.add(ticket);
        logEvents("Vehicle parked: " + vehicle.description());
        return ticket;
    }

    public void logEvents(String... events) {
        for (String e : events) {
            System.out.println("[LOG] " + e);
        }
    }

    private int findFreeBay() {
        for (int i = 0; i < bays.length; i++) {
            if (bays[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public Money exitVehicle(String registration) {
        Vehicle vehicle = null;
        int indexFound = -1;

        for (int i = 0; i < bays.length; i++) {
            Vehicle v = bays[i];
            if (v != null && Objects.equals(v.getRegistration(), registration)) {
                vehicle = v;
                indexFound = i;
                break;
            }
        }

        if (vehicle == null) {
            throw new InvalidTicketException("No vehicle with reg " + registration + " found");
        }

        bays[indexFound] = null;
        LocalDateTime now = LocalDateTime.now();

        ParkingTicket matching = null;
        for (ParkingTicket t : activeTickets) {
            if (t.vehicle().getRegistration().equals(registration)) {
                matching = new ParkingTicket(t.id(), t.vehicle(), t.entryTime(), now);
                activeTickets.remove(t);
                break;
            }
        }

        if (matching == null) {
            throw new InvalidTicketException("No active ticket for vehicle " + registration);
        }

        Money charge = billingStrategy.calculateCharge(matching);
        Billable.printHeader();
        billingStrategy.printBill(matching);
        return charge;
    }

    public List<Vehicle> findVehicles(Predicate<Vehicle> filter) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : bays) {
            if (v != null && filter.test(v)) {
                result.add(v);
            }
        }
        return result;
    }

    public void printAllVehicles() {
        List<Vehicle> vehicles = getOccupiedVehicles();
        vehicles.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "ParkingLot{name='" + name + "', capacity=" + bays.length + "}";
    }

    public void tryToClearArray(Vehicle[] externalArray) {
        externalArray = new Vehicle[0];
    }

    public void addInitialVehicles(List<Vehicle> vehicles) throws ParkingFullException {
        List<Vehicle> safeCopy = new ArrayList<>(vehicles);
        for (Vehicle v : safeCopy) {
            parkVehicle(v);
        }
    }

    public void printBayArray() {
        System.out.println("Bay state: " + Arrays.toString(bays));
    }
}
