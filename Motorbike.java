public non-sealed class Motorbike implements Vehicle {


    private String registration;

    public Motorbike(String registration) {
        this.registration = registration;
    }

    @Override
    public String getRegistration() {
        return registration;
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.SMALL;
    }

    @Override
    public ParkingZone getPreferredZone() {
        return ParkingZone.MOTORBIKE;
    }

    @Override
    public String toString() {
        return "Motorbike[reg=" + registration + "]";
    }
}
