package Object;

public class StaffTypeDataUtils {
    public static StaffType[] getProductType(){
        StaffType pt1=new StaffType(1,"Nhân viên order");
        StaffType pt2=new StaffType(2,"Nhân viên pha chế");
        StaffType pt3=new StaffType(3,"Nhân viên thu ngân");
        return new StaffType[]{pt1, pt2, pt3};
    }
}
