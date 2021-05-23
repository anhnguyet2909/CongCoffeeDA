package Object;

public class ShowOrderByTable extends ProductOrder{
   String tableName;

    public ShowOrderByTable(String id, String name, String option, String image, int price, int quantity, String tableName) {
        super(id, name, option, image, price, quantity);
        this.tableName = tableName;
    }

    public ShowOrderByTable(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
