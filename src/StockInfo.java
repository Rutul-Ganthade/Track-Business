public class StockInfo {
    private double unitPrice;
    private int quantity;

    public StockInfo(double unitPrice, int quantity) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
}
