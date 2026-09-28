import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class DbService {

    private final Connection connection;

    public DbService(Connection connection) {
        this.connection = connection;
    }

    private void executeUpdate(String sql, Object... params) {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
            System.out.println("Операція успішно виконана");
        } catch (SQLException e) {
            System.err.println("Помилка виконання SQL-операції");
            e.printStackTrace();
        }
    }

    // Додати нову каву і її ціну
    public void addDrink(String name, double price) {
        String sql = "INSERT INTO drinks (name, price) VALUES (?, ?)";
        executeUpdate(sql, name, price);
    }

    // Змінити назву вже існуючого виду кави
    public void updateDrinkName(int drinkId, String newName) {
        String sql = "UPDATE drinks SET name = ? WHERE id = ?";
        executeUpdate(sql, newName, drinkId);
    }

    // Змінити ціну на окремий вид кави
    public void updateDrinkPrice(int drinkId, double newPrice) {
        String sql = "UPDATE drinks SET price = ? WHERE id = ?";
        executeUpdate(sql, newPrice, drinkId);
    }

    // Показати всі напої
    public void showAllDrinks() {
        String sql = "SELECT * FROM drinks";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Асортимент напоїв");
            while (rs.next()) {
                System.out.printf("Id: %d | Назва: %s | Ціна: %.2f грн\n",
                        rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Додавання інформації про десерт
    public void addDessert(String name, double price) {
        String sql = "INSERT INTO desserts (name, price) VALUES (?, ?)";
        executeUpdate(sql, name, price);
    }

    // Змінити назву існуючого десерту
    public void updateDessertName(int dessertId, String newName) {
        String sql = "UPDATE desserts SET name = ? WHERE id = ?";
        executeUpdate(sql, newName, dessertId);
    }

    // Змінити ціну існуючого десерту
    public void updateDessertPrice(int dessertId, double newPrice) {
        String sql = "UPDATE desserts SET price = ? WHERE id = ?";
        executeUpdate(sql, newPrice, dessertId);
    }

    // Видалити десерт
    public void deleteDessert(int dessertId) {
        String sql = "DELETE FROM desserts WHERE id = ?";
        executeUpdate(sql, dessertId);
    }

    // Показати всі десерти
    public void showAllDesserts() {
        String sql = "SELECT * FROM desserts";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Асортимент десертів");
            while (rs.next()) {
                System.out.printf("Id: %d | Назва: %s | Ціна: %.2f грн\n",
                        rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Додавання працівника
    public void addStaff(String fullName, String phone, String address, String position) {
        String sql = "INSERT INTO staff (full_name, phone, address, position) VALUES (?, ?, ?, ?)";
        executeUpdate(sql, fullName, phone, address, position);
    }

    // Показати інформацію про всіх барист
    public void showAllBaristas() {
        showStaffByPosition("Бариста", "Баристи: ");
    }

    // Показати інформацію про всіх офіціантів
    public void showAllWaiters() {
        showStaffByPosition("Офіціант", "Офіціанти: ");
    }

    private void showStaffByPosition(String position, String title) {
        String sql = "SELECT * FROM staff WHERE position = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, position);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println(title);
                while (rs.next()) {
                    System.out.printf("Id: %d | ПІБ: %s | Тел: %s | Адреса: %s\n",
                            rs.getInt("id"), rs.getString("full_name"), rs.getString("phone"), rs.getString("address"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Змінити телефон баристи
    public void updateBaristaPhone(int staffId, String newPhone) {
        String sql = "UPDATE staff SET phone = ? WHERE id = ? AND position = 'Бариста'";
        executeUpdate(sql, newPhone, staffId);
    }

    // Змінити поштовий адрес кондитера
    public void updateConfectionerAddress(int staffId, String newAddress) {
        String sql = "UPDATE staff SET address = ? WHERE id = ? AND position = 'Кондитер'";
        executeUpdate(sql, newAddress, staffId);
    }

    // Видалити інформацію про баристу
    public void deleteBarista(int staffId) {
        deleteStaff(staffId, "Бариста");
    }

    // Видалити інформацію про офіціанта
    public void deleteWaiter(int staffId) {
        deleteStaff(staffId, "Офіціант");
    }

    private void deleteStaff(int staffId, String position) {
        String sql = "DELETE FROM staff WHERE id = ? AND position = ?";
        executeUpdate(sql, staffId, position);
    }

    // Додавання інформації про клієнта
    public void addCustomer(String fullName, LocalDate birthDate, String phone, String address, int discount) {
        String sql = "INSERT INTO customers (full_name, birth_date, phone, address, discount_percent) VALUES (?, ?, ?, ?, ?)";
        executeUpdate(sql, fullName, Date.valueOf(birthDate), phone, address, discount);
    }

    // Змінити знижку клієнта
    public void updateCustomerDiscount(int customerId, int newDiscount) {
        String sql = "UPDATE customers SET discount_percent = ? WHERE id = ?";
        executeUpdate(sql, newDiscount, customerId);
    }

    // Видалення інформації про клієнта
    public void deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE id = ?";
        executeUpdate(sql, customerId);
    }

    // Додавання інформації про графік роботи
    public void addSchedule(int staffId, LocalDate workDate, LocalTime start, LocalTime end) {
        String sql = "INSERT INTO staff_schedule (staff_id, work_date, shift_start, shift_end) VALUES (?, ?, ?, ?)";
        executeUpdate(sql, staffId, Date.valueOf(workDate), Time.valueOf(start), Time.valueOf(end));
    }

    // Змінити розклад роботи
    public void updateSchedule(int scheduleId, LocalTime newStart, LocalTime newEnd) {
        String sql = "UPDATE staff_schedule SET shift_start = ?, shift_end = ? WHERE id = ?";
        executeUpdate(sql, Time.valueOf(newStart), Time.valueOf(newEnd), scheduleId);
    }

    // Видалити розклад роботи на конкретний день
    public void deleteScheduleByDate(LocalDate date) {
        String sql = "DELETE FROM staff_schedule WHERE work_date = ?";
        executeUpdate(sql, Date.valueOf(date));
    }

    // Видалити розклад роботи між вказаними датами
    public void deleteScheduleBetweenDates(LocalDate startDate, LocalDate endDate) {
        String sql = "DELETE FROM staff_schedule WHERE work_date BETWEEN ? AND ?";
        executeUpdate(sql, Date.valueOf(startDate), Date.valueOf(endDate));
    }

    // Показати розклад роботи на вказаний день
    public void showScheduleByDate(LocalDate date) {
        String sql = "SELECT s.full_name, sc.shift_start, sc.shift_end FROM staff_schedule sc " +
                "JOIN staff s ON sc.staff_id = s.id WHERE sc.work_date = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(date));
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Графік роботи на " + date);
                while (rs.next()) {
                    System.out.printf("Працівник: %s | Зміна: %s - %s\n",
                            rs.getString("full_name"), rs.getTime("shift_start"), rs.getTime("shift_end"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Додавання інформації про замовлення кави
    public void addOrderCoffee(int customerId, int staffId, int drinkId, double amount) {
        String sql = "INSERT INTO orders (customer_id, staff_id, total_amount) VALUES (?, ?, ?)";
        executeUpdate(sql, customerId, staffId, amount);
    }

    // Додавання інформації про замовлення десерту
    public void addOrderDessert(int customerId, int staffId, int dessertId, double amount) {
        String sql = "INSERT INTO orders (customer_id, staff_id, total_amount) VALUES (?, ?, ?)";
        executeUpdate(sql, customerId, staffId, amount);
    }

    // Змінити інформацію про замовлення
    public void updateOrder(int orderId, double newAmount) {
        String sql = "UPDATE orders SET total_amount = ? WHERE id = ?";
        executeUpdate(sql, newAmount, orderId);
    }

    // Видалити замовлення
    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE id = ?";
        executeUpdate(sql, orderId);
    }

    // Видалити замовлення десерту
    public void deleteOrderDessert(int orderId) {
        deleteOrder(orderId);
    }

    // Показати всі замовлення клієнта
    public void showOrdersByCustomer(int customerId) {
        String sql = "SELECT * FROM orders WHERE customer_id = ?";
        showOrdersGeneric(sql, customerId, "Замовлення клієнта ID: " + customerId);
    }

    // Показати всі замовлення офіціанта
    public void showOrdersByStaff(int staffId) {
        String sql = "SELECT * FROM orders WHERE staff_id = ?";
        showOrdersGeneric(sql, staffId, "Замовлення офіціанта ID: " + staffId);
    }

    // Універсальний метод для показу замовлень
    private void showOrdersGeneric(String sql, int paramId, String title) {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, paramId);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println(title);
                while (rs.next()) {
                    System.out.printf("Замовлення Id: %d | Сума: %.2f | Дата: %s\n",
                            rs.getInt("id"), rs.getDouble("total_amount"), rs.getTimestamp("order_date"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Показати топ 3 напої за останній місяць
    public void showTop3DrinksLastMonth() {
        String sql = "SELECT d.name, COUNT(o.id) as order_count " +
                "FROM orders o JOIN drinks d ON o.drink_id = d.id " +
                "WHERE o.order_date >= CURRENT_DATE - INTERVAL '1 month' " +
                "GROUP BY d.id, d.name ORDER BY order_count DESC LIMIT 3";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Топ 3 напої за останній місяць:");
            while (rs.next()) {
                System.out.printf("Напій: %s | Кількість замовлень: %d\n", rs.getString("name"), rs.getInt("order_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Показати топ 5 десертів за останні 10 днів
    public void showTop5DessertsLast10Days() {
        String sql = "SELECT d.name, COUNT(o.id) as order_count " +
                "FROM orders o JOIN desserts d ON o.dessert_id = d.id " +
                "WHERE o.order_date >= CURRENT_DATE - INTERVAL '10 days' " +
                "GROUP BY d.id, d.name ORDER BY order_count DESC LIMIT 5";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Топ 5 десертів за останні 10 днів:");
            while (rs.next()) {
                System.out.printf("Десерт: %s | Кількість замовлень: %d\n", rs.getString("name"), rs.getInt("order_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Показати середню суму замовлення за конкретну дату
    public void showAverageOrderSumByDate(LocalDate date) {
        String sql = "SELECT AVG(total_amount) as avg_sum FROM orders WHERE DATE(order_date) = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(date));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getObject("avg_sum") != null) {
                    System.out.printf("Середня сума замовлення за %s: %.2f грн\n", date, rs.getDouble("avg_sum"));
                } else {
                    System.out.println("Замовлень на " + date + " немає.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Показати найбільші замовлення за конкретну дату
    public void showLargestOrdersByDate(LocalDate date) {
        String sql = "SELECT * FROM orders WHERE DATE(order_date) = ? AND total_amount = " +
                "(SELECT MAX(total_amount) FROM orders WHERE DATE(order_date) = ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setDate(2, Date.valueOf(date));
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Найбільші замовлення за " + date + ":");
                while (rs.next()) {
                    System.out.printf("Id замовлення: %d | Сума: %.2f грн\n", rs.getInt("id"), rs.getDouble("total_amount"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Показати постійних тих, хто відвідував заклад 3 і більше разів за останній тиждень
    public void showLoyalCustomers() {
        String sql = "SELECT c.full_name, COUNT(o.id) as visits " +
                "FROM orders o JOIN customers c ON o.customer_id = c.id " +
                "WHERE o.order_date >= CURRENT_DATE - INTERVAL '7 days' " +
                "GROUP BY c.id, c.full_name HAVING COUNT(o.id) >= 3";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Постійні клієнти:");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("Клієнт: %s | Кількість візитів: %d\n", rs.getString("full_name"), rs.getInt("visits"));
            }
            if (!found) {
                System.out.println("Постійних клієнтів за останній тиждень не знайдено.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}