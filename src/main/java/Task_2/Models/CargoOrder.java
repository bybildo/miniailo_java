package Task_2.Models;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CargoOrder {
    private UUID id;
    private String destination;
    private CargoType cargoType;
    private double cargoWeightTons;
    private double distanceKm;
}