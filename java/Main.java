import services.NotebookService;
import util.HibernateUtil;
import dao.NotebookDao;
import entity.Notebook;
import entity.CoverType;
import entity.PageStyleType;

import java.util.List;
import java.util.Scanner;

public class Main {
    private  static final NotebookService service = new NotebookService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        service.seedDataIfEmpty();

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Оберіть пункт меню: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> service.showAllNotebooks();
                case "2" -> service.addNewNotebook();
                case "3" -> service.updateNotebook();
                case "4" -> service.deleteNotebook();
                case "5" -> service.showCountriesWithCount();
                case "6" -> service.showManufacturersWithCount();
                case "7" -> service.showMaxAndMinCountry();
                case "8" -> service.showMaxAndMinManufacturer();
                case "9" -> service.showByCoverType();
                case "10" -> service.filterByCountry();
                case "11" -> service.filterByPageStyle();
                case "12" -> service.filterByPages();
                case "13" -> service.filterByCirculation();
                case "0" -> {
                    running = false;
                    HibernateUtil.shutdown();
                    System.out.println("Від'єднання від бази даних. Застосунок завершено.");
                }
                default -> System.out.println("Невірний вибір! Спробуйте ще раз.");
            }
            System.out.println("\n--------------------------------------------------\n");
        }
    }

    private static void printMenu() {
        System.out.println("---------------------------------------------------");
        System.out.println("1. Показати усі блокноти");
        System.out.println("2. Додати новий блокнот");
        System.out.println("3. Оновити інформацію про блокнот");
        System.out.println("4. Видалити блокнот");
        System.out.println("5. Статистика: Країни-виробники та кількість блокнотів");
        System.out.println("6. Статистика: Виробники та кількість блокнотів");
        System.out.println("7. Країна з найбільшою та найменшою кількістю");
        System.out.println("8. Виробник з найбільшою та найменшою кількістю");
        System.out.println("9. Показати блокноти за типом обкладинки");
        System.out.println("10. Фільтр за країною");
        System.out.println("11. Фільтр за зовнішнім виглядом сторінки");
        System.out.println("12. Фільтр за кількістю сторінок");
        System.out.println("13. Фільтр за тиражем");
        System.out.println("0. Вихід");
    }
}