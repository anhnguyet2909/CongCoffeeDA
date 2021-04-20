package Object;

public class OrderList {
    int orderID, productID, count;

    public OrderList(int orderID, int productID, int count) {
        this.orderID = orderID;
        this.productID = productID;
        this.count = count;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
