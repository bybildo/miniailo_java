package Task_1.Models;

import lombok.*;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Almanac extends LibraryItem {
    public List<Book> includedWorks;

    @Override
    public int getPageCount() {
        return includedWorks.stream().mapToInt(book -> book.getPageCount()).sum();
    }
}