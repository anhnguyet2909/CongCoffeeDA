package Object;

public class ProductOrder {
    String id;
    String name, image, option;
    int price, quantity;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductOrder(String id, String name, String option, String image, int price, int count) {
        this.id = id;
        this.name = name;
        this.option = option;
        this.image = image.replace(APIClient.HOST,"");
        this.price = price;
        this.quantity = count;
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
        return APIClient.HOST+image;
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
        return quantity;
    }

    public void setCount(int count) {
        this.quantity = count;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
