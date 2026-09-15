package Task_2.Services;

import Task_2.Models.*;

import java.util.*;

public class AutobaseService {
    public List<Vehicle> vehicles = new ArrayList<>();
    public List<Driver> drivers = new ArrayList<>();
    public List<Trip> trips = new ArrayList<>();
    private final Random random = new Random();

    public Trip createTrip(CargoOrder order) {
        Vehicle bestVehicle = vehicles.stream()
                .filter(v -> v.getStatus() == VehicleStatus.AVAILABLE)
                .filter(v -> v.getCapacityTons() >= order.getCargoWeightTons())
                .min(Comparator.comparingDouble(v -> v.getCapacityTons() - order.getCargoWeightTons()))
                .orElse(null);

        if (bestVehicle == null) {
            System.out.println("Немає підходящого вільного авто для замовлення: " + order.getDestination());
            return null;
        }

        int requiredExperience = order.getCargoType().getMinExperienceYears()
                + (bestVehicle.getDrivingDifficulty() > 3 ? 2 : 0)
                + (order.getDistanceKm() > 500 ? 1 : 0);

        Driver assignedDriver = drivers.stream()
                .filter(Driver::isAvailable)
                .filter(d -> d.getExperienceYears() >= requiredExperience)
                .findFirst()
                .orElse(null);

        if (assignedDriver == null) {
            System.out.println("Немає вільного водія зі стажем >= " + requiredExperience + " років для авто " + bestVehicle.getModel());
            return null;
        }

        bestVehicle.setStatus(VehicleStatus.ON_TRIP);
        assignedDriver.setAvailable(false);

        double payout = order.getDistanceKm() * 15.0 + (assignedDriver.getExperienceYears() * 100);

        Trip trip = new Trip(UUID.randomUUID(), order, bestVehicle, assignedDriver, TripStatus.CREATED, payout, false);
        trips.add(trip);

        System.out.printf("Рейс призначено: Водій %s на %s -> %s (Оплата: %.2f грн)%n",
                assignedDriver.getFullName(), bestVehicle.getModel(), order.getDestination(), payout);
        return trip;
    }

    public void executeTrip(Trip trip) {
        trip.setStatus(TripStatus.IN_PROGRESS);
        System.out.println("\nРейс до " + trip.getOrder().getDestination() + " розпочато");

        boolean hasBrokenDown = random.nextInt(100) < 30;

        if (hasBrokenDown) {
            trip.getVehicle().setStatus(VehicleStatus.BROKEN);
            requestRepair(trip);
            trip.setStatus(TripStatus.FAILED_BROKEN);
            System.out.println("⚠Автомобіль зламався у дорозі! Оформлено заявку на ремонт.");
        } else {
            completeTrip(trip, VehicleStatus.AVAILABLE);
        }
    }

    public void requestRepair(Trip trip) {
        trip.setRepairRequested(true);
        trip.getVehicle().setStatus(VehicleStatus.IN_REPAIR);
        System.out.println("Автомобіль " + trip.getVehicle().getModel() + " відправлено в ремонт.");
    }

    public void completeTrip(Trip trip, VehicleStatus finalVehicleState) {
        trip.setStatus(TripStatus.COMPLETED);

        Driver driver = trip.getDriver();
        driver.setBalance(driver.getBalance() + trip.getPaymentAmount());

        driver.setAvailable(true);
        trip.getVehicle().setStatus(finalVehicleState);

        System.out.printf("Рейс завершено! Водій %s отримав %.2f грн. Баланс: %.2f грн.%n",
                driver.getFullName(), trip.getPaymentAmount(), driver.getBalance());
        System.out.println("Стан авто " + trip.getVehicle().getModel() + ": " + finalVehicleState);
    }
}