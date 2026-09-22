import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitialDbService {
    private static final String DB_NAME = "lab_2_cafe";
    private static final String URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    public Connection connect() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Успішно підключено до бази даних lab_2_cafe!");
        } catch (SQLException e) {
            throw e;
        }
        return connection;
    }

    public void initializeDatabase() {
        try (Connection conn = connect()) {
            if (conn == null) {
                System.err.println("Не вдалося встановити з'єднання для ініціалізації бази даних.");
                return;
            }

            System.out.println("Перевірка та створення структури бази даних...");

            createDrinksTable(conn);
            createDessertsTable(conn);
            createStaffTable(conn);
            createCustomersTable(conn);
            createStaffScheduleTable(conn);
            createOrdersTable(conn);

            System.out.println("Ініціалізація бази даних успішно завершена!");
        } catch (SQLException e) {
            System.err.println("Помилка під час ініціалізації бази даних!");
            e.printStackTrace();
        }
    }

    // 1. Асортимент: Напої
    private void createDrinksTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS drinks (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "price NUMERIC(10, 2) NOT NULL" +
                ")";
        executeSQL(conn, sql, "Drinks");
    }

    // 2. Асортимент: Десерти
    private void createDessertsTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS desserts (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "price NUMERIC(10, 2) NOT NULL" +
                ")";
        executeSQL(conn, sql, "Desserts");
    }

    // 3. Персонал
    private void createStaffTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS staff (" +
                "id SERIAL PRIMARY KEY, " +
                "full_name VARCHAR(150) NOT NULL, " +
                "phone VARCHAR(20) NOT NULL, " +
                "address TEXT NOT NULL, " +
                "position VARCHAR(50) CHECK (position IN ('Бариста', 'Офіціант', 'Кондитер')) NOT NULL" +
                ")";
        executeSQL(conn, sql, "Staff");
    }

    // 4. Клієнти
    private void createCustomersTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS customers (" +
                "id SERIAL PRIMARY KEY, " +
                "full_name VARCHAR(150) NOT NULL, " +
                "birth_date DATE NOT NULL, " +
                "phone VARCHAR(20) NOT NULL, " +
                "address TEXT NOT NULL, " +
                "discount_percent INT DEFAULT 0" +
                ")";
        executeSQL(conn, sql, "Customers");
    }

    // 5. Графік роботи персоналу
    private void createStaffScheduleTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS staff_schedule (" +
                "id SERIAL PRIMARY KEY, " +
                "staff_id INT REFERENCES staff(id) ON DELETE CASCADE, " +
                "work_date DATE NOT NULL, " +
                "shift_start TIME NOT NULL, " +
                "shift_end TIME NOT NULL" +
                ")";
        executeSQL(conn, sql, "Staff Schedule");
    }

    // 6. Замовлення
    private void createOrdersTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS orders (" +
                "id SERIAL PRIMARY KEY, " +
                "customer_id INT REFERENCES customers(id) ON DELETE SET NULL, " +
                "staff_id INT REFERENCES staff(id) ON DELETE SET NULL, " +
                "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "total_amount NUMERIC(10, 2) NOT NULL" +
                ")";
        executeSQL(conn, sql, "Orders");
    }

    private void executeSQL(Connection conn, String sql, String tableName) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}
