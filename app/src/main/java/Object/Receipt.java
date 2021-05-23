package Object;

import java.util.List;

public class Receipt {
    String staffName;
    String staffPhone;
    int additionalFee;
    int discount;
    int cash;
    int change;
    int[] orderIds;

    public Receipt(String staffName, String staffPhone, int additionalFee, int discount, int cash, int change, int[] orderIds) {
        this.staffName = staffName;
        this.staffPhone = staffPhone;
        this.additionalFee = additionalFee;
        this.discount = discount;
        this.cash = cash;
        this.change = change;
        this.orderIds = orderIds;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStaffPhone() {
        return staffPhone;
    }


    public int getAdditionalFee() {
        return additionalFee;
    }

    public int getDiscount() {
        return discount;
    }

    public int getCash() {
        return cash;
    }

    public int getChange() {
        return change;
    }

    public int[] getOrderIds() {
        return orderIds;
    }
}
