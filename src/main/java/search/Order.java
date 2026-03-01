package search;

/**
 * Order represents a purchase order.
 * Selected for MC/DC testing due to compound boolean expressions.
 */
public class Order implements Comparable<Order> {

    private int    orderId;
    private String customerName;
    private double totalAmount;
    private String status; // PENDING | PROCESSING | SHIPPED | DELIVERED | CANCELLED

    public Order(int orderId, String customerName, double totalAmount, String status) {
        this.orderId      = orderId;
        this.customerName = customerName;
        this.totalAmount  = totalAmount;
        this.status       = status;
    }

    public int    getOrderId()      { return orderId; }
    public String getCustomerName() { return customerName; }
    public double getTotalAmount()  { return totalAmount; }
    public String getStatus()       { return status; }
    public void   setStatus(String s)       { this.status = s; }
    public void   setTotalAmount(double a)  { this.totalAmount = a; }

    /** Active = PENDING, PROCESSING, or SHIPPED */
    public boolean isActive() {
        return status != null && (
                status.equals("PENDING")    ||
                        status.equals("PROCESSING") ||
                        status.equals("SHIPPED")
        );
    }

    /** Discount if: amount > 100 AND isActive() AND not SHIPPED */
    public boolean qualifiesForDiscount() {
        return totalAmount > 100.0 && isActive() && !status.equals("SHIPPED");
    }

    /** High priority if: (amount > 500 OR VIP customer) AND status is PENDING */
    public boolean isHighPriority() {
        boolean highValue   = totalAmount > 500.0;
        boolean vipCustomer = customerName != null && customerName.startsWith("VIP");
        boolean isPending   = "PENDING".equals(status);
        return (highValue || vipCustomer) && isPending;
    }

    @Override
    public int compareTo(Order other) { return Integer.compare(this.orderId, other.orderId); }

    @Override
    public String toString() {
        return "Order{id=" + orderId + ", customer='" + customerName +
                "', amount=" + totalAmount + ", status='" + status + "'}";
    }
}