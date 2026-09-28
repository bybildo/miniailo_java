import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DbServiceTest {

    private static Connection connection;
    private static DbService dbService;

    @BeforeAll
    static void setUp() {
        try {
            InitialDbService initialDbService = new InitialDbService();
            connection = initialDbService.connect();
            dbService = new DbService(connection);
        } catch (SQLException e) {
            fail("Не вдалося підключитися до бази даних для тестування: " + e.getMessage());
        }
    }

    @Test
    void testDrinkOperations() {
        assertDoesNotThrow(() -> {
            dbService.addDrink("Еспресо", 40.0);
            int drinkId = getLastInsertId("drinks");
            dbService.updateDrinkName(drinkId, "Подвійне Еспресо");
            dbService.updateDrinkPrice(drinkId, 55.0);

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM drinks WHERE id = " + drinkId)) {
                assertTrue(rs.next(), "Напій має існувати в базі");
                assertEquals("Подвійне Еспресо", rs.getString("name"));
                assertEquals(55.0, rs.getDouble("price"));
            }
        }, "Операції з напоями не повинні викидати винятків");
    }

    @Test
    void testDessertOperations() {
        assertDoesNotThrow(() -> {
            dbService.addDessert("Чизкейк", 85.0);
            int dessertId = getLastInsertId("desserts");

            dbService.deleteDessert(dessertId);

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM desserts WHERE id = " + dessertId)) {
                assertFalse(rs.next(), "Десерт має бути видалений");
            }
        });
    }

    @Test
    void testStaffOperations() {
        assertDoesNotThrow(() -> {
            dbService.addStaff("Іван Іванов", "0991234567", "Київ", "Бариста");
            int baristaId = getLastInsertId("staff");

            dbService.updateBaristaPhone(baristaId, "0999999999");

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT phone FROM staff WHERE id = " + baristaId)) {
                assertTrue(rs.next());
                assertEquals("0999999999", rs.getString("phone"));
            }

            dbService.deleteBarista(baristaId);
        });
    }

    @Test
    void testOrderCreation() {
        assertDoesNotThrow(() -> {
            dbService.addCustomer("Олена Петрівна", LocalDate.of(1995, 5, 20), "0501112233", "Полтава", 5);
            int customerId = getLastInsertId("customers");

            dbService.addStaff("Петро Олексійович", "0670001122", "Полтава", "Офіціант");
            int staffId = getLastInsertId("staff");

            dbService.addOrderCoffee(customerId, staffId, 1, 150.0);
            int orderId = getLastInsertId("orders");

            dbService.updateOrder(orderId, 200.0);

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT total_amount FROM orders WHERE id = " + orderId)) {
                assertTrue(rs.next());
                assertEquals(200.0, rs.getDouble("total_amount"));
            }
        });
    }

    @Test
    void testShowMethods() {
        assertDoesNotThrow(() -> {
            dbService.showAllDrinks();
            dbService.showAllDesserts();
            dbService.showAllBaristas();
            dbService.showScheduleByDate(LocalDate.now());
        });
    }

    void testOrderAnalyticsMethods() {
        assertDoesNotThrow(() -> {
            dbService.addCustomer("Тестовий Клієнт", LocalDate.of(1990, 1, 1), "0990001122", "Київ", 0);
            int customerId = getLastInsertId("customers");

            dbService.addStaff("Тестовий Бариста", "0990003344", "Київ", "Бариста");
            int staffId = getLastInsertId("staff");

            dbService.addDrink("Макіято", 60.0);
            int drinkId = getLastInsertId("drinks");

            dbService.addDessert("Тірамісу", 90.0);
            int dessertId = getLastInsertId("desserts");

            dbService.addOrderCoffee(customerId, staffId, drinkId, 60.0);
            dbService.addOrderCoffee(customerId, staffId, drinkId, 60.0);
            dbService.addOrderDessert(customerId, staffId, dessertId, 90.0);

            dbService.showTop3DrinksLastMonth();
            dbService.showTop5DessertsLast10Days();
            dbService.showAverageOrderSumByDate(LocalDate.now());
            dbService.showLargestOrdersByDate(LocalDate.now());
            dbService.showLoyalCustomers();

        }, "Аналітичні методи не повинні викидати винятків при виконанні");
    }

    @AfterAll
    static void tearDown() {
        if (connection != null) {
            try (Statement stmt = connection.createStatement()) {
                String sql = "TRUNCATE TABLE orders, staff_schedule, customers, staff, desserts, drinks RESTART IDENTITY CASCADE";
                stmt.execute(sql);
                System.out.println("Всі таблиці успішно очищено після виконання тестів.");
            } catch (SQLException e) {
                System.err.println("Помилка під час очищення таблиць: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                    System.out.println("З'єднання закрито.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int getLastInsertId(String tableName) throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM " + tableName)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        }
    }
}