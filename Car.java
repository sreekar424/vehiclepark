public non-sealed class Car implements Vehicle {


    private String registration;
    private VehicleSize size;
    private ParkingZone preferredZone;

    public Car(String registration) {
        this(registration, VehicleSize.MEDIUM);
    }

    public Car(String registration, VehicleSize size) {
        this.registration = registration;
        this.size = size;
        this.preferredZone = ParkingZone.REGULAR;
    }

    @Override
    public String getRegistration() {
        return registration;
    }

    @Override
    public VehicleSize getSize() {
        return size;
    }

    @Override
    public ParkingZone getPreferredZone() {
        return preferredZone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Car[reg=")
          .append(registration)
          .append(", size=")
          .append(size)
          .append("]");
        return sb.toString();
    }
}
