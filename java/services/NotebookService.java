package services;

import dao.NotebookDao;
import entity.CoverType;
import entity.Notebook;
import entity.PageStyleType;

import java.util.List;
import java.util.Scanner;

public class NotebookService {
    private final NotebookDao dao = new NotebookDao();
    private final Scanner scanner = new Scanner(System.in);

    public void seedDataIfEmpty() {
        if (dao.findAll().isEmpty()) {
            System.out.println("База даних порожня. Заповнення початковими даними...");
            dao.save(Notebook.builder()
                    .manufacturerName("Moleskine")
                    .codeOrTitle("Classic Large")
                    .pageCount(240)
                    .coverType(CoverType.HARD)
                    .country("Італія")
                    .circulation(50000L)
                    .pageStyle(PageStyleType.GRID)
                    .build());

            dao.save(Notebook.builder()
                    .manufacturerName("Leuchtturm1917")
                    .codeOrTitle("A5 Medium")
                    .pageCount(251)
                    .coverType(CoverType.HARD)
                    .country("Німеччина")
                    .circulation(35000L)
                    .pageStyle(PageStyleType.LINE)
                    .build());

            dao.save(Notebook.builder()
                    .manufacturerName("Школярик")
                    .codeOrTitle("Soft Touch Ultra")
                    .pageCount(96)
                    .coverType(CoverType.SOFT)
                    .country("Україна")
                    .circulation(100000L)
                    .pageStyle(PageStyleType.BLANK)
                    .build());

            dao.save(Notebook.builder()
                    .manufacturerName("Школярик")
                    .codeOrTitle("Eco Note")
                    .pageCount(48)
                    .coverType(CoverType.SOFT)
                    .country("Україна")
                    .circulation(150000L)
                    .pageStyle(PageStyleType.GRID)
                    .build());
        }
    }

    public void showAllNotebooks() {
        List<Notebook> list = dao.findAll();
        if (list.isEmpty()) {
            System.out.println("У БД немає блокнотів.");
        } else {
            list.forEach(n -> System.out.println(n));
        }
    }

    public void addNewNotebook() {
        System.out.print("Виробник: ");
        String m = scanner.nextLine();
        System.out.print("Назва/Шифр: ");
        String title = scanner.nextLine();
        System.out.print("Кількість сторінок: ");
        int pages = Integer.parseInt(scanner.nextLine());
        System.out.print("Тип обкладинки (1 - HARD, 2 - SOFT): ");
        CoverType cover = scanner.nextLine().equals("1") ? CoverType.HARD : CoverType.SOFT;
        System.out.print("Країна: ");
        String country = scanner.nextLine();
        System.out.print("Тираж: ");
        long circ = Long.parseLong(scanner.nextLine());
        System.out.print("Стиль сторінки (1 - GRID, 2 - LINE, 3 - BLANK): ");
        String st = scanner.nextLine();
        PageStyleType style = switch (st) {
            case "2" -> PageStyleType.LINE;
            case "3" -> PageStyleType.BLANK;
            default -> PageStyleType.GRID;
        };

        Notebook n = Notebook.builder()
                .manufacturerName(m)
                .codeOrTitle(title)
                .pageCount(pages)
                .coverType(cover)
                .country(country)
                .circulation(circ)
                .pageStyle(style)
                .build();

        dao.save(n);
        System.out.println("Блокнот успішно додано!");
    }

    public void updateNotebook() {
        System.out.print("Введіть ID блокнота для оновлення: ");
        Long id = Long.parseLong(scanner.nextLine());
        dao.findById(id).ifPresentOrElse(n -> {
            System.out.print("Нова назва/шифр (поточна: " + n.getCodeOrTitle() + "): ");
            String title = scanner.nextLine();
            if (!title.isBlank()) n.setCodeOrTitle(title);
            dao.update(n);
            System.out.println("Запис оновлено!");
        }, () -> System.out.println("Блокнот з таким ID не знайдено."));
    }

    public void deleteNotebook() {
        System.out.print("Введіть ID блокнота для видалення: ");
        Long id = Long.parseLong(scanner.nextLine());
        dao.deleteById(id);
        System.out.println("Блокнот видалено (якщо він існував).");
    }

    public void showCountriesWithCount() {
        dao.countNotebooksByCountry().forEach(arr ->
                System.out.printf("Країна: %-15s | Кількість: %d%n", arr[0], arr[1]));
    }

    public void showManufacturersWithCount() {
        dao.countNotebooksByManufacturer().forEach(arr ->
                System.out.printf("Виробник: %-15s | Кількість: %d%n", arr[0], arr[1]));
    }

    public void showMaxAndMinCountry() {
        Object[] max = dao.findCountryWithMaxNotebooks();
        Object[] min = dao.findCountryWithMinNotebooks();
        if (max != null) System.out.println("Країна з найбільшою кількістю: " + max[0] + " (" + max[1] + " шт.)");
        if (min != null) System.out.println("Країна з найменшою кількістю: " + min[0] + " (" + min[1] + " шт.)");
    }

    public void showMaxAndMinManufacturer() {
        Object[] max = dao.findManufacturerWithMaxNotebooks();
        Object[] min = dao.findManufacturerWithMinNotebooks();
        if (max != null) System.out.println("Виробник з найбільшою кількістю: " + max[0] + " (" + max[1] + " шт.)");
        if (min != null) System.out.println("Виробник з найменшою кількістю: " + min[0] + " (" + min[1] + " шт.)");
    }

    public void showByCoverType() {
        System.out.print("1 - Тверда (HARD), 2 - М'яка (SOFT): ");
        CoverType ct = scanner.nextLine().equals("1") ? CoverType.HARD : CoverType.SOFT;
        dao.findByCoverType(ct).forEach(n -> System.out.println(n));
    }

    public void filterByCountry() {
        System.out.print("Введіть назву країни: ");
        String c = scanner.nextLine();
        dao.findByCountry(c).forEach(n -> System.out.println(n));
    }

    public void filterByPageStyle() {
        System.out.print("1 - Клітинка (GRID), 2 - Лінійка (LINE), 3 - Порожня (BLANK): ");
        String st = scanner.nextLine();
        PageStyleType style = switch (st) {
            case "2" -> PageStyleType.LINE;
            case "3" -> PageStyleType.BLANK;
            default -> PageStyleType.GRID;
        };
        dao.findByPageStyle(style).forEach(n -> System.out.println(n));
    }

    public void filterByPages() {
        System.out.print("Мін. кількість сторінок: ");
        int min = Integer.parseInt(scanner.nextLine());
        System.out.print("Макс. кількість сторінок: ");
        int max = Integer.parseInt(scanner.nextLine());
        dao.findByPageCountRange(min, max).forEach(n -> System.out.println(n));
    }

    public void filterByCirculation() {
        System.out.print("Мінімальний тираж: ");
        long min = Long.parseLong(scanner.nextLine());
        dao.findByMinCirculation(min).forEach(n -> System.out.println(n));
    }
}
