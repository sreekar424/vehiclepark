import java.util.Scanner;

public class CarParkApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ParkingLot lot = new ParkingLot("Campus Car Park", 5, new SimpleTariffCalculator());

        System.out.println("====================================");
        System.out.println("      WELCOME TO CAMPUS PARKING     ");
        System.out.println("====================================");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Park a new vehicle");
            System.out.println("2. View occupied vehicles");
            System.out.println("3. Find only electric vehicles");
            System.out.println("4. Exit a vehicle (Generate Bill)");
            System.out.println("5. Show parking availability");
            System.out.println("6. Exit program");

            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1 -> {
                    System.out.println("\nSelect Vehicle Type:");
                    System.out.println("1. Car");
                    System.out.println("2. Electric Car");
                    System.out.println("3. Motorbike");
                    System.out.print("Enter choice: ");
                    int type = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter registration: ");
                    String reg = sc.nextLine();

                    Vehicle v = null;

                    if (type == 1) {
                        v = new Car(reg, VehicleSize.MEDIUM);
                    }
                    else if (type == 2) {
                        System.out.print("Enter battery capacity (kWh): ");
                        int cap = sc.nextInt();
                        sc.nextLine();
                        v = new ElectricCar(reg, cap);  // <-- matches YOUR constructor
                    }
                    else if (type == 3) {
                        v = new Motorbike(reg);
                    }

                    try {
                        ParkingTicket t = lot.parkVehicle(v);
                        System.out.println("\nVehicle successfully parked!");
                        System.out.println("Ticket ID: " + t.id());
                    } catch (ParkingFullException e) {
                        System.out.println("⚠️ Parking Full! Cannot add vehicle.");
                    }
                }

                case 2 -> {
                    System.out.println("\n=== Currently Parked Vehicles ===");
                    lot.printAllVehicles();
                }

                case 3 -> {
                    System.out.println("\n=== Only Electric Vehicles ===");
                    lot.findVehicles(v -> v instanceof ElectricCar)
                            .forEach(System.out::println);
                }

                case 4 -> {
                    System.out.print("\nEnter registration to exit: ");
                    String r = sc.nextLine();
                    try {
                        Money m = lot.exitVehicle(r);
                        System.out.println("Customer must pay: " + m);
                    } catch (Exception ex) {
                        System.out.println("⚠️ " + ex.getMessage());
                    }
                }

                case 5 -> {
                    int occupied = lot.getOccupiedVehicles().size();
                    int free = 5 - occupied;
                    System.out.println("\nTotal Bays: 5");
                    System.out.println("Occupied: " + occupied);
                    System.out.println("Free: " + free);
                }

                case 6 -> {
                    System.out.println("\nThank you! Exiting...");
                    return;
                }

                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
