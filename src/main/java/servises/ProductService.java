package servises;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface ProductService {
    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @GET("products")
    Call<ResponseBody> getProducts();

    @PUT("products")
    Call<Product> putProducts(@Body Product createProductRequest);
}
