package Object;

public class Product {
    String id;
    String name, image;
    int price;
    String options;
    int typeID;

    public Product() {
    }

    public Product(String id, String name, String image, int price, String options, int typeID) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.options = options;
        this.typeID = typeID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }
}
