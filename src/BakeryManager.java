import java.util.List;
import java.util.Map;

public class BakeryManager {
    private List<Product> products;
    private Order currentOrder;

    public BakeryManager() {
        currentOrder = new Order();
        loadProductsFromDatabase();  // Załaduj produkty z bazy danych
    }

    private void loadProductsFromDatabase() {
        products = DatabaseManager.getProductsFromDatabase();
    }

    public void addProductToOrder(String productName, int quantity) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                currentOrder.addProduct(product, quantity);
                return;
            }
        }
    }

    public String getOrderDetails() {
        StringBuilder details = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : currentOrder.getProducts().entrySet()) {
            details.append(entry.getKey().getName()).append(" - ").append(entry.getValue()).append(" szt.\n");
        }
        return details.toString();
    }

    public String generateInvoice(String clientName) {
        Invoice invoice = new Invoice(currentOrder, clientName);
        return invoice.generateInvoice();
    }
}
