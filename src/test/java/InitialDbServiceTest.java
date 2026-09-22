import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class InitialDbServiceTest {
    @Test
    void testConnectSuccess() {
        InitialDbService dbService = new InitialDbService();
        try (Connection connection = dbService.connect()) {
            assertNotNull(connection, "Об'єкт з'єднання не повинен бути null");
            assertFalse(connection.isClosed(), "З'єднання з базою даних має бути відкритим");
        } catch (SQLException e) {
            fail("Підключення викинуло виняток SQLException: " + e.getMessage());
        }
    }

    @Test
    void testInitializeDatabaseCreatesTables() {
        InitialDbService dbService = new InitialDbService();

        assertDoesNotThrow(() -> dbService.initializeDatabase(), "Метод викинув виняток");

        try (Connection connection = dbService.connect()) {
            assertNotNull(connection, "З'єднання не повинно бути null");

            DatabaseMetaData metaData = connection.getMetaData();

            String[] expectedTables = {
                    "drinks",
                    "desserts",
                    "staff",
                    "customers",
                    "staff_schedule",
                    "orders"
            };

            for (String tableName : expectedTables) {
                try (ResultSet rs = metaData.getTables(null, null, tableName, new String[]{"TABLE"})) {
                    assertTrue(rs.next(), "Таблиця '" + tableName + "' не була знайдена в базі даних після ініціалізації");
                }
            }

        } catch (SQLException e) {
            fail("Помилка під час перевірки наявності таблиць: " + e.getMessage());
        }
    }
}