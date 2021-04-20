package Object;

public class Consum {
    int consumID, materialID, productID;
    float quantity;
    String tag;

    public Consum(int consumID, int materialID, int productID, float quantity, String tag) {
        this.consumID = consumID;
        this.materialID = materialID;
        this.productID = productID;
        this.quantity = quantity;
        this.tag = tag;
    }

    public int getConsumID() {
        return consumID;
    }

    public void setConsumID(int consumID) {
        this.consumID = consumID;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
