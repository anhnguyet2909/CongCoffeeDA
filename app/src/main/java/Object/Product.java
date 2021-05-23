package Object;

import android.util.Log;

import java.util.List;

public class Product {
    String id;
    String name, image;
    List<Options> options;
    int categoryId;

    public Product(String name, String image, List<Options> options, int categoryID) {
        this.name = name;
        this.image = image;
        this.options = options;
        this.categoryId = categoryID;
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

    public String getFullLinkImage(){
        return APIClient.HOST+image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Options> getOptions() {
        return options;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }

    public int getCategoryId() {
        //Log.d("TAG123", "getCategoryID: "+ categoryId);
        return categoryId;
    }

    // hien thi gia sp
    public int getPrice(){
        if(options != null && options.size() > 0){
            int index=-1;
            for (int i = 0; i < options.size(); i++) {
                if(options.get(i).id == 0) {
                    index = i;break;
                }
            }
            if(index >= 0){
                return options.get(index).price;
            }else {
                return -5;
            }
        }else{
            return -10;
        }
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", options=" + options +
                ", categoryId=" + categoryId +
                '}';
    }
}
