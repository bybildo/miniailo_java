package Task_1.Models;

import lombok.*;
import java.util.Map;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Newspaper extends LibraryItem {
    private int issueNumber;
    private int quantity;
    private Map<String, String> TopicAuthor;
}