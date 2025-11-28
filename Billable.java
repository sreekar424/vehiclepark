public interface Billable {

    Money calculateCharge(ParkingTicket ticket);

    default void printBill(ParkingTicket ticket) {
        Money charge = calculateCharge(ticket);
        logLine("Ticket " + ticket.id() + " total: " + charge);
    }

    private void logLine(String message) {
        System.out.println("[BILL] " + message);
    }

    static void printHeader() {
        System.out.println("=== Campus Car Park Bill ===");
    }
}
