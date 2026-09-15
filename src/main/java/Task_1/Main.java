package Task_1;

import Task_1.Models.*;
import Task_1.Services.LibraryService;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        LibraryService service = new LibraryService();

        System.out.println("\nТестова ініціалізація");
        initTestData(service);
        service.printCatalog(null);

        System.out.println("\nДодавання випадкового об'єкта");
        LibraryItem randomItem = generateRandomItem();
        service.addLibraryItem(randomItem);
        System.out.println("Згенеровано та додано:\n" + randomItem);

        System.out.println("\nГрупування каталогу");
        service.printCatalog(GroupBy.TYPE);
        service.printCatalog(GroupBy.PUBLICATION_YEAR);
        service.printCatalog(GroupBy.CREATOR);

        System.out.println("\nПошук за критеріями");

        System.out.println("-> Пошук за назвою (\"Кобзар\"):");
        service.findByTitle("Кобзар").forEach(System.out::println);

        System.out.println("\n-> Пошук за видавництвом (\"А-БА-БА-ГА-ЛА-МА-ГА\"):");
        service.findByPublisher("А-БА-БА-ГА-ЛА-МА-ГА").forEach(System.out::println);

        System.out.println("\n-> Пошук за роком (2024):");
        service.findByPublicationYear(2024).forEach(System.out::println);

        System.out.println("\n-> Пошук за автором (\"Шевченко\"):");
        service.findByAuthor("Шевченко").forEach(System.out::println);

        System.out.println("\nКоригування об'єкта");
        LibraryItem itemToEdit = service.libraryItems.getFirst();
        System.out.println("До редагування: " + itemToEdit.getTitle() + " (Видавництво: " + itemToEdit.getPublisher() + ")");

        if (itemToEdit instanceof Book bookToEdit) {
            Book updatedBook = new Book();
            updatedBook.setId(bookToEdit.getId());
            updatedBook.setTitle(bookToEdit.getTitle() + " [Ювілейне видання]");
            updatedBook.setPublicationData(LocalDateTime.now());
            updatedBook.setPublisher("Видавництво Старого Лева");
            updatedBook.setPageCount(bookToEdit.getPageCount() + 20);
            updatedBook.setAuthor(bookToEdit.getAuthor());
            updatedBook.setGenre("Класика");
            updatedBook.setSynopsis("Оновлений тираж з ілюстраціями");

            service.updateLibraryItem(updatedBook);
            System.out.println("Після оновлення: " + updatedBook.getTitle() + " (Видавництво: " + updatedBook.getPublisher() + ")");
        }

        System.out.println("\nВидалення об'єкта");
        System.out.println("\nВидалення об'єкта");
        UUID idToDelete = service.libraryItems.getLast().getId();
        int sizeBefore = service.libraryItems.size();
        service.removeLibraryItem(idToDelete);
        System.out.println("Кількість елементів до: " + sizeBefore + ", після видалення: " + service.libraryItems.size());
    }

    private static void initTestData(LibraryService service) {
        Book book1 = new Book();
        book1.setId(UUID.randomUUID());
        book1.setTitle("Кобзар");
        book1.setPublicationData(LocalDateTime.of(2021, 5, 10, 0, 0));
        book1.setPublisher("А-БА-БА-ГА-ЛА-МА-ГА");
        book1.setPageCount(400);
        book1.setAuthor("Тарас Шевченко");
        book1.setGenre("Поезія");
        book1.setSynopsis("Збірка поетичних творів");
        service.addBook(book1);

        Book book2 = new Book();
        book2.setId(UUID.randomUUID());
        book2.setTitle("Тіні забутих предків");
        book2.setPublicationData(LocalDateTime.of(2023, 3, 15, 0, 0));
        book2.setPublisher("Фоліо");
        book2.setPageCount(180);
        book2.setAuthor("Михайло Коцюбинський");
        book2.setGenre("Повість");
        book2.setSynopsis("Історія кохання Івана та Марічки");
        service.addBook(book2);

        Newspaper newspaper = new Newspaper();
        newspaper.setId(UUID.randomUUID());
        newspaper.setTitle("Урядовий кур'єр");
        newspaper.setPublicationData(LocalDateTime.of(2024, 9, 1, 8, 30));
        newspaper.setPublisher("Преса України");
        newspaper.setPageCount(16);
        newspaper.setIssueNumber(178);
        newspaper.setQuantity(10000);
        newspaper.setTopicAuthor(Map.of(
                "Політика", "Олександр Гончар",
                "Економіка", "Марія Коваль"
        ));
        service.addMagazine(newspaper);

        Book work1 = new Book();
        work1.setId(UUID.randomUUID());
        work1.setTitle("Заповіт");
        work1.setAuthor("Тарас Шевченко");
        work1.setPageCount(2);

        Book work2 = new Book();
        work2.setId(UUID.randomUUID());
        work2.setTitle("Каменярі");
        work2.setAuthor("Іван Франко");
        work2.setPageCount(3);

        Almanac almanac = new Almanac();
        almanac.setId(UUID.randomUUID());
        almanac.setTitle("Альманах класичної лірики");
        almanac.setPublicationData(LocalDateTime.of(2024, 1, 10, 12, 0));
        almanac.setPublisher("А-БА-БА-ГА-ЛА-МА-ГА");
        almanac.setIncludedWorks(new ArrayList<>(List.of(work1, work2)));
        service.addAlmanac(almanac);
    }

    private static LibraryItem generateRandomItem() {
        int type = random.nextInt(3);
        UUID id = UUID.randomUUID();
        int randomYear = 2018 + random.nextInt(9);
        LocalDateTime date = LocalDateTime.of(randomYear, random.nextInt(12) + 1, random.nextInt(28) + 1, 10, 0);

        return switch (type) {
            case 0 -> {
                Book book = new Book();
                book.setId(id);
                book.setTitle("Випадкова книга #" + random.nextInt(1000));
                book.setPublicationData(date);
                book.setPublisher("Фабула");
                book.setPageCount(150 + random.nextInt(300));
                book.setAuthor("Автор " + (char) ('А' + random.nextInt(20)));
                book.setGenre("Фантастика");
                book.setSynopsis("Згенерований синопсис книги");
                yield book;
            }
            case 1 -> {
                Newspaper news = new Newspaper();
                news.setId(id);
                news.setTitle("Ранковий вісник #" + random.nextInt(500));
                news.setPublicationData(date);
                news.setPublisher("МедіаХолдинг");
                news.setPageCount(8 + random.nextInt(24));
                news.setIssueNumber(random.nextInt(300) + 1);
                news.setQuantity(5000);
                news.setTopicAuthor(Map.of("Новини дня", "Репортер " + random.nextInt(10)));
                yield news;
            }
            default -> {
                Almanac almanac = new Almanac();
                almanac.setId(id);
                almanac.setTitle("Збірник оповідань #" + random.nextInt(100));
                almanac.setPublicationData(date);
                almanac.setPublisher("Либідь");

                Book shortWork = new Book();
                shortWork.setId(UUID.randomUUID());
                shortWork.setTitle("Есе #" + random.nextInt(50));
                shortWork.setAuthor("Есеїст " + (char) ('А' + random.nextInt(20)));
                shortWork.setPageCount(40 + random.nextInt(60));

                almanac.setIncludedWorks(new ArrayList<>(List.of(shortWork)));
                yield almanac;
            }
        };
    }
}