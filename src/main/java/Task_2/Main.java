package Task_2;

import Task_2.Models.*;
import Task_2.Services.AutobaseService;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        AutobaseService autobase = new AutobaseService();

        autobase.vehicles.addAll(List.of(
                new Vehicle(UUID.randomUUID(), "Газель Next", 2.5, 2, VehicleStatus.AVAILABLE),
                new Vehicle(UUID.randomUUID(), "MAN TGL", 8.0, 3, VehicleStatus.AVAILABLE),
                new Vehicle(UUID.randomUUID(), "Volvo FH16", 22.0, 5, VehicleStatus.AVAILABLE)
        ));

        autobase.drivers.addAll(List.of(
                new Driver(UUID.randomUUID(), "Іваненко Василь (1 рік)", 1, true, 0.0),
                new Driver(UUID.randomUUID(), "Петренко Петро (4 роки)", 4, true, 0.0),
                new Driver(UUID.randomUUID(), "Коваль Сергій (9 років)", 9, true, 0.0)
        ));

        System.out.println("Система автобаза: початок зміни");

        for (int i = 1; i <= 3; i++) {
            CargoOrder order = generateRandomOrder(i);
            System.out.println("\nНова заявка #" + i + ": " + order.getDestination() +
                    " | Вага: " + order.getCargoWeightTons() + " т | Тип: " + order.getCargoType() +
                    " | Відстань: " + order.getDistanceKm() + " км");

            Trip trip = autobase.createTrip(order);
            if (trip != null) {
                autobase.executeTrip(trip);
            }
        }

        System.out.println("\nПідсумок дня");
        System.out.println("\nСтан автомобілів");
        autobase.vehicles.forEach(v -> System.out.println(v.getModel() + " -> " + v.getStatus()));

        System.out.println("\nБаланс водіїв");
        autobase.drivers.forEach(d -> System.out.println(d.getFullName() + " | Вільний: " + d.isAvailable() + " | Заробіток: " + d.getBalance() + " грн"));
    }

    private static CargoOrder generateRandomOrder(int index) {
        String[] cities = {"Київ", "Одеса", "Львів", "Дніпро", "Харків"};
        CargoType[] types = CargoType.values();

        return new CargoOrder(
                UUID.randomUUID(),
                cities[random.nextInt(cities.length)],
                types[random.nextInt(types.length)],
                Math.round((1.0 + random.nextDouble() * 15.0) * 10.0) / 10.0,
                (double) (150 + random.nextInt(600))
        );
    }
}