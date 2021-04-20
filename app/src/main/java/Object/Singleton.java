package Object;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    private static Singleton uniqInstance;
    public List<ProductOrder> list=new ArrayList<>();
    public List<User> userList=new ArrayList<>();

    public Singleton() {
    }

    public static Singleton getUniqInstance() {
        if (uniqInstance == null)
            uniqInstance = new Singleton();
        return uniqInstance;
    }

    public List<ProductOrder> getList() {
        return list;
    }

    public void setList(List<ProductOrder> list) {
        this.list = list;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
