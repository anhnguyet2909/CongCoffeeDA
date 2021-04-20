package Object;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Material {
    String id;
    String name, unit;
    int amount;

    public Material() {
    }

    public Material(String id, String name, String unit, int amount) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.amount = amount;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", amount=" + amount +
                '}';
    }
}
