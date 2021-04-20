package Object;

public class ProductOrder {
    String id;
    String name, image;
    int price, count;

    public ProductOrder(String id, String name, String image, int price, int count) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.count = count;
    }

    public ProductOrder() {
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
