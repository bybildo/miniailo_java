package Task_1.Models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class LibraryItem {
    private UUID id;
    private String title;
    private LocalDateTime publicationData;
    private String publisher;
    private int pageCount;
}
