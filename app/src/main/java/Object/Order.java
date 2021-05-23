package Object;

public class Order {
    String name;
    String staffName, staffPhone;
    String note;
    String listProduct;

    public Order(String name, String staffName, String staffPhone, String note, String lisProduct) {
        this.staffPhone = staffPhone;
        this.name = name;
        this.staffName = staffName;
        this.note = note;
        this.listProduct = lisProduct;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
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
        return listProduct;
    }

    public void setLisProduct(String lisProduct) {
        this.listProduct = lisProduct;
    }
}
