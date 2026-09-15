package Task_2.Models;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    private UUID id;
    private CargoOrder order;
    private Vehicle vehicle;
    private Driver driver;
    private TripStatus status;
    private double paymentAmount;
    private boolean repairRequested;
}