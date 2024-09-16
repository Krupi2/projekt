import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI {
    private BakeryManager bakeryManager = new BakeryManager();
    private JComboBox<String> productComboBox;
    private JComboBox<String> clientComboBox;
    private JTextField quantityField; // Pole do wpisywania ilości

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Piekarnia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridLayout(5, 1));

        // Panel wyboru klienta
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new GridLayout(1, 2));
        clientPanel.add(new JLabel("Klient:"));
        clientComboBox = new JComboBox<>();
        populateClientComboBox();  // Wypełnij ComboBox danymi klientów z bazy danych
        clientPanel.add(clientComboBox);
        frame.add(clientPanel);

        // Panel zamówień
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new GridLayout(2, 2));

        // Użyj JComboBox zamiast JTextField dla wyboru produktu
        productComboBox = new JComboBox<>();
        populateProductComboBox();  // Wypełnij ComboBox danymi produktów z bazy danych

        quantityField = new JTextField();
        JButton addToInvoiceButton = new JButton("Dodaj do faktury");

        orderPanel.add(new JLabel("Produkt:"));
        orderPanel.add(productComboBox);
        orderPanel.add(new JLabel("Ilość:"));
        orderPanel.add(quantityField);
        frame.add(orderPanel);

        // Panel do wyświetlania zamówienia
        JTextArea orderArea = new JTextArea();
        frame.add(new JScrollPane(orderArea));

        // Panel przycisków
        JPanel buttonPanel = new JPanel();
        JButton invoiceButton = new JButton("Generuj Fakturę");
        JButton addProductButton = new JButton("Dodaj produkt do bazy danych");
        JButton addClientButton = new JButton("Dodaj nowego klienta do bazy danych");

        buttonPanel.add(invoiceButton);
        buttonPanel.add(addProductButton);
        buttonPanel.add(addClientButton);
        buttonPanel.add(addToInvoiceButton);
        frame.add(buttonPanel);

        // Listener do przycisku dodawania zamówienia
        addToInvoiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String product = (String) productComboBox.getSelectedItem();
                try {
                    int quantity = Integer.parseInt(quantityField.getText());  // Pobieramy ilość z pola tekstowego
                    bakeryManager.addProductToOrder(product, quantity);
                    orderArea.setText(bakeryManager.getOrderDetails());  // Aktualizujemy obszar tekstowy zamówienia
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Proszę wprowadzić poprawną ilość", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener do przycisku generowania faktury
        invoiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String client = (String) clientComboBox.getSelectedItem();
                String invoice = bakeryManager.generateInvoice(client);
                JOptionPane.showMessageDialog(frame, invoice, "Faktura", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Listener do przycisku dodawania nowego produktu
        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddProductDialog(frame);
            }
        });

        // Listener do przycisku dodawania nowego klienta
        addClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddClientDialog(frame);
            }
        });

        frame.setVisible(true);
    }

    // Metoda do wypełnienia JComboBox danymi produktów z bazy danych
    private void populateProductComboBox() {
        List<Product> products = DatabaseManager.getProductsFromDatabase();
        productComboBox.removeAllItems();  // Czyść istniejące elementy
        for (Product product : products) {
            productComboBox.addItem(product.getName());
        }
    }

    // Metoda do wypełnienia JComboBox danymi klientów z bazy danych
    private void populateClientComboBox() {
        List<String> clients = DatabaseManager.getClientsFromDatabase();
        clientComboBox.removeAllItems();  // Czyść istniejące elementy
        for (String client : clients) {
            clientComboBox.addItem(client);
        }
    }

    // Okno dialogowe do dodawania nowego produktu
    private void showAddProductDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Dodaj Produkt", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JButton submitButton = new JButton("Dodaj");

        dialog.add(new JLabel("Nazwa produktu:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Cena:"));
        dialog.add(priceField);
        dialog.add(new JLabel());
        dialog.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                DatabaseManager.addProductToDatabase(name, price);
                populateProductComboBox();  // Odśwież listę produktów
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    // Okno dialogowe do dodawania nowego klienta
    private void showAddClientDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Dodaj Klienta", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new GridLayout(2, 2));

        JTextField nameField = new JTextField();
        JButton submitButton = new JButton("Dodaj");

        dialog.add(new JLabel("Nazwa klienta:"));
        dialog.add(nameField);
        dialog.add(new JLabel());
        dialog.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                DatabaseManager.addClientToDatabase(name);
                populateClientComboBox();  // Odśwież listę klientów
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }
}
