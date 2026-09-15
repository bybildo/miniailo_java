package Task_1.Services;

import Task_1.Models.GroupBy;
import Task_1.Models.Almanac;
import Task_1.Models.Book;
import Task_1.Models.LibraryItem;
import Task_1.Models.Newspaper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class LibraryService {
    public List<LibraryItem> libraryItems = new ArrayList<>();

    public void addLibraryItem(LibraryItem libraryItem) {
        libraryItems.add(libraryItem);
    }

    public void addBook(Book book) {
        libraryItems.add(book);
    }

    public void addMagazine(Newspaper magazine) {
        libraryItems.add(magazine);
    }

    public void addAlmanac(Almanac almanac) {
        libraryItems.add(almanac);
    }

    public void removeLibraryItem(UUID id) {
        libraryItems.removeIf(item -> item.getId().equals(id));
    }

    public boolean updateLibraryItem(LibraryItem updatedItem) {
        if (updatedItem == null || updatedItem.getId() == null) {
            return false;
        }

        for (int i = 0; i < libraryItems.size(); i++) {
            if (libraryItems.get(i).getId().equals(updatedItem.getId())) {
                libraryItems.set(i, updatedItem);
                return true;
            }
        }
        return false;
    }

    private String getCreator(LibraryItem item) {
        if (item instanceof Book book) {
            return book.getAuthor() != null ? book.getAuthor() : "Невідомий автор";
        } else if (item instanceof Newspaper news) {
            return "Редакція: " + news.getPublisher();
        } else if (item instanceof Almanac almanac) {
            return almanac.getPublisher() + " " +
                    almanac.getIncludedWorks().stream()
                            .map(Book::getTitle)
                            .collect(Collectors.joining(", "));        }
        return "Невідомо";
    }

    public void printCatalog(GroupBy groupBy) {
        if (libraryItems.isEmpty()) {
            System.out.println("Каталог порожній.");
            return;
        }

        if (groupBy == null) {
            System.out.println("Весь каталог");
            libraryItems.forEach(System.out::println);
            return;
        }

        switch (groupBy) {
            case TYPE -> {
                Map<String, List<LibraryItem>> byType = libraryItems.stream()
                        .collect(Collectors.groupingBy(item -> item.getClass().getSimpleName()));

                System.out.println("\nКаталог, згрупований за типом");
                byType.forEach((type, items) -> {
                    System.out.println("\nТип: " + type + " (" + items.size() + ")");
                    items.forEach(System.out::println);
                });
            }

            case PUBLICATION_YEAR -> {
                Map<Integer, List<LibraryItem>> byYear = libraryItems.stream()
                        .collect(Collectors.groupingBy(item -> item.getPublicationData().getYear()));

                System.out.println("\nКаталог, згрупований за роком видання");
                byYear.entrySet().stream()
                        .sorted(Map.Entry.<Integer, List<LibraryItem>>comparingByKey().reversed())
                        .forEach(entry -> {
                            System.out.println("\nРік: " + entry.getKey() + " (" + entry.getValue().size() + ")");
                            entry.getValue().forEach(System.out::println);
                        });
            }

            case CREATOR -> {
                Map<String, List<LibraryItem>> byCreator = libraryItems.stream()
                        .collect(Collectors.groupingBy(this::getCreator));

                System.out.println("\nКаталог, згрупований за автором");
                byCreator.forEach((creator, items) -> {
                    System.out.println("\nАвтор: " + creator + " (" + items.size() + ")");
                    items.forEach(System.out::println);
                });
            }
        }
    }

    public List<LibraryItem> findByTitle(String title) {
        if (title == null || title.isBlank()) return List.of();

        String query = title.toLowerCase().trim();
        return libraryItems.stream()
                .filter(item -> item.getTitle() != null && item.getTitle().toLowerCase().contains(query))
                .toList();
    }

    public List<LibraryItem> findByPublisher(String publisher) {
        if (publisher == null || publisher.isBlank()) return List.of();

        String query = publisher.toLowerCase().trim();
        return libraryItems.stream()
                .filter(item -> item.getPublisher() != null && item.getPublisher().toLowerCase().contains(query))
                .toList();
    }

    public List<LibraryItem> findByPublicationYear(int year) {
        return libraryItems.stream()
                .filter(item -> item.getPublicationData() != null && item.getPublicationData().getYear() == year)
                .toList();
    }

    public List<LibraryItem> findByAuthor(String author) {
        if (author == null || author.isBlank()) return List.of();
        String query = author.toLowerCase().trim();

        return libraryItems.stream()
                .filter(item -> {
                    if (item instanceof Book book) {
                        return book.getAuthor() != null && book.getAuthor().toLowerCase().contains(query);
                    } else if (item instanceof Newspaper news && news.getTopicAuthor() != null) {
                        return news.getTopicAuthor().values().stream()
                                .anyMatch(a -> a != null && a.toLowerCase().contains(query));
                    } else if (item instanceof Almanac almanac && almanac.getIncludedWorks() != null) {
                        return almanac.getIncludedWorks().stream()
                                .anyMatch(b -> b.getAuthor() != null && b.getAuthor().toLowerCase().contains(query));
                    }
                    return false;
                })
                .toList();
    }
}
