import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<Product, Integer> products;

    public Order() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        products.put(product, products.getOrDefault(product, 0) + quantity);
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }
}
