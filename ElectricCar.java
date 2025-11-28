public class ElectricCar extends Car {


    private int batteryCapacityKwh;

    public ElectricCar(String registration) {
        super(registration, VehicleSize.MEDIUM);
        this.batteryCapacityKwh = 60;
    }

    public ElectricCar(String registration, int batteryCapacityKwh) {
        super(registration, VehicleSize.MEDIUM);
        this.batteryCapacityKwh = batteryCapacityKwh;
    }

    public int getBatteryCapacityKwh() {
        return batteryCapacityKwh;
    }

    @Override
    public String toString() {
        return super.toString() + " (EV, " + batteryCapacityKwh + "kWh)";
    }

    @Override
    public ParkingZone getPreferredZone() {
        return ParkingZone.ELECTRIC_ONLY;
    }
}
