package Product;

import java.io.*;
import java.util.List;

public class ProductRepository {
    private final static File productFile = new File("Product.txt");
    private final static File paymentFile = new File("Payment.txt");


    public static List<Product> getAllProducts() throws IOException {
        if (!productFile.exists()) productFile.createNewFile();
        return new BufferedReader(new FileReader(productFile))
                .lines().map(lines -> lines.split(","))
                .map(lines -> new Product(Integer.parseInt(lines[0]), lines[1], Double.parseDouble(lines[2]), Integer.parseInt(lines[3])))
                .toList();
    }

    public static void doPayment(Payment payment) throws IOException {

        if (!paymentFile.exists()) paymentFile.createNewFile();
        inventory(payment);
        BufferedWriter br = new BufferedWriter(new FileWriter(paymentFile, true));
        br.write(payment.getProductId() + "," + payment.getProductName() + "," + payment.getProductPrice() + "," + payment.getProductQty() + "\n");
        br.close();

    }

    private static void inventory(Payment payment) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(productFile));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            String[] lines = line.split(",");
            if (Integer.parseInt(lines[0]) == payment.getProductId()) {
                int newQty = Integer.parseInt(lines[3]) - payment.getProductQty();

                sb.append(payment.getProductId()).append(",")
                        .append(payment.getProductName()).append(",")
                        .append(payment.getProductPrice()).append(",")
                        .append(newQty).append("\n");

            } else {
                sb.append(line).append("\n");
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(productFile));
        bw.write(sb.toString());
        bw.close();
        br.close();

    }

    public static void addProduct(Product productToAdd) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(productFile));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            String[] lines = line.split(",");
            if (Integer.parseInt(lines[0]) == productToAdd.getProductId()) {
                int newQty = Integer.parseInt(lines[3]) + productToAdd.getProductQty();
                sb.append(productToAdd.getProductId()).append(",")
                        .append(productToAdd.getProductName()).append(",")
                        .append(productToAdd.getProductPrice()).append(",")
                        .append(newQty).append("\n");
            } else {
                sb.append(line).append("\n");
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(productFile));
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    public static void addStaticProduct(List<Product> products) {
        try {
            if (!productFile.exists()) productFile.createNewFile();
            if (productFile.length() != 0) return;
            BufferedWriter bw = new BufferedWriter(new FileWriter(productFile, true));
            for (Product product : products) {
                bw.write(product.getProductId() + "," + product.getProductName() + "," + product.getProductPrice() + "," + product.getProductQty() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Payment> getAllPayments() {
        try {
            if (!paymentFile.exists()) paymentFile.createNewFile();
            return new BufferedReader(new FileReader(paymentFile))
                    .lines().map(lines -> lines.split(","))
                    .map(lines -> new Payment(Integer.parseInt(lines[0]), lines[1], Double.parseDouble(lines[2]), Integer.parseInt(lines[3])))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
