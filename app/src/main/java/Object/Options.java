package Object;

import com.google.gson.annotations.SerializedName;

public class Options {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("price")
    int price;

    public Options(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
