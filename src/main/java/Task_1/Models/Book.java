package Task_1.Models;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Book extends LibraryItem {
    private String author;
    private String genre;
    private String synopsis;
}