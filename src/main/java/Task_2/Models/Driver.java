package Task_2.Models;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    private UUID id;
    private String fullName;
    private int experienceYears;
    private boolean isAvailable;
    private double balance;
}