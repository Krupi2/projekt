import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bakery";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Metoda do pobierania listy produktów z bazy danych
    public static List<Product> getProductsFromDatabase() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM products";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                products.add(new Product(name, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Metoda do pobierania listy klientów z bazy danych
    public static List<String> getClientsFromDatabase() {
        List<String> clients = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String query = "SELECT name FROM clients";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                clients.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    // Metoda do dodawania nowego produktu do bazy danych
    public static void addProductToDatabase(String name, double price) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda do dodawania nowego klienta do bazy danych
    public static void addClientToDatabase(String name) {
        String query = "INSERT INTO clients (name) VALUES (?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
