package Object;

public class ProductTypeDataUtils {
    public static ProductType[] getProductType(){
        ProductType pt1=new ProductType(1,"Cafe");
        ProductType pt2=new ProductType(2,"Tea");
        ProductType pt3=new ProductType(3,"Juice");
        ProductType pt4=new ProductType(4,"Others");
        return new ProductType[]{pt1, pt2, pt3, pt4};
    }
}
