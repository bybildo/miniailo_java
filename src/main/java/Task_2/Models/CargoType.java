package Task_2.Models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CargoType {
    STANDARD(1),
    PERISHABLE(2),
    FRAGILE(3),
    HAZARDOUS(5);

    private final int minExperienceYears;
}