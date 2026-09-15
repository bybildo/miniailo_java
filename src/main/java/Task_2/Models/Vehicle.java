package Task_2.Models;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private UUID id;
    private String model;
    private double capacityTons;
    private int drivingDifficulty;
    private VehicleStatus status;
}