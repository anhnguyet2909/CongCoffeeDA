package Object;

public class Receipt {
    int id;
    String tableName;
    String staffName;
    String listProduct;
    String date;

    public Receipt(int id, String tableName, String staffName, String listProduct, String date) {
        this.id = id;
        this.tableName = tableName;
        this.staffName = staffName;
        this.listProduct = listProduct;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getListProduct() {
        return listProduct;
    }

    public void setListProduct(String listProduct) {
        this.listProduct = listProduct;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
