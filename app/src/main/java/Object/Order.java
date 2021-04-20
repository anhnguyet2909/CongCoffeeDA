package Object;

public class Order {
    String tableID;
    String name;
    String staffName;
    String note;
    String lisProduct;

    public Order(String tableID, String name, String staffName, String note, String lisProduct) {
        this.tableID = tableID;
        this.name = name;
        this.staffName = staffName;
        this.note = note;
        this.lisProduct = lisProduct;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLisProduct() {
        return lisProduct;
    }

    public void setLisProduct(String lisProduct) {
        this.lisProduct = lisProduct;
    }
}
