package Product;

import java.util.List;

public class GenerateSales {

    private final List<Payment> paymentList;

    public GenerateSales() {
        paymentList = ProductRepository.getAllPayments();
    }

    public double computeSales() {
        return paymentList.stream().mapToDouble(Payment::compute).sum();
    }
}
