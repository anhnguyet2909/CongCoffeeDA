package Object;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    private static Singleton uniqInstance;
    public List<ProductOrder> list=new ArrayList<>();
    public List<User> userList=new ArrayList<>();
    public List<ShowOrderByTable> listTableOrder=new ArrayList<ShowOrderByTable>();

    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public List<ShowOrderByTable> getListTableOrder() {
        return listTableOrder;
    }

    public void setListTableOrder(List<ShowOrderByTable> listTableOrder) {
        this.listTableOrder = listTableOrder;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private User user;

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

    public String IP="192.168.26.103";

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
