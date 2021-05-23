package Object;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("login")
    Call<User> createUser(@Body User user);

    @GET("product/list?page=1&size=50")
    Call<ListProduct> getListProduct();

    @GET("category/list")
    Call<ListCategory> getCategories();

    class ListCategory {
        public String message;
        public List<Category> categories;
    }

    class Category {
        public int id;
        public String name;
    }

    class ListProduct {
        public int totalItems, totalPages, currentPage;
        public List<Product> items;
    }

    @POST("upload/product")
    Call<StatusUploadImageProduct> uploadImageProduct(@Body String filename);

    class StatusUploadImageProduct{
        String message;
        public String url;
    }


    @POST("order/create")
    Call<OrderCreateResponse> createOrder(@Body Order order);

    class OrderCreateResponse {
        public String message;
        public OrderResponse order;
    }

    class OrderResponse{
        public int id;
        String name;
        String staffName, staffPhone;
        String note;
        String listProduct;
    }

    @GET("order/list?page=1&size=50")
    Call<OrderToBar> getOrder();

    class  OrderToBar {
        public int totalItems, totalPages, currentPage;
        public List<OrderInBarList> items;
    }

    class OrderInBarList{
        public int id;
        public String name;
        public String staffName, staffPhone;
        public String note;
        public List<ProductOrder> listProduct;
    }

    @POST("product/create")
    Call<ProductCreateResponse> createProduct(@Body Product product);

    class ProductCreateResponse{
        public String message;
        public Product product;
    }

    @POST("receipt/create")
    Call<ReceiptCreateResponse> createReceipt(@Body Receipt receipt);

    class ReceiptCreateResponse{
        public String message;
        public Receipt receipt;
    }

    class ReceiptOnList{
        public int id, additionalFee, discount, totalAmount,total, cash, change;
        public String staffName, staffPhone, name;
        public List<ProductOrder> listReceiptOnList;
    }

    class ReceiptListResponse{
        public int totalItems, totalPages, currentPage;
        public List<ReceiptOnList> items;
    }

    @GET("receipt/list?page=1&size=50")
    Call<ReceiptListResponse> getListReceipt();
}
