import java.util.Map;

public class Invoice {
    private Order order;
    private String clientName;

    public Invoice(Order order, String clientName) {
        this.order = order;
        this.clientName = clientName;
    }

    public String generateInvoice() {
        StringBuilder invoice = new StringBuilder();
        invoice.append("Faktura dla klienta: ").append(clientName).append("\n\n");

        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double cost = product.getPrice() * quantity;
            total += cost;
            invoice.append(quantity).append(" x ").append(product.getName()).append(" = ").append(quantity)
                    .append(" x ").append(product.getPrice()).append(" zł = ").append(cost).append(" zł\n");
        }
        invoice.append("\nRazem: ").append(total).append(" zł");
        return invoice.toString();
    }
}
